package com.riwi.prueba.api.dto.request;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SurveyReq {
    @NotBlank(message = "the title survey is required")
    @Size(max = 255, message = "the title survey maximum lenght is 255 characters")
    private String title;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "the date can't be in the past")
    private LocalDateTime creationDate;

    @NotNull(message = "the user status is required")
    private Boolean active;

    @NotNull(message = "the user id is required")
    private int id_user;

}
