package com.company.apis.Models.Map;

import com.company.apis.Models.DTO.SubcriptionPlanCompanyDTO;
import com.company.apis.Models.Entity.SubcriptionPlanCompany;

public class SubcriptionPlanCompanyMapping {
    public static SubcriptionPlanCompanyDTO getSubcriptionPlanCompany(SubcriptionPlanCompany sc){
        SubcriptionPlanCompanyDTO subcriptionPlanCompanyDTO = new SubcriptionPlanCompanyDTO();
        subcriptionPlanCompanyDTO.setId(sc.getId());
        subcriptionPlanCompanyDTO.setStart_date(sc.getStart_date());
        subcriptionPlanCompanyDTO.setEnd_date(sc.getEnd_date());
        return subcriptionPlanCompanyDTO;
    }
}
