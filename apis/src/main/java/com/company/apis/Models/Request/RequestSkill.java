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
public class RequestSkill {
    @Nullable
    public String name;
    @Nullable
    public int industry_id;

}
