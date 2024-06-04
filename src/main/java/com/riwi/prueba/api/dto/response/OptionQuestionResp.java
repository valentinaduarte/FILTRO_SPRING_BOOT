package com.riwi.prueba.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionQuestionResp {

    private int option_id;

    private String text;

    private Boolean active;

    private int question_id;

}