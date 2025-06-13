package com.company.apis.Models.DTO;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {
    private int id;
    private  String title;
    private  String description;
    private  String reponsibility;
    private  String skill_required;
    private  String benefit;
    private  String interview_steps;
    private  int amount;
    private  String experience_required;
    private  String salary_max;
    private  String salary_min;
    private  Date start_date;
    private  Date end_date;
    private  boolean is_active;
    private  int gender;
    private  boolean job_is_apply =false;
    private  boolean job_is_save =false;

    private CompanyDTO company;
    private JobTypeDTO jobType;

    private List<SkillDTO> skills;
    private List<LevelDTO> levels;
}
