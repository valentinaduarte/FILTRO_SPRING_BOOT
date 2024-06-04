package com.riwi.prueba.api.dto.errors;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseErrorResp implements Serializable {
    private Integer code;
    private String status;
}
