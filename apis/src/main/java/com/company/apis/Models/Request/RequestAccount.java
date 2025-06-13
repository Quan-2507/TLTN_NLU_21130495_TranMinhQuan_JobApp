package com.company.apis.Models.Request;

import com.company.apis.Enums.Role;
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
public class RequestAccount {
    @Nullable
    public String name;
    @Nullable
    public String email;
    @Nullable
    public  String password;
    @Nullable
    public  int gender;
    @Nullable
    public  String address;

    @Nullable
    public MultipartFile UploadFile;
    @Nullable
    public Role role;
}
