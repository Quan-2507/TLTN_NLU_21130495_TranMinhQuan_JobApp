package com.company.apis.Repository;

import com.company.apis.Models.Entity.Account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepo extends JpaRepository<Account, Integer> {
    Account getAccountById(int id);

    @Query("select a from Account a Where a.id = ?1 ORDER BY a.created_at DESC")
    Account findIdAccount(int id);

    @Query("select a from Account a Where a.role = 1 ORDER BY a.created_at DESC")
    List<Account> findAllEmployer();

    @Query("select a from Account a Where a.role = 0 ORDER BY a.created_at DESC")
    List<Account> findAllCandidate();

    List<Account> findAccountByNameContaining(String name);
}
