package com.company.apis.Models.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubcriptionPlanCompanyDTO {
    private int id;
    private Date start_date;
    private  Date end_date;
//    private CompanyDTO company;
//    private SubcriptionPlanDTO subcriptionPlan;
}
