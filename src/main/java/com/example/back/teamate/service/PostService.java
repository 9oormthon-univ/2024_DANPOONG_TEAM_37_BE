package com.example.back.teamate.service;

import com.example.back.teamate.enums.MatchStatus;
import com.example.back.teamate.repository.MatchRepository;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.back.teamate.dto.PostDetailResponseDto;
import com.example.back.teamate.dto.PostFilterRequestDto;
import com.example.back.teamate.dto.PostListResponseDto;
import com.example.back.teamate.dto.PostRequestDto;
import com.example.back.teamate.entity.Field;
import com.example.back.teamate.entity.Mode;
import com.example.back.teamate.entity.Position;
import com.example.back.teamate.entity.Post;
import com.example.back.teamate.entity.PostPosition;
import com.example.back.teamate.entity.PostSkill;
import com.example.back.teamate.entity.Role;
import com.example.back.teamate.entity.Skill;
import com.example.back.teamate.entity.Users;
import com.example.back.teamate.enums.FieldName;
import com.example.back.teamate.enums.ModeName;
import com.example.back.teamate.enums.PositionName;
import com.example.back.teamate.enums.SkillName;
import com.example.back.teamate.enums.TeamRole;
import com.example.back.teamate.repository.FieldRepository;
import com.example.back.teamate.repository.ModeRepository;
import com.example.back.teamate.repository.PositionRepository;
import com.example.back.teamate.repository.PostPositionRepository;
import com.example.back.teamate.repository.PostRepository;
import com.example.back.teamate.repository.PostSkillRepository;
import com.example.back.teamate.repository.RoleRepository;
import com.example.back.teamate.repository.SkillRepository;
import com.example.back.teamate.repository.UsersRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final FieldRepository fieldRepository;
    private final ModeRepository modeRepository;
    private final PositionRepository positionRepository;
    private final SkillRepository skillRepository;
    private final PostPositionRepository postPositionRepository;
    private final PostSkillRepository postSkillRepository;
    private final RoleRepository roleRepository;
    private final UsersRepository usersRepository;
    private final MatchRepository matchRepository;

    // 게시글 생성
    public Long createPost(Long userId, PostRequestDto postRequestDto) {
        try {
            log.info("Step 1: Fetching user with ID: {}", userId);
            Users user = getUserById(userId);
            Role role = createRole(user, TeamRole.TEAM_LEADER);
            log.info("Step 2: User and Role fetched successfully");

            Post post = buildPost(postRequestDto, role);
            postRepository.save(post);
            log.info("Step 3: Post saved successfully with ID: {}", post.getPostId());

            savePositionsAndSkills(post, postRequestDto.getPositionList());
            log.info("Step 4: Positions and skills saved successfully");
            return post.getPostId();
        } catch (Exception e) {
            log.error("Error occurred while creating post: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void updatePost(Long postId, Long userId, PostRequestDto postRequestDto) {
        // 게시글과 사용자 검증
        Post post = validateAndGetPost(postId, userId);
        // Post 엔티티 업데이트
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setTotalMembers(postRequestDto.getTotalMembers());
        post.setExpectedPeriod(postRequestDto.getExpectedPeriod());
        post.setStartDate(postRequestDto.getStartDate());
        post.setDeadline(postRequestDto.getDeadline());
        post.setModeId(ModeName.fromDisplayName(postRequestDto.getMode()).getModeId());
        post.setFieldId(FieldName.fromDisplayName(postRequestDto.getField()).getFieldId());
        post.setGoogleFormUrl(postRequestDto.getGoogleFormUrl());
        post.setKakaoChatUrl(postRequestDto.getKakaoChatUrl());
        post.setPortfolioUrl(postRequestDto.getPortfolioUrl());

        // 기존 포지션 및 스킬 삭제
        postPositionRepository.deleteByPost(post);
        postSkillRepository.deleteByPost(post);

        // 새 포지션 및 스킬 저장
        savePositionsAndSkills(post, postRequestDto.getPositionList());
    }


    // 게시글 리스트 조회
    @Transactional(readOnly = true)
    public List<PostListResponseDto> getPostListAsList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findAll(pageable).stream()
            .map(this::convertToPostListResponseDto)
            .collect(Collectors.toList());
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public PostDetailResponseDto getPost(Long postId) {
        Post post = getPostById(postId);

        Map<PositionName, List<SkillName>> positionSkills = getPositionSkills(postId);
        Field field = getFieldById(post.getFieldId());
        Mode mode = getModeById(post.getModeId());
        Role author = post.getRole();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        return PostDetailResponseDto.builder()
            .postId(post.getPostId())
            .header(PostDetailResponseDto.HeaderInfo.builder()
                .title(post.getTitle())
                .deadline("D-" + calculateDeadlineDays(post.getDeadline()))
                .createdAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .build())
            .author(PostDetailResponseDto.AuthorInfo.builder()
                .nickname(author.getUser().getNickname())
                .profileImage(author.getUser().getProfileImage())
                .build())
            .category(PostDetailResponseDto.CategoryInfo.builder()
                .totalMembers(post.getTotalMembers())
                .currentMembers(3)
                .mode(mode.getModeName())
                .field(field.getFieldName())
                .startDate(post.getStartDate().format(formatter))
                .positionSkills(positionSkills)
                .googleFormUrl(post.getGoogleFormUrl())
                .kakaoChatUrl(post.getKakaoChatUrl())
                .portfolioUrl(post.getPortfolioUrl())
                .build())
            .content(post.getContent())
            .build();
    }

    private void savePositionsAndSkills(Post post, List<PostRequestDto.PositionRequestDto> positionRequestDtos) {
        for (PostRequestDto.PositionRequestDto positionDto : positionRequestDtos) {
            // Position 찾기
            Position position = getPositionByName(positionDto.getPosition());

            // 기존 PostPosition이 있는지 확인
            PostPosition postPosition = postPositionRepository.findByPostAndPosition(post, position)
                .orElse(PostPosition.builder()
                    .post(post)
                    .position(position)
                    .build());

            postPosition = postPositionRepository.save(postPosition);

            // Skills 저장
            saveSkillsForPosition(post, positionDto.getSkills(), postPosition);
        }
    }

    private void saveSkillsForPosition(Post post, List<String> skills, PostPosition postPosition) {
        for (String skillName : skills) {
            Skill skill = getSkillByName(skillName);

            // 기존 PostSkill이 있는지 확인
            if (!postSkillRepository.existsByPostAndPostPositionAndSkill(post, postPosition, skill)) {
                PostSkill postSkill = PostSkill.builder()
                    .post(post)
                    .postPosition(postPosition)
                    .skill(skill)
                    .build();

                postSkillRepository.save(postSkill);
            }
        }
    }


    // 포지션과 연관된 기술 조회P
    public Map<PositionName, List<SkillName>> getPositionSkills(Long postId) {
        List<PostPosition> postPositions = postPositionRepository.findByPost_PostId(postId);

        return postPositions.stream().collect(Collectors.toMap(
            postPosition -> postPosition.getPosition().getPositionName(),
            postPosition -> postSkillRepository.findByPost_PostIdAndPostPosition_Position_PositionId(
                    postId, (long)postPosition.getPosition().getPositionId())
                .stream()
                .map(postSkill -> postSkill.getSkill().getSkillName())
                .collect(Collectors.toList())
        ));
    }

    // 마감일 계산
    private int calculateDeadlineDays(LocalDate deadline) {
        return Period.between(LocalDate.now(), deadline).getDays();
    }

    // 사용자 조회
    private Users getUserById(Long userId) {
        return usersRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }

    // 역할 생성
    private Role createRole(Users user, TeamRole teamRole) {
        Role role = new Role(user, teamRole);
        return roleRepository.save(role);
    }

    // Post 생성
    private Post buildPost(PostRequestDto postRequestDto, Role role) {
        return Post.builder()
            .title(postRequestDto.getTitle())
            .content(postRequestDto.getContent())
            .totalMembers(postRequestDto.getTotalMembers())
            .expectedPeriod(postRequestDto.getExpectedPeriod())
            .startDate(postRequestDto.getStartDate())
            .deadline(postRequestDto.getDeadline())
            .fieldId(FieldName.fromDisplayName(postRequestDto.getField()).getFieldId())
            .modeId(ModeName.fromDisplayName(postRequestDto.getMode()).getModeId())
            .googleFormUrl(postRequestDto.getGoogleFormUrl())
            .kakaoChatUrl(postRequestDto.getKakaoChatUrl())
            .portfolioUrl(postRequestDto.getPortfolioUrl())
            .role(role)
            .build();
    }

    // 게시글 검증 및 조회
    private Post validateAndGetPost(Long postId, Long userId) {
        Post post = getPostById(postId);
        if (!post.getRole().getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized user for this post.");
        }
        return post;
    }

    private Post getPostById(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));
    }

    private Position getPositionByName(String positionName) {
        return positionRepository.findByPositionName(PositionName.fromDatabaseValue(positionName))
            .orElseThrow(() -> new IllegalArgumentException("Position not found: " + positionName));
    }

    private Skill getSkillByName(String skillName) {
        return skillRepository.findBySkillName(SkillName.fromDatabaseValue(skillName))
            .orElseThrow(() -> new IllegalArgumentException("Skill not found: " + skillName));
    }

    private Field getFieldById(int fieldId) {
        return fieldRepository.findByFieldId(fieldId)
            .orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + fieldId));
    }
    public List<PostListResponseDto> getPostsByRole(Long userId, TeamRole teamRole, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 1. Role 테이블에서 userId로 역할(Role) 조회
        List<Role> roles = roleRepository.findByUser_IdAndTeamRole(userId, teamRole);

        // 2. 역할을 가진 Role의 게시물(Post) 조회
        List<Post> posts = roles.stream()
            .flatMap(role -> role.getPosts().stream())
            .toList();

        // 3. DTO로 변환
        return posts.stream().map(post -> {
            Field field = fieldRepository.findByFieldId(post.getFieldId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid field id: " + post.getFieldId()));

            return PostListResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .field(field.getFieldName()) // FieldName을 변환
                .deadline(post.getDeadline().toString())
                .currentMembers(getCurrentMembers(post.getPostId())) // 현재 인원 가져오기
                .totalMembers(post.getTotalMembers())
                .build();
            }).collect(Collectors.toList());
        }
    private Mode getModeById(int modeId) {
        return modeRepository.findByModeId(modeId)
            .orElseThrow(() -> new IllegalArgumentException("Mode not found with ID: " + modeId));
    }

        //현재 멤버 몇명인지 확인
    private int getCurrentMembers(Long postId) {
        return matchRepository.countByPostIdAndMatchStatus(postId, MatchStatus.ACCEPTED);
    }

    @Transactional(readOnly = true)
    public void validateTeamLeader(Long userId, Long postId) {
        // 사용자가 팀장인지 확인
        Role teamLeaderRole = roleRepository.findTeamLeaderByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("팀장이 아닙니다."));

        // 팀장의 RoleId와 PostId가 일치하는지 확인
        postRepository.findByPostIdAndRoleId(postId, teamLeaderRole.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글의 팀장이 아닙니다."));
    }
}

    private PostListResponseDto convertToPostListResponseDto(Post post) {
        List<PositionName> positions = postPositionRepository.findByPost_PostId(post.getPostId()).stream()
            .map(postPosition -> postPosition.getPosition().getPositionName())
            .collect(Collectors.toList());

        List<SkillName> skills = postSkillRepository.findByPost_PostId(post.getPostId()).stream()
            .map(postSkill -> postSkill.getSkill().getSkillName())
            .collect(Collectors.toList());

        Field field = getFieldById(post.getFieldId());
        Role author = post.getRole();

        return PostListResponseDto.builder()
            .postId(post.getPostId())
            .title(post.getTitle())
            .field(field.getFieldName())
            .position(positions)
            .skill(skills)
            .deadline(post.getDeadline().toString())
            .author(PostListResponseDto.AuthorInfo.builder()
                .nickname(author.getUser().getNickname())
                .profileImage(author.getUser().getProfileImage())
                .build())
            .build();
    }

    public List<PostListResponseDto> getPostListByFilter(PostFilterRequestDto postFilterRequestDto) {
        Pageable pageable = PageRequest.of(
            postFilterRequestDto.getPage() - 1,
            postFilterRequestDto.getSize(),
            Sort.by(Sort.Direction.DESC, "createdAt")
        );

        try {
            log.info("Filter Request received: Field={}, Positions={}, Skills={}, Mode={}, Page={}, Size={}",
                postFilterRequestDto.getField(),
                postFilterRequestDto.getPositions(),
                postFilterRequestDto.getSkills(),
                postFilterRequestDto.getMode(),
                postFilterRequestDto.getPage(),
                postFilterRequestDto.getSize()
            );

            // 필터링된 데이터 가져오기
            List<Post> posts = postRepository.findFilteredPosts(
                postFilterRequestDto.getPositions(),
                postFilterRequestDto.getSkills(),
                postFilterRequestDto.getMode(),
                postFilterRequestDto.getField()
                );

            log.info("Filtered posts count: {}", posts.size());
            posts.forEach(post -> log.debug("Filtered Post: ID={}, Title={}", post.getPostId(), post.getTitle()));

            return posts.stream()
                .map(this::convertToPostListResponseDto)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while filtering posts: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to filter posts. Please check the request and try again.", e);
        }
    }



}