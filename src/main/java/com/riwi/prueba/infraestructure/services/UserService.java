package com.riwi.prueba.infraestructure.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import java.util.*;
import org.springdoc.api.ErrorMessage;
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
import com.riwi.prueba.util.exceptions.BadRequestException;
import com.riwi.prueba.api.dto.request.SurveyReq;
import com.riwi.prueba.api.dto.response.SurveyBasicResp;
import com.riwi.prueba.api.dto.response.SurveyResp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@AllArgsConstructor
@Data
public class UserService implements IUserService{

    /* Inyección de dependencias */
    @Autowired
    private final UserRepository repository;

    /* dependencias */
    @Autowired
    private final UserRepository userRepository;

    /* metodos CRUD */
    @Override
    public UserResp create(UserReq request) {
        User user = this.UserReqToEntity(request); // creacion de la entidad usuario desdel el request

        //user.setSurveys(new ArrayList<>()); // lista vacia de encuestas

        return this.userEntityToResponse(this.userRepository.save(user)); // se regresa un response a partir de la
                                                                          // entidad usuario y se guarda en el
                                                                          // repositorio

    }

    @SuppressWarnings("null")
    @Override
    public Page<UserResp> getAll(int page, int size, SortType sortType) {
        if (page < 0)
            page = 0; /* si es numero negativo, regresar a la pagina 0 */

        PageRequest pagination = null;
        switch (sortType) {
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending()); // organizar de
                                                                                                     // forma ascendente
                                                                                                     // por titulo de
                                                                                                     // lesson
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending()); // organizar de
                                                                                                       // forma
                                                                                                       // descendente
                                                                                                       // por titulo de
                                                                                                       // lesson
        }

        return this.userRepository.findAll(pagination).map(this::userEntityToResponse); // se nesesita devolver un
                                                                                        // response de la entidad

    }

    @Override
    public UserResp getById(Integer id) {
        return this.userEntityToResponse(this.findUser(id)); // se busca el usuario por id y se construye la endidad del
                                                             // response
    }

    @Override
    public UserResp update(UserReq request, Integer id) {
        User user = this.findUser(id); // se busca el usuario por id para actualizar

        User userUpdate = this.UserReqToEntity(request); // se crea la entidad usuario desde el request

       // userUpdate.setSurveys(user.getSurveys()); // se guarda las encuestas

        return this.userEntityToResponse(this.userRepository.save(userUpdate)); // se guarda el usuario actualizado
    }

    @Override
    public void delete(Integer id) {
        this.userRepository.delete(this.findUser(id)); // se busca el usuario con el id ingresado y se elmina del
                                                       // repositorio
    }

    /* Metodos propios */

    /* Entidad a response */
    private UserResp userEntityToResponse(User entity) {
        /* valor a las tablas relacionadas */
        List<SurveyBasicResp> surveys = entity.getSurveys() // se obtiene lista de inscripciones
                .stream() // conversion a coleccion
                .map(this::surveyEntityToResponse) // mapeo de lista de entidades a response
                .collect(Collectors.toList()); // coleccion a lista

        return UserResp.builder()
                // construccion de objeto usuario
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .Surveys(surveys)
                .active(entity.getActive())
                .build();
    }

    private SurveyBasicResp surveyEntityToResponse(Survey entity) {
        return SurveyBasicResp.builder()
                .survey_id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .creationDate(entity.getCreationDate())
                .active(entity.getActive())
                .build();
    }

    /* request a entidad */
    private User UserReqToEntity(UserReq request) {
        return User.builder()
                .name(request.getName())
                .password(request.getPassword())
                .email(request.getEmail())
                .active(request.isActive())
                .build();
    }

    /* buscadores */
    private User findUser(Integer id) { // funcion para buscar usuario
        return this.userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("User")));
    }

}
