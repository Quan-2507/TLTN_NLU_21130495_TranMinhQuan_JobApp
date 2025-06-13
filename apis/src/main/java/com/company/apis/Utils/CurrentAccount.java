package com.company.apis.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.apis.Models.Entity.Account;
import com.company.apis.Repository.AccountRepo;

/**
 * CurrentAccount
 */
@Service
public class CurrentAccount {
    @Autowired
    AccountRepo _accountRepo;

    // get account by token with JWT
    public Account getAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() != "anonymousUser") {
            Account account = (Account) auth.getPrincipal();
            return _accountRepo.findIdAccount(account.getId());
        }
        return null;
    }
}