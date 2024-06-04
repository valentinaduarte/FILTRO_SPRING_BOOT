package com.riwi.prueba.infraestructure.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.prueba.api.dto.request.UserReq;
import com.riwi.prueba.api.dto.response.SurveyBasicResp;
import com.riwi.prueba.api.dto.response.UserResp;
import com.riwi.prueba.domain.entities.Survey;
import com.riwi.prueba.domain.entities.User;
import com.riwi.prueba.domain.repositories.UserRepository;
import com.riwi.prueba.infraestructure.abstract_services.IUserService;
import com.riwi.prueba.util.enums.SortType;
import com.riwi.prueba.util.exceptions.BadRequestException;
import com.riwi.prueba.util.messages.ErrorMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class UserService implements IUserService {

    /*Inyección de dependencias */
    @Autowired
    private final UserRepository userRepository;

   
    @Override
    public UserResp create(UserReq request) {
        User user = this.UserReqToEntity(request); 
        /* Lista vacia encuestas */
        user.setSurveys(new ArrayList<>()); 

        return this.userEntityToResponse(this.userRepository.save(user)); 

    }

    @SuppressWarnings("null")
    @Override
    public Page<UserResp> getAll(int page, int size, SortType sortType) {
        if (page < 0)
            page = 0;

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
        User user = this.findUser(id); 

        User userUpdate = this.UserReqToEntity(request); 

        userUpdate.setSurveys(user.getSurveys()); 

        return this.userEntityToResponse(this.userRepository.save(userUpdate)); 
    }

    @Override
    public void delete(Integer id) {
        this.userRepository.delete(this.findUser(id)); 
                                                       
    }


    /* Entidad a response */
    private UserResp userEntityToResponse(User entity) {
   
        List<SurveyBasicResp> surveys = entity.getSurveys() // se obtiene lista de inscripciones
                .stream() 
                .map(this::surveyEntityToResponse) // mapeo de lista de entidades a response
                .collect(Collectors.toList()); // coleccion a lista

        return UserResp.builder()

                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .survey(surveys)
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

   
    private User UserReqToEntity(UserReq request) {
        return User.builder()
                .name(request.getName())
                .password(request.getPassword())
                .email(request.getEmail())
                .active(request.isActive())
                .build();
    }

    
    private User findUser(Integer id) {  
        return this.userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("User")));
    }
}