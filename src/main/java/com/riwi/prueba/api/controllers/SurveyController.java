package com.riwi.prueba.api.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.prueba.api.dto.request.SurveyReq;
import com.riwi.prueba.api.dto.response.SurveyResp;
import com.riwi.prueba.infraestructure.abstract_services.ISurveyService;
import com.riwi.prueba.util.enums.SortType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping(path = "/surveys")
@Data
@AllArgsConstructor
public class SurveyController {
    /* Inyeccion de dependencias */
    @Autowired
    private final ISurveyService surveyService;

   
    @GetMapping
    @Operation(summary = "Obtiene las encuestas de forma paginada y organizada")
    public ResponseEntity<Page<SurveyResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestHeader(required = false) SortType sortType) {
        if (Objects.isNull(sortType)) {
            sortType = SortType.NONE;
        }
        return ResponseEntity.ok(this.surveyService.getAll(page - 1, size, sortType));
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Obtiene una encuesta")
    @ApiResponse(responseCode = "400", description = "Cuando el id no es valido", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    public ResponseEntity<SurveyResp> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.surveyService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Crea una encuesta")
    @ApiResponse(responseCode = "400", description = "Cuando el id no es valido", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    public ResponseEntity<SurveyResp> create(
            @Validated SurveyReq request) {
        return ResponseEntity.ok(this.surveyService.create(request));
    }
}