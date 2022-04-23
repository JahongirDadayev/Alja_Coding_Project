package com.example.alja_coding_project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;

    @Email(message = "You entered an email in error")
    private String email;

    @Size(min = 8, message = "You have created an inconsistent password")
    private String password;
}
