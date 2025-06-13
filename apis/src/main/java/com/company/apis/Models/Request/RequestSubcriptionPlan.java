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
public class RequestSubcriptionPlan {
    @Nullable
    public String name;
    @Nullable
    public String description;
    @Nullable
    public Float price;
    @Nullable
    public int expiry;

}


