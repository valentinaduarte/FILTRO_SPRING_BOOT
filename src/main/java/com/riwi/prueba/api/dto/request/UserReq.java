package com.riwi.prueba.api.dto.request;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReq {
    @NotBlank(message = "The user's UserName is required.")
    private String username;

    @NotBlank(message = "The user's password is required.")
    private String password;

    @Email(message = "The email is not valid.")
    @Size(
        min = 5, 
        max = 100,
        message = "The email must be between 5 and 100 characters."
    )
    private String email;

 
    /*@NotNull(message = "The user role is required.")
    private Role role;**/
}
