package com.company.apis.Services.IServices;

import com.company.apis.Models.DTO.IndustryDTO;
import com.company.apis.Models.Request.RequestIndustry;

import java.util.List;

public interface IIndustryService {
    List<IndustryDTO> getAll();

    IndustryDTO create(RequestIndustry requestIndustry);

    IndustryDTO put(int id, RequestIndustry requestIndustry);

    String delete(int id);

    IndustryDTO getById(int id);
}
