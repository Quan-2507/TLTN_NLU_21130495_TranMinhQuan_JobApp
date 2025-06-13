package com.company.apis.Models.Request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDataCreateCV {

    // candidate
    @Nullable
    public String name_candidate;
    @Nullable
    public String name_cv;
    @Nullable
    public String decription;
    @Nullable
    public String email;
    @Nullable
    public String address;
    @Nullable
    public String phone;
    @Nullable
    public String skill;
    // school
    @Nullable
    public String name_school;
    @Nullable
    public String year;
    @Nullable
    public String major;
    //
    @Nullable
    public List<RequestWorkExperience> work_experiences;

}
