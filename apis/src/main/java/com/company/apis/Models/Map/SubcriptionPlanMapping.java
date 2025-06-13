package com.company.apis.Models.Map;

import com.company.apis.Models.DTO.SubcriptionPlanDTO;
import com.company.apis.Models.Entity.SubcriptionPlan;
import com.company.apis.Models.Request.RequestSubcriptionPlan;

public class SubcriptionPlanMapping {
    public static SubcriptionPlanDTO subcriptionPlanDTO(SubcriptionPlan s) {
        SubcriptionPlanDTO subcriptionPlanDTO = new SubcriptionPlanDTO();
        subcriptionPlanDTO.setId(s.getId());
        subcriptionPlanDTO.setName(s.getName());
        subcriptionPlanDTO.setDescription(s.getDescription());
        subcriptionPlanDTO.setPrice(s.getPrice());
        subcriptionPlanDTO.setExpiry(s.getExpiry());
        // subcriptionPlanDTO.setSubcriptionPlanCompanies(s.getSubcritionPlanCompanies());
        return subcriptionPlanDTO;
    }

    public static SubcriptionPlan SubcriptionPlan(RequestSubcriptionPlan rp) {
        SubcriptionPlan subcriptionPlan = new SubcriptionPlan();
        subcriptionPlan.setName(rp.getName());
        subcriptionPlan.setPrice(rp.getPrice());
        subcriptionPlan.setExpiry(rp.getExpiry());
        subcriptionPlan.setDescription(rp.getDescription());

        return subcriptionPlan;
    }

    public static SubcriptionPlan SubcriptionPlanPut(RequestSubcriptionPlan rsp, SubcriptionPlan sp) {
        if (rsp.getName() != null) {
            sp.setName(rsp.getName());
            sp.setPrice(rsp.getPrice());
            sp.setExpiry(rsp.getExpiry());
            sp.setDescription(rsp.getDescription());
        }
        return sp;
    }
}
