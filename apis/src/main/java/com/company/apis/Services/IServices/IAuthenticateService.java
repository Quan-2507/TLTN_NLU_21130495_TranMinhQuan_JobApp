package com.company.apis.Services.IServices;

import com.company.apis.Models.DTO.AccountDTO;
import com.company.apis.Models.DTO.TokenUser;
import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Request.RequestLogin;

public interface IAuthenticateService {
    TokenUser login(RequestLogin requestLogin);

    Account register(RequestLogin requestLogin);

    AccountDTO checkToken(String token);
    AccountDTO checkTokenWeb(String token);



}
