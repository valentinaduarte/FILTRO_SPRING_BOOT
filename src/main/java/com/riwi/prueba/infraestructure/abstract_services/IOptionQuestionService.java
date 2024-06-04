package com.riwi.prueba.infraestructure.abstract_services;

import com.riwi.prueba.api.dto.request.OptionQuestionReq;
import com.riwi.prueba.api.dto.response.OptionQuestionResp;

public interface IOptionQuestionService extends CrudService<OptionQuestionReq, OptionQuestionResp, Integer> {

    public final String FIELD_BY_SORT = "text"; 
}