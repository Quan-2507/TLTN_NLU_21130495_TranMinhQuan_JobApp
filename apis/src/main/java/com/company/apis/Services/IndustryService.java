package com.company.apis.Services;

import com.company.apis.Models.DTO.IndustryDTO;
import com.company.apis.Models.Entity.Industry;
import com.company.apis.Models.Map.IndustryMapping;
import com.company.apis.Models.Request.RequestIndustry;
import com.company.apis.Repository.IndustryRepo;
import com.company.apis.Services.IServices.IIndustryService;
import com.company.apis.Utils.Variable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IndustryService implements IIndustryService {
    @Autowired
    IndustryRepo _industryRepo;

    @Override
    public List<IndustryDTO> getAll() {
        List<Industry> industries = _industryRepo.findAll();
        List<IndustryDTO> industryDTOs = new ArrayList<>();
        for (int i = 0; i < industries.size(); i++) {
            if (industries.get(i).getDeleted_at() == null) {
                industryDTOs.add(IndustryMapping.industryDTO(industries.get(i)));
            }
        }
        return industryDTOs;
    }

    @Override
    public IndustryDTO create(RequestIndustry requestIndustry) {
        Industry industry = IndustryMapping.Industry(requestIndustry);
        _industryRepo.save(industry);
        return (IndustryDTO) IndustryMapping.industryDTO(industry);
    }

    @Override
    public IndustryDTO put(int id, RequestIndustry requestIndustry) {
        Industry getIndustry = _industryRepo.findIdIndustry(id);
        boolean checkIndustryNotFound = (getIndustry != null && getIndustry.getDeleted_at() == null) ? false : true;
        if (checkIndustryNotFound) {
            throw Variable.NOT_FOUND;
        }
        Industry industry = IndustryMapping.IndustryPut(requestIndustry, getIndustry);
        industry.setId(id);
        _industryRepo.save(industry);
        return (IndustryDTO) IndustryMapping.industryDTO(industry);
    }

    @Override
    public String delete(int id) {
        Industry industry = _industryRepo.findIdIndustry(id);
        boolean checkIndustryNotFound = (industry != null && industry.getDeleted_at() == null) ? false : true;
        if (checkIndustryNotFound) {
            throw Variable.NOT_FOUND;
        }
        industry.setDeleted_at(new Date());
        _industryRepo.save(industry);
        return "Success";
    }

    @Override
    public IndustryDTO getById(int id) {
        Industry industry = _industryRepo.findIdIndustry(id);
        boolean checkIndustryNotFound = (industry != null && industry.getDeleted_at() == null) ? false : true;
        if (checkIndustryNotFound) {
            throw Variable.NOT_FOUND;
        }
        IndustryDTO industryDTO = IndustryMapping.industryDTO(industry);
        return industryDTO;
    }

}
