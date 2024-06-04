package com.riwi.prueba.infraestructure.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.prueba.api.dto.request.SurveyReq;
import com.riwi.prueba.api.dto.response.QuestionBasicResp;
import com.riwi.prueba.api.dto.response.SurveyResp;
import com.riwi.prueba.domain.entities.Question;
import com.riwi.prueba.domain.entities.Survey;
import com.riwi.prueba.domain.repositories.SurveyRepository;
import com.riwi.prueba.infraestructure.abstract_services.ISurveyService;
import com.riwi.prueba.util.enums.SortType;
import com.riwi.prueba.util.exceptions.BadRequestException;
import com.riwi.prueba.util.messages.ErrorMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class SurveyService implements ISurveyService {

    /* Inyección de dependencias */
    @Autowired
    private final SurveyRepository surveyRepository;

 
    @Override
    public SurveyResp create(SurveyReq request) {

        Survey survey = this.surveyReqToEntity(request); // creacion de la entidad pregunta desdel el request

        survey.setQuestions(new ArrayList<>());

        return this.surveyEntityToResponse(this.surveyRepository.save(survey)); // se regresa un response a partir de la
                                                                                // entidad encuesta y se guarda en el
                                                                                // repositorio
    }

    @SuppressWarnings("null")
    @Override
    public Page<SurveyResp> getAll(int page, int size, SortType sortType) {
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

        return this.surveyRepository.findAll(pagination).map(this::surveyEntityToResponse); // se nesesita devolver un
                                                                                            // response de la entidad
    }

    @Override
    public SurveyResp getById(Integer id) {
        return this.surveyEntityToResponse(this.findSurvey(id)); 
    }

    @Override
    public SurveyResp update(SurveyReq request, Integer id) {
        Survey survey = this.findSurvey(id);
        Survey surveyUpdate = this.surveyReqToEntity(request); 

        surveyUpdate.setId(survey.getId());
        surveyUpdate.setQuestions(survey.getQuestions()); 

        return this.surveyEntityToResponse(this.surveyRepository.save(surveyUpdate)); 
    }

    @Override
    public void delete(Integer id) {
        this.surveyRepository.delete(this.findSurvey(id)); 
    }


    /* entidad to response */
    private SurveyResp surveyEntityToResponse(Survey entity) {
        List<QuestionBasicResp> questions = entity.getQuestions() 
                .stream() 
                .map(this::questionBasicEntityToResponse) 
                .collect(Collectors.toList()); 

        return SurveyResp.builder()
                .survey_id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .creationDate(entity.getCreationDate())
                .active(entity.getActive())
                .user_id(entity.getUser().getId())
                .questions(questions)
                .build();
    }

    private QuestionBasicResp questionBasicEntityToResponse(Question entity) {
        return QuestionBasicResp.builder()
                .question_id(entity.getId())
                .text(entity.getText())
                .type(entity.getType())
                .active(entity.getActive())
                .build();
    }

    /* request to entidad */
    private Survey surveyReqToEntity(SurveyReq request) {
        return Survey.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .creationDate(request.getCreationDate())
                .active(request.getActive())
                .build();
    }

    /* Buscador */
    private Survey findSurvey(Integer id) {
        return this.surveyRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("Survey")));
    }

}
