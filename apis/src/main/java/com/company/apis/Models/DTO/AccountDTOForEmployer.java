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
public class AccountDTOForEmployer {
    private int id;
    private String name;
    private String email;
    private  String password;
    private  int gender;
    private  String address;
    private  String image;
    private Role role;

    private  int limit_job=0;
    private  int count_jobs=0;

    private  CompanyDTO company;
    private  CompanyForEmployerDTO companyForEmployer;


}
