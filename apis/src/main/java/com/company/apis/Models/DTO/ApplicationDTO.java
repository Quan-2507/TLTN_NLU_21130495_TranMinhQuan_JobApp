package com.company.apis.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {
    private int id;
    private String note;
    private AccountDTO account;
    private JobDTO jobs;
    private CVDTO cv;
    private boolean cv_is_save =false;

}