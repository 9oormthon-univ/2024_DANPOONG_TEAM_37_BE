package com.example.back.teamate.service;

import com.example.back.teamate.dto.ApplicationRequestDto;
import com.example.back.teamate.dto.ApplicationListResponseDto;
import com.example.back.teamate.dto.RedisUserInfoDto;
import com.example.back.teamate.entity.Application;
import com.example.back.teamate.entity.ApplicationSkill;
import com.example.back.teamate.entity.ApplicationTopic;
import com.example.back.teamate.entity.Field;
import com.example.back.teamate.entity.Role;
import com.example.back.teamate.entity.Skill;
import com.example.back.teamate.entity.Users;
import com.example.back.teamate.enums.FieldName;
import com.example.back.teamate.enums.PositionName;
import com.example.back.teamate.enums.SkillName;
import com.example.back.teamate.enums.TeamRole;
import com.example.back.teamate.repository.ApplicationRepository;
import com.example.back.teamate.repository.ApplicationSkillRepository;
import com.example.back.teamate.repository.ApplicationTopicRepository;
import com.example.back.teamate.repository.FieldRepository;
import com.example.back.teamate.repository.PositionRepository;
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
    private final TokenAuthenticationService tokenAuthenticationService;

    public ApplicationService(ApplicationRepository applicationRepository,
        SkillRepository skillRepository, ApplicationSkillRepository applicationSkillRepository,
        ApplicationTopicRepository applicationTopicRepository, UsersRepository usersRepository,
        RoleRepository roleRepository, FieldRepository fieldRepository,
        TokenAuthenticationService tokenAuthenticationService) {
        this.applicationRepository = applicationRepository;
        this.skillRepository = skillRepository;
        this.applicationSkillRepository = applicationSkillRepository;
        this.applicationTopicRepository = applicationTopicRepository;
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.fieldRepository = fieldRepository;
        this.tokenAuthenticationService = tokenAuthenticationService;
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

    public List<ApplicationListResponseDto> getApplicationsByAuthenticatedUser(String accessToken, int page, int size) {
        RedisUserInfoDto userInfo = tokenAuthenticationService.authenticateUser(accessToken);
        Long userId = userInfo.getId();
        return getApplicationsByUserId(userId, page, size);
    }

    @Transactional(readOnly = true)
    public List<ApplicationListResponseDto> getApplicationsByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));
        Page<Application> applications = applicationRepository.findByUserId(userId, pageable);

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
}
