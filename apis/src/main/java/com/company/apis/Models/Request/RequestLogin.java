package com.company.apis.Models.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLogin {
    private String full_name;
    private String email;
    private String password;
}
