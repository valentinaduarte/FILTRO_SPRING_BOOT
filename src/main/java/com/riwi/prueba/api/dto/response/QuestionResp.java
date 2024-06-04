package com.riwi.prueba.api.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResp {

    private int question_id;

    private String text;

    private String type;

    private Boolean active;
    
    private int survey_id;

    private List<OptionQuestionResp> optionQuestions;

}
    