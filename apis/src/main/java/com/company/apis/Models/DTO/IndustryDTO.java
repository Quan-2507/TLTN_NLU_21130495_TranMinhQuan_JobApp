package com.company.apis.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IndustryDTO {
    private int id;
    private  String name;
    // private Set<Skill> skills;
    private Set<SkillDTO> skills;// skill dto

}
