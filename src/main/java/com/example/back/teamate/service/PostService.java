package com.example.back.teamate.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.back.teamate.dto.PostListResponseDto;
import com.example.back.teamate.dto.PostRequestDto;
import com.example.back.teamate.entity.Field;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
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

    public Long createPost(Long userId, PostRequestDto postRequestDto) {
        // 사용자 조회
        Users user = usersRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid userId: " + userId));

        // 역할 생성 및 저장
        Role role = Role.builder()
            .teamRole(TeamRole.TEAM_LEADER)
            .user(user)
            .build();
        roleRepository.save(role);

        // field값을 프로젝트,스터디 중 뭘로 받았는지 확인하고 enum값으로 변환
        FieldName fieldName = FieldName.fromDisplayName(postRequestDto.getField());
        ModeName modeName = ModeName.fromDisplayName(postRequestDto.getMode());

        Post post = new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setTotalMembers(postRequestDto.getTotalMembers());
        post.setExpectedPeriod(postRequestDto.getExpectedPeriod());
        post.setStartDate(postRequestDto.getStartDate());
        post.setDeadline(postRequestDto.getDeadline());
        post.setFieldId(fieldName.getFieldId());
        post.setModeId(modeName.getModeId());
        post.setRole(role);
        Post savedPost = postRepository.save(post);

        //URL 값 저장
        if (postRequestDto.getGoogleFormUrl() != null && !postRequestDto.getGoogleFormUrl().isBlank()) {
            post.setGoogleFormUrl(postRequestDto.getGoogleFormUrl());
        }
        if (postRequestDto.getKakaoChatUrl() != null && !postRequestDto.getKakaoChatUrl().isBlank()) {
            post.setKakaoChatUrl(postRequestDto.getKakaoChatUrl());
        }
        if (postRequestDto.getPortfolioUrl() != null && !postRequestDto.getPortfolioUrl().isBlank()) {
            post.setPortfolioUrl(postRequestDto.getPortfolioUrl());
        }

        //position와 skill DB저장
        for (PostRequestDto.PositionRequestDto positionDto : postRequestDto.getPositionList()) {
            PositionName positionName = PositionName.fromDatabaseValue(positionDto.getPosition());
            Position position = positionRepository.findByPositionName(positionName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid position name: " + positionName));

            PostPosition postPosition = new PostPosition();
            postPosition.setPost(post);
            postPosition.setPosition(position);
            postPositionRepository.save(postPosition);

            for (String skillName : positionDto.getSkills()) {
                SkillName skillEnum = SkillName.fromDatabaseValue(skillName);
                Skill skill = skillRepository.findBySkillName(skillEnum)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid skill name: " + skillName));

                PostSkill postSkill = new PostSkill();
                postSkill.setPost(savedPost);
                postSkill.setSkill(skill);
                postSkill.setPostPosition(postPosition);
                postSkillRepository.save(postSkill);
            }
        }
        return savedPost.getPostId();
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> getPostListAsList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> posts = postRepository.findAll(pageable);

        // Page<Post>를 List<PostListResponseDto>로 변환
        return posts.stream().map(post -> {
            // 1. Position 데이터 조회
            List<PositionName> positions = postPositionRepository.findByPost_PostId(post.getPostId()).stream()
                .map(postPosition -> postPosition.getPosition().getPositionName())
                .collect(Collectors.toList());

            // 2. Skill 데이터 조회
            List<SkillName> skills = postSkillRepository.findByPost_PostId(post.getPostId()).stream()
                .map(postSkill -> postSkill.getSkill().getSkillName())
                .collect(Collectors.toList());

            // 3. Field 데이터 조회
            Field field = fieldRepository.findByFieldId(post.getFieldId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid field id: " + post.getFieldId()));

            // 4. Author 데이터 조회
            Role author = roleRepository.findByPosts_PostId(post.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid role id: " + post.getPostId()));

            // 5. DTO로 변환
            return PostListResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .field(field.getFieldName()) // FieldName을 변환
                .position(positions) // 리스트로 변환한 포지션
                .skill(skills)       // 리스트로 변환한 스킬
                .deadline(post.getDeadline().toString())
                .author(PostListResponseDto.AuthorInfo.builder()
                    .nickname(author.getUser().getNickname())
                    .profileImage(author.getUser().getProfileImage())
                    .build())
                .build();
        }).collect(Collectors.toList());
    }

}
