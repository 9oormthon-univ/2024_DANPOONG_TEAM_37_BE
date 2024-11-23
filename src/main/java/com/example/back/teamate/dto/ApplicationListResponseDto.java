package com.example.back.teamate.dto;

import com.example.back.teamate.enums.FieldName;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationListResponseDto {
    private Long applicationId;
    private String name;
    private FieldName field;
}