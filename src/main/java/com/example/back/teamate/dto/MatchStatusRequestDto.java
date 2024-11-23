package com.example.back.teamate.dto;

import com.example.back.teamate.enums.MatchStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchStatusRequestDto {
    @NotNull(message = "Status cannot be null.")
    private MatchStatus status; // ACCEPTED or REJECTED
}
