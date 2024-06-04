package com.riwi.prueba.infraestructure.abstract_services;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;

import com.riwi.prueba.domain.entities.User;
import com.riwi.prueba.util.enums.SortType;
public interface CrudService<RQ,RS,ID> {
    public RS create(RQ request);

    public Page<RS> getAll(int page, int size, SortType sortType); /*Obtener todos los datos por paginacion */
    public RS getById(ID id);

    public RS update(RQ request, ID id);

    public void delete(ID id);
}