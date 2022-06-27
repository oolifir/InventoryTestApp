package com.airxelerate.inventory.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Please enter username")
    @Size(min = 2, max = 100, message = "Username should be between 2 and 100 characters")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Please enter password")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Please enter role")
    @Column(name = "role_name")
    private String role;
}
