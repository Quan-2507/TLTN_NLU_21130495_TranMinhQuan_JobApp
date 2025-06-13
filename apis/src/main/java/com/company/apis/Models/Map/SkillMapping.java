package com.company.apis.Models.Map;

import com.company.apis.Models.DTO.SkillDTO;
import com.company.apis.Models.Entity.*;
import com.company.apis.Models.Request.RequestSkill;

public class SkillMapping {
    public static SkillDTO getSkill(Skill skill) {
        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setId(skill.getId());
        skillDTO.setName(skill.getName());
        skillDTO.setIndustry(IndustryMapping.industryDTO(skill.getIndustry()));
        return skillDTO;
    }

    public static Skill SkillPost(RequestSkill requestSkill) {
        Skill skill = new Skill();
        skill.setName(requestSkill.getName());
        return skill;
    }

    public static Skill SkillPut(RequestSkill requestSkill, Skill skill) {
        if (requestSkill.getName() != null) {
            skill.setName(requestSkill.getName());
        }
        if (requestSkill.getIndustry_id() != 0) {
            skill.setIndustry(new Industry(0));
        }
        return skill;
    }
}
