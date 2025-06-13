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
public class RequestApplication {
    @Nullable
    public String job_id;
    @Nullable
    public String cv_id;

    public String note;
}
