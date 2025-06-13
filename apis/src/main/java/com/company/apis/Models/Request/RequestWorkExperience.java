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
public class RequestWorkExperience {
    @Nullable
    public String name_work_experience;
    @Nullable
    public String content;
    @Nullable
    public String skill_work_experience;
    @Nullable
    public String working_time;
}
