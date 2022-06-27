package com.challenge.vgg;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AppRequest {

    private long id;

    @NotNull(message = "dateOfBirth is required.")
    @NotEmpty(message = "dateOfBirth cannot be empty.")
    @Size(min = 10, max = 10, message = "The format of dateOfBirth must be of the form: YYYY-MM-DD")
    private String dateOfBirth;
}