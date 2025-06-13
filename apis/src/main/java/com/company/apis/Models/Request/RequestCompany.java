package com.company.apis.Models.Request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestCompany {
    @Nullable
    public  String name;
    @Nullable
    public  String introduction;
    @Nullable
    public  String benefit;
    @Nullable
    public  String profession;
    @Nullable
    public  String size;
    @Nullable
    public  String link_website;
    @Nullable
    public  String nationnality;
    @Nullable
    public  int enable;
    @Nullable
    public String skill_id;
    @Nullable
    public String level_id;
    @Nullable
    public MultipartFile background;
    @Nullable
    public MultipartFile logo;
    @Nullable
    public String list_image;
    @Nullable
    public int account_id;
    @Nullable
    public String location;
    @Nullable
    public int city_province_id;

}
