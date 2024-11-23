package com.example.back.teamate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationRequestDto {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Field is required.")
    private String field;

    @NotBlank(message = "Position is required.")
    private String position;

    @NotEmpty(message = "Skill is required.")
    private List<String> skill;

    private List<String> topic;

    private String introduction;
}