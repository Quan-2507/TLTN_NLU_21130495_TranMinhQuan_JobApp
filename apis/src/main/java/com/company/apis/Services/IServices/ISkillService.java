package com.company.apis.Services.IServices;

import com.company.apis.Models.DTO.SkillDTO;
import com.company.apis.Models.Request.RequestSkill;

import java.util.List;

public interface ISkillService {
    List<SkillDTO> getAll();

    SkillDTO create(RequestSkill requestSkill);

    SkillDTO put(int id, RequestSkill requestSkill);

    SkillDTO getById(int id);

    String delete(int id);
}
