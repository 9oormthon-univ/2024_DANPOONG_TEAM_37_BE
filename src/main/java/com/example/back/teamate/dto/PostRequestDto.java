package com.example.back.teamate.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    @NotBlank(message="Title is required.")
    private String title;

    @NotBlank(message="Content is required.")
    private String content;

    @NotNull(message="TotalMembers is required.")
    private int totalMembers;

    @NotNull(message="ExpectedPeriod is required.")
    private int expectedPeriod;

    @NotNull(message="StartDate is required.")
    private LocalDate startDate;

    @NotNull(message="Deadline is required.")
    private LocalDate deadline;

    @NotNull(message = "Mode is required.")
    private String mode;

    @NotNull(message = "Field is required.")
    private String field;

    private String googleFormUrl;

    private String kakaoChatUrl;

    private String portfolioUrl;

    @NotNull(message = "positionList are required.")
    private List<PositionRequestDto> positionList;

    @Getter
    @Setter
    public static class PositionRequestDto { //포지션과 기술스택
        @NotBlank(message = "Position is required.")
        private String position;

        @NotEmpty(message = "Skills are required.")
        private List<String> skills;
    }
}
