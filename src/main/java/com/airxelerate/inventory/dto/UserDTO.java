package com.airxelerate.inventory.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDTO {

    @NotEmpty(message = "Please enter username")
    @Size(min = 2, max = 100, message = "Username should be between 2 and 100 characters")
    private String username;

    @NotEmpty(message = "Please enter password")
    private String password;
}
