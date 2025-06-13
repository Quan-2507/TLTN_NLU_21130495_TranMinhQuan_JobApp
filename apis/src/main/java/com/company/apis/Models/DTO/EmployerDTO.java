package com.company.apis.Models.DTO;

import com.company.apis.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDTO {
    private int id;
    private String address;
    private String email;
    private  String name;
    private  String password;

    private Role role;

}
