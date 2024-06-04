package com.riwi.prueba.infraestructure.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.prueba.api.dto.request.QuestionReq;
import com.riwi.prueba.api.dto.response.OptionQuestionResp;
import com.riwi.prueba.api.dto.response.QuestionResp;
import com.riwi.prueba.domain.entities.OptionQuestion;
import com.riwi.prueba.domain.entities.Question;
import com.riwi.prueba.domain.repositories.QuestionRepository;
import com.riwi.prueba.infraestructure.abstract_services.IQuestionService;
import com.riwi.prueba.util.enums.SortType;
import com.riwi.prueba.util.exceptions.BadRequestException;
import com.riwi.prueba.util.messages.ErrorMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class QuestionService implements IQuestionService {

    /* Inyección de dependencias */
    @Autowired
    private final QuestionRepository questionRepository;

    
    @Override
    public QuestionResp create(QuestionReq request) {
        Question question = this.questionReqToEntity(request); 

        question.setOptionQuestions(new ArrayList<>()); 

        return this.questionEntityToResponse(this.questionRepository.save(question)); 
    }

    @SuppressWarnings("null")
    @Override
    public Page<QuestionResp> getAll(int page, int size, SortType sortType) {
        if (page < 0)
            page = 0; 

        PageRequest pagination = null;
        switch (sortType) {
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending()); 
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending()); 
        }

        return this.questionRepository.findAll(pagination).map(this::questionEntityToResponse); 
    }

    @Override
    public QuestionResp getById(Integer id) {
        return this.questionEntityToResponse(this.findQuestion(id)); 
    }

    @Override
    public QuestionResp update(QuestionReq request, Integer id) {
        Question question = this.findQuestion(id); 

        Question questionUpdate = this.questionReqToEntity(request);

        questionUpdate.setOptionQuestions(question.getOptionQuestions()); 

        return this.questionEntityToResponse(this.questionRepository.save(questionUpdate)); 
    }

    @Override
    public void delete(Integer id) {
        this.questionRepository.delete(this.findQuestion(id)); 
    }


    /* Entidad a response */
    private QuestionResp questionEntityToResponse(Question entity) {

        List<OptionQuestionResp> optionQuestion = entity.getOptionQuestions() 
                .stream() 
                .map(this::optionQuestionEntityToResponse) 
                .collect(Collectors.toList()); 

        return QuestionResp.builder()
                .question_id(entity.getId())
                .text(entity.getText())
                .type(entity.getType())
                .active(entity.getActive())
                .survey_id(entity.getSurvey().getId())
                .optionQuestions(optionQuestion)
                .build();
    }

    private OptionQuestionResp optionQuestionEntityToResponse(OptionQuestion entity) {
        return OptionQuestionResp.builder()
                .option_id(entity.getId())
                .text(entity.getText())
                .active(entity.getActive())
                .question_id(entity.getQuestion().getId())
                .build();
    }

    /* request a entidad */
    private Question questionReqToEntity(QuestionReq request) {
        return Question.builder()
                .text(request.getText())
                .type(request.getType())
                .active(request.getActive())
                .build();
    }

    /* Buscador */
    private Question findQuestion(Integer id) {
        
        return this.questionRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("Question")));
    }
}