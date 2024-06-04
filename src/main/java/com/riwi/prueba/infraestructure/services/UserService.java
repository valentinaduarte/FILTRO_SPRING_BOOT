package com.riwi.prueba.infraestructure.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.riwi.prueba.api.dto.request.UserReq;
import com.riwi.prueba.api.dto.response.UserBasicResp;
import com.riwi.prueba.api.dto.response.UserResp;
import com.riwi.prueba.domain.entities.User;
import com.riwi.prueba.domain.repositories.UserRepository;
import com.riwi.prueba.infraestructure.abstract_services.*;
import com.riwi.prueba.util.enums.SortType;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService{
     /* Inyección de dependencias */
    @Autowired
    private final UserRepository repository;

    /* Obtener todos los usuarios y listarlos por orden de "full_name" */
    @Override
    public Page<User> getAll(int page, int size, SortType sortType) {
        /* Se valida que no se tiene un número de página negativo, si es así se asigna como página incial la cero */
        if (page < 0) page = 0;

        /* Creo la variable pagination que será la que dependiendo del tipo de ordenamiento me devolvera la forma de paginación */
        PageRequest pagination = null;

        /* Lógica de clasificación de ordenamiento */
        switch (sortType) {
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }

        /* Retorna todos la respuesta de todos los usuarios organizados según la clasificación asignada por los parametros de entrada */
        return this.repository.findAll(pagination);
    }

    @Override
    public UserResp get(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public UserResp update(UserReq request, Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public UserResp create(UserReq request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }
}