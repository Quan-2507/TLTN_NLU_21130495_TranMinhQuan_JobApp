package com.company.apis.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HttpException extends RuntimeException {
    // set data when service fail -> return status code != 200 for api
    public int StatusCode;
    public String message;
}
