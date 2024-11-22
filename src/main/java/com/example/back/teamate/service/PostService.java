package com.example.back.teamate.service;

import com.example.back.teamate.dto.PostRequestDto;
import com.example.back.teamate.entity.Position;
import com.example.back.teamate.entity.Post;
import com.example.back.teamate.entity.PostPosition;
import com.example.back.teamate.entity.PostSkill;
import com.example.back.teamate.entity.Skill;
import com.example.back.teamate.enums.FieldName;
import com.example.back.teamate.enums.ModeName;
import com.example.back.teamate.enums.PositionName;
import com.example.back.teamate.enums.SkillName;
import com.example.back.teamate.repository.FieldRepository;
import com.example.back.teamate.repository.ModeRepository;
import com.example.back.teamate.repository.PositionRepository;
import com.example.back.teamate.repository.PostPositionRepository;
import com.example.back.teamate.repository.PostRepository;
import com.example.back.teamate.repository.PostSkillRepository;
import com.example.back.teamate.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final FieldRepository fieldRepository;
    private final ModeRepository modeRepository;
    private final PositionRepository positionRepository;
    private final SkillRepository skillRepository;
    private final PostPositionRepository postPositionRepository;
    private final PostSkillRepository postSkillRepository;

    public PostService(PostRepository postRepository, FieldRepository fieldRepository, ModeRepository modeRepository,
                        PositionRepository positionRepository, SkillRepository skillRepository,
                        PostPositionRepository postPositionRepository, PostSkillRepository postSkillRepository) {
        this.postRepository = postRepository;
        this.fieldRepository = fieldRepository;
        this.modeRepository = modeRepository;
        this.positionRepository = positionRepository;
        this.skillRepository = skillRepository;
        this.postPositionRepository = postPositionRepository;
        this.postSkillRepository = postSkillRepository;
    }

    public Long createPost(PostRequestDto postRequestDto) {
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

}
