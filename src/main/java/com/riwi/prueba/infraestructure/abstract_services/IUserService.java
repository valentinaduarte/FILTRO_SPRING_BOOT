package com.riwi.prueba.infraestructure.abstract_services;
import com.riwi.prueba.api.dto.request.*;
import com.riwi.prueba.api.dto.response.*;

public interface IUserService extends CrudService<UserReq, UserResp, Integer>{
    public final String FIELD_BY_SORT = "full_name";
}
