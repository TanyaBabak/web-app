package com.babak.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationFormBean {

    private String name;
    private String surname;
    private String email;
    private String login;
    private String password;
    private String passwordConfirmation;
    private String captcha;
}
