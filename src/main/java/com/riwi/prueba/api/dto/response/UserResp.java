package com.riwi.prueba.api.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResp {
    private int id;

    private String name;

    private String password;

    private String email;

    private boolean active;


    /*Relaciones con otras tablas */
     /* Relación con tabla "Course" 
    private List<SurveyResp> survey;*/
}
