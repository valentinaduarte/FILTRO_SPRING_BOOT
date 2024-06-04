package com.riwi.prueba.api.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyBasicResp {

    private int survey_id;

    private String title;

    private String description;

    private LocalDateTime creationDate;

    private Boolean active;
}