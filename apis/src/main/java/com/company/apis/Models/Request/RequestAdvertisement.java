package com.company.apis.Models.Request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestAdvertisement {
    @Nullable
    public MultipartFile image;
    public String path;
}
