package com.riwi.prueba.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionReq {

    @NotBlank(message = "Text is required")
    private String text;

    @NotNull(message = "Type status is required")
    private String type;

    @NotNull(message = "Status is required")
    private Boolean active;

    @NotNull(message = "Survey id is required")
    private int id_survey;

}