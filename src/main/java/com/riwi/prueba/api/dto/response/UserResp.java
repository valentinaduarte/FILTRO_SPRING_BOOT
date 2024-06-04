package com.riwi.prueba.api.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResp {
    private int user_id;

    private String name;

    private String password;

    private String email;

    private boolean active;


    /*Lista de encuestas para respuesta */
    private List<SurveyResp> survey;
}
