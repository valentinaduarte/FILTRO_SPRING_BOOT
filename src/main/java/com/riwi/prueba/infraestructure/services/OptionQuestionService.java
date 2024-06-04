package com.riwi.prueba.infraestructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.prueba.api.dto.request.OptionQuestionReq;
import com.riwi.prueba.api.dto.response.OptionQuestionResp;
import com.riwi.prueba.domain.entities.OptionQuestion;
import com.riwi.prueba.domain.repositories.OptionQuestionRepository;
import com.riwi.prueba.infraestructure.abstract_services.IOptionQuestionService;
import com.riwi.prueba.util.enums.SortType;
import com.riwi.prueba.util.exceptions.BadRequestException;
import com.riwi.prueba.util.messages.ErrorMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class OptionQuestionService implements IOptionQuestionService {
    /*Inyección de dependencias */
    @Autowired
    private final OptionQuestionRepository optionQuestionRepository;

 
    @Override
    public OptionQuestionResp create(OptionQuestionReq request) {
        OptionQuestion optionQuestion = this.optionQuestionReqToEntity(request);

        return this.optionQuestionEntityToResponse(this.optionQuestionRepository.save(optionQuestion)); 
    }

    @SuppressWarnings("null")
    @Override
    public Page<OptionQuestionResp> getAll(int page, int size, SortType sortType) {
        if (page < 0)
            page = 0; 

        PageRequest pagination = null;
        switch (sortType) {
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending()); 
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending()); 
        }

        return this.optionQuestionRepository.findAll(pagination).map(this::optionQuestionEntityToResponse); 
    }

    @Override
    public OptionQuestionResp getById(Integer id) {
        return this.optionQuestionEntityToResponse(this.finndOptionQuestion(id)); 
    }

    @Override
    public OptionQuestionResp update(OptionQuestionReq request, Integer id) {
        OptionQuestion optionQuestion = this.finndOptionQuestion(id);

        OptionQuestion optionQuestionUpdate = this.optionQuestionReqToEntity(request); 

        optionQuestionUpdate.setQuestion(optionQuestion.getQuestion()); 

        return this.optionQuestionEntityToResponse(this.optionQuestionRepository.save(optionQuestionUpdate));                                                                                                       
    }

    @Override
    public void delete(Integer id) {
        this.optionQuestionRepository.delete(this.finndOptionQuestion(id)); 
    }

    /* Entity to response */
    private OptionQuestionResp optionQuestionEntityToResponse(OptionQuestion entity) {
        return OptionQuestionResp.builder()
                .option_id(entity.getId())
                .text(entity.getText())
                .active(entity.getActive())
                .question_id(entity.getQuestion().getId())
                .build();
    }

    /* req to entidad */
    private OptionQuestion optionQuestionReqToEntity(OptionQuestionReq request) {
        return OptionQuestion.builder()
                .id(request.getId_question())
                .text(request.getText())
                .build();
    }

    /* Buscador */
    private OptionQuestion finndOptionQuestion(Integer id) {
        return this.optionQuestionRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("OptionQuestion")));
    }

}