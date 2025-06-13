package com.company.apis.Services.IServices;

import com.company.apis.Models.DTO.AccountDTO;
import com.company.apis.Models.DTO.AccountDTOForEmployer;
import com.company.apis.Models.Request.RequestAccount;

import java.util.List;

public interface IAccountService {
    List<AccountDTO> getAll();

    AccountDTO create(RequestAccount requestAccount);

    AccountDTO put(int id, RequestAccount r);

    AccountDTO updateProfileByAccount( RequestAccount r);
    AccountDTO delete(int id);

    AccountDTO getById(int id);
    AccountDTOForEmployer getAccountCompanyJob(int id);

}
