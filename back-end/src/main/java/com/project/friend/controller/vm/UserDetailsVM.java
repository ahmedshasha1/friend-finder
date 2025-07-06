package com.project.friend.controller.vm;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsVM {
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String password;
    private String phoneNumber;
    private String photo;
    private LocalDateTime lastSeen;
    private Integer friend;
}
