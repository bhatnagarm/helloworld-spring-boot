package com.techartworks.helloworld.library.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginForm {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;

}
