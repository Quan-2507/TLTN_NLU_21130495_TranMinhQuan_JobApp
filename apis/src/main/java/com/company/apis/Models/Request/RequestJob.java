package com.company.apis.Models.Request;


import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestJob {
    @Nullable
    public  String title;
    @Nullable
    public  String description;
    @Nullable
    public  String reponsibility;
    @Nullable
    public  String skill_required;
    @Nullable
    public  String benefit;
    @Nullable
    public  String interview_steps;
    @Nullable
    public  int amount;
    @Nullable
    public  String experience_required;
    @Nullable
    public  String salary_max;
    @Nullable
    public  String salary_min;
    @Nullable
    public  String start_date;
    @Nullable
    public  String end_date;
    @Nullable
    public  String is_active;
    @Nullable
    public  int gender;

    @Nullable
    public String skill_id;
    @Nullable
    public String level_id;
    @Nullable
    public int company_id;
    // @Nullable
    // public int location_id;
    @Nullable
    public int job_type_id;
}
