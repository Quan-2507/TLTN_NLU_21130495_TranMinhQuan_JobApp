package com.company.apis.Models.DTO;

import java.util.Date;

import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Entity.Application;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CVDTO {
    private int id;
    private String file_name;
    private String name;

    private Account account;

    private Application application;
    private Date create_at;

}
