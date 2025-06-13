package com.company.apis.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.company.apis.Models.Entity.Account;

public interface UserRepo extends JpaRepository<Account, Integer> {

    UserDetails findByEmail(String email);
    Account getAccountByEmail(String email);

    
}
