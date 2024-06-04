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
public class OptionQuestionReq {

    @NotBlank(message = "Question option is required")
    private String text;

    @NotNull(message = "User status is required")
    private Boolean active;

    @NotNull(message = "Question id is required")
    private int id_question;
}