package com.company.apis.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData <T>{
    // data return when status 200
    public int statusCode;
    public String message;
    public String error;
    public T data;
}
