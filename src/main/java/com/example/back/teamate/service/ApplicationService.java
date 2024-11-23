package com.example.back.teamate.service;

import com.example.back.teamate.dto.ApplicationRequestDto;
import com.example.back.teamate.dto.ApplicationListResponseDto;
import com.example.back.teamate.dto.RedisUserInfoDto;
import com.example.back.teamate.dto.UserDetailsDto;
import com.example.back.teamate.entity.Application;
import com.example.back.teamate.entity.ApplicationSkill;
import com.example.back.teamate.entity.ApplicationTopic;
import com.example.back.teamate.entity.Field;
import com.example.back.teamate.entity.Match;
import com.example.back.teamate.entity.Post;
import com.example.back.teamate.entity.Role;
import com.example.back.teamate.entity.Skill;
import com.example.back.teamate.entity.Users;
import com.example.back.teamate.enums.FieldName;
import com.example.back.teamate.enums.PositionName;
import com.example.back.teamate.enums.SkillName;
import com.example.back.teamate.enums.TeamRole;
import com.example.back.teamate.repository.ApplicationRepository;
import com.example.back.teamate.dto.ApplicationResponseDto;
import com.example.back.teamate.repository.ApplicationSkillRepository;
import com.example.back.teamate.repository.ApplicationTopicRepository;
import com.example.back.teamate.repository.FieldRepository;
import com.example.back.teamate.repository.MatchRepository;
import com.example.back.teamate.repository.PostRepository;
import com.example.back.teamate.repository.RoleRepository;
import com.example.back.teamate.repository.SkillRepository;
import com.example.back.teamate.repository.UsersRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final SkillRepository skillRepository;
    private final ApplicationSkillRepository applicationSkillRepository;
    private final ApplicationTopicRepository applicationTopicRepository;
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final FieldRepository fieldRepository;
    private final PostRepository postRepository;
    private final MatchRepository matchRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
        SkillRepository skillRepository, ApplicationSkillRepository applicationSkillRepository,
        ApplicationTopicRepository applicationTopicRepository, UsersRepository usersRepository,
        RoleRepository roleRepository, FieldRepository fieldRepository,
        PostRepository postRepository, MatchRepository matchRepository) {
        this.applicationRepository = applicationRepository;
        this.skillRepository = skillRepository;
        this.applicationSkillRepository = applicationSkillRepository;
        this.applicationTopicRepository = applicationTopicRepository;
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.fieldRepository = fieldRepository;
        this.postRepository = postRepository;
        this.matchRepository = matchRepository;
    }


    public Long createApplication(Long userId, ApplicationRequestDto applicationRequestDto) {
        // Application 엔티티 생성
        Users user = usersRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid userId: " + userId));

        // 역할 생성 및 저장
        Role role = Role.builder()
            .teamRole(TeamRole.TEAM_MEMBER)
            .user(user)
            .build();
        roleRepository.save(role);

        Application application = Application.builder()
            .name(applicationRequestDto.getName())
            .fieldId(FieldName.fromDisplayName(applicationRequestDto.getField()).getFieldId())
            .positionId(PositionName.fromDisplayName(applicationRequestDto.getPosition()).getPositionId())
            .introduction(applicationRequestDto.getIntroduction())
            .role(role)
            .build();

        // Application 저장
        Application savedApplication = applicationRepository.save(application);

        // Skills 저장
        applicationRequestDto.getSkill().forEach(skill -> {
            SkillName skillName = SkillName.fromDatabaseValue(skill);
            Skill skillEntity = skillRepository.findBySkillName(skillName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid skill name: " + skill));

            ApplicationSkill applicationSkill = ApplicationSkill.builder()
                .application(savedApplication)
                .skill(skillEntity)
                .build();

            applicationSkillRepository.save(applicationSkill);
        });

        // Topics 저장
        applicationRequestDto.getTopic().forEach(topic -> {
            SkillName topicName = SkillName.fromDatabaseValue(topic);
            Skill topicEntity = skillRepository.findBySkillName(topicName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid topic name: " + topic));

            ApplicationTopic applicationTopic = ApplicationTopic.builder()
                .application(savedApplication)
                .topic(topicEntity)
                .build();

            applicationTopicRepository.save(applicationTopic);
        });

        return savedApplication.getId();
    }

    @Transactional(readOnly = true)
    public List<ApplicationListResponseDto> getApplicationsByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));
        // 1. userId로 Role 목록 조회
        List<Role> roles = roleRepository.findByUser_Id(userId);

        // 2. Role 목록으로 Application 조회
        Page<Application> applications = applicationRepository.findByRoleIn(roles, PageRequest.of(page, size));

        return applications.stream().map(application -> {
            Field field = fieldRepository.findByFieldId(application.getFieldId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid field id: " + application.getFieldId()));

            return ApplicationListResponseDto.builder()
                .applicationId(application.getId())
                .name(application.getName())
                .field(field.getFieldName())
                .build();
        }).collect(Collectors.toList());
    }

    // 팀장 확인 로직
    @Transactional
    public void checkIfUserIsTeamLeader(Long userId, Long postId) throws IllegalAccessException {
        Role teamLeaderRole = roleRepository.findByPosts_PostId(postId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid postId: " + postId));

        if (!teamLeaderRole.getTeamRole().equals(TeamRole.TEAM_LEADER) || !teamLeaderRole.getUser().getId().equals(userId)) {
            throw new IllegalAccessException("해당 게시물에 대한 접근 권한이 없습니다.");
        }
    }

    public List<ApplicationResponseDto> getApplicationsByPostId(Long userId, Long postId) throws IllegalAccessException {
        // 팀장 여부 확인
        checkIfUserIsTeamLeader(userId, postId);

        // Match 테이블에서 postId로 관련된 applications 엔티티 가져오기
        List<Application> applications = matchRepository.findApplicationsByPostId(postId);

        // ApplicationResponseDto로 변환
        return applications.stream()
            .map(application -> {
                List<String> skills = applicationSkillRepository.findByApplication(application)
                    .stream()
                    .map(skill -> skill.getSkill().getSkillName().name())
                    .toList();

                return ApplicationResponseDto.builder()
                    .applicationId(application.getId())
                    .name(application.getName())
                    .field(FieldName.fromDatabaseValueForInt(application.getFieldId())
                        .getFieldDisplayName())
                    .position(PositionName.fromDatabaseValueForInt(application.getPositionId())
                        .getPositionDisplayName())
                    .skills(skills)
                    .build();
            }).toList();
    }

}
