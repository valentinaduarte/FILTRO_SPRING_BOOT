package com.riwi.prueba.infraestructure.abstract_services;

import com.riwi.prueba.api.dto.request.QuestionReq;
import com.riwi.prueba.api.dto.response.QuestionResp;

public interface IQuestionService extends CrudService<QuestionReq, QuestionResp, Integer> {

    public final String FIELD_BY_SORT = "text"; /* Variable a utilizar para la paginacion */
}