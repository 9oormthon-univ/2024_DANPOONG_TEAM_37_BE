package com.example.back.teamate.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponseDto {
    private Long applicationId;
    private String name;
    private String field;
    private String position;
    private List<String> skills;
    private UserDetailsDto userDetailsDto;
}