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
    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Password is required.")
    private String password;

    @Email(message = "Email is not valid.")
    @Size(
        min = 5, 
        max = 100,
        message = "Email must be between 5 and 100 characters."
    )
    private String email;

 
    @NotNull(message = "Role is required.")
    private boolean active;
}
