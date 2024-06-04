package com.riwi.prueba.api.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import com.riwi.prueba.infraestructure.abstract_services.*;
import com.riwi.prueba.api.dto.request.UserReq;
import com.riwi.prueba.api.dto.response.*;
import com.riwi.prueba.domain.entities.User;
import com.riwi.prueba.util.enums.*;


@RestController
@RequestMapping(path = "/users")
@Data
@AllArgsConstructor
public class UserController {
    /* Inyeccion de dependencias */
    @Autowired
    private final IUserService userService;

    /* Peticiones HTTP */
    @GetMapping
    @Operation(summary = "Obtiene los usuarios de forma paginada y organizada")
    public ResponseEntity<Page<UserResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestHeader(required = false) SortType sortType) {
        if (Objects.isNull(sortType)) {
            sortType = SortType.NONE;
        }
        return ResponseEntity.ok(this.userService.getAll(page - 1, size, sortType));
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Obtiene el usuario por id")
    @ApiResponse(responseCode = "400", description = "Cuando el id no es valido", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    public ResponseEntity<UserResp> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.userService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Crea el usuario")
    @ApiResponse(responseCode = "400", description = "Cuando el id no es valido", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    public ResponseEntity<UserResp> create(
            @Validated UserReq request) {
        return ResponseEntity.ok(this.userService.create(request));
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Actualiza el usuario por id")
    @ApiResponse(responseCode = "400", description = "Cuando el id no es valido", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    public ResponseEntity<UserResp> update(
            @PathVariable Integer id, @Validated @RequestBody UserReq request) {
        return ResponseEntity.ok(this.userService.update(request, id));
    }
    
}