package com.example.tt_nsk.dto;

import com.example.tt_nsk.security.validation.FieldMatch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Artem Kropotov
 * created at 01.06.2022
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldMatch(firstFieldName = "password", secondFieldName = "matchingPassword", message = "The passwords must match")

public class UserDto {
    @JsonIgnore
    private Long id;

    @NotBlank
    @Size(min = 3, message = "username length must be grater than 3 symbols")
    private String username;
    @NotBlank
    @Size(min = 4, message = "password length must be grater than 4 symbols")
    private String password;
    @NotNull(message = "is required")
    @Size(min = 4, message = "password length must be grater than 4 symbols")
    private String matchingPassword;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 5, max = 20)
    private String phone;
}
