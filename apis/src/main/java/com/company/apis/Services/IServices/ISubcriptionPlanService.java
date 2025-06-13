package com.company.apis.Services.IServices;

import com.company.apis.Models.DTO.SubcriptionPlanDTO;
import com.company.apis.Models.Request.RequestSubcriptionPlan;

import java.util.List;

public interface ISubcriptionPlanService {
    List<SubcriptionPlanDTO> getAll();

    SubcriptionPlanDTO create(RequestSubcriptionPlan requestSubcriptionPlan);

    SubcriptionPlanDTO put(int id, RequestSubcriptionPlan requestSubcriptionPlan);

    String delete(int id);

    SubcriptionPlanDTO getById(int id);
}
