package com.riwi.prueba.infraestructure.abstract_services;

import com.riwi.prueba.api.dto.request.SurveyReq;
import com.riwi.prueba.api.dto.response.SurveyResp;

public interface ISurveyService extends CrudService<SurveyReq, SurveyResp, Integer> {
    public final String FIELD_BY_SORT = "title";
}
