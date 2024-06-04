package com.riwi.prueba.infraestructure.abstract_services;

import org.springframework.data.domain.Page;

import com.riwi.prueba.domain.entities.User;
import com.riwi.prueba.util.enums.SortType;
public interface CrudService<RQ,RS,ID> {
    public Page<User> getAll(int page, int size, SortType sortType);

    public RS get(ID id);

    public RS create(RQ request);

    public RS update(RQ request, ID id);

    public void delete(ID id);
}
