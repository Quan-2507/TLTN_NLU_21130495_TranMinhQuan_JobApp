package com.company.apis.Models.Map;

import com.company.apis.Models.DTO.AccountDTO;
import com.company.apis.Models.DTO.AccountDTOForEmployer;
import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Request.RequestAccount;

public class AccountMapping {
    public static AccountDTO accountDTO(Account a) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(a.getId());
        accountDTO.setName(a.getName());
        accountDTO.setEmail(a.getEmail());
        accountDTO.setPassword(a.getPassword());
        accountDTO.setGender(a.getGender());
        accountDTO.setAddress(a.getAddress());
        accountDTO.setImage(a.getImage());
        accountDTO.setRole(a.getRole());
        return accountDTO;
    }
    public static AccountDTOForEmployer accountDTOForEmployer(Account a) {
        AccountDTOForEmployer accountDTO = new AccountDTOForEmployer();
        accountDTO.setId(a.getId());
        accountDTO.setName(a.getName());
        accountDTO.setEmail(a.getEmail());
        accountDTO.setPassword(a.getPassword());
        accountDTO.setGender(a.getGender());
        accountDTO.setAddress(a.getAddress());
        accountDTO.setImage(a.getImage());
        accountDTO.setRole(a.getRole());
        return accountDTO;
    }

    @SuppressWarnings("static-access")
    public static Account account(RequestAccount a) {
        Account account = new Account();
        account.setName(a.getName());
        account.setEmail(a.getEmail());
        account.setPassword(a.getPassword());
        account.setGender(a.getGender());
        account.setAddress(a.getAddress());
        account.setRole(a.getRole());
        return account;
    }

    public static Account AccountPut(RequestAccount re, Account a) {
        if (re.getName() != null) {
            a.setName(re.getName());
        }
        if (re.getEmail() != null) {
            a.setEmail(re.getEmail());
        }
        if (re.getGender() != 0) {
            a.setGender(re.getGender());
        }
        if (re.getAddress() != null) {
            a.setAddress(re.getAddress());
        }
        if (a.getImage() != null) {
            a.setImage(a.getImage());
        }
        return a;
    }
}
