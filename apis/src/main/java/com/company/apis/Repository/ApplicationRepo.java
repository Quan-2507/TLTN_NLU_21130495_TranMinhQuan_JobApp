package com.company.apis.Repository;

import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Entity.Application;
import com.company.apis.Models.Entity.Jobs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicationRepo extends JpaRepository<Application,Integer> {
    List<Application> findByAccount(Account account);
    @Query("select v from Application v Where  account = :account and jobs = :job ORDER BY v.created_at DESC")
    Application findByAccountAndJobs(@Param("account") Account account, @Param("job") Jobs job);



    @Query("select j from Application j  Where j.jobs =:job and j.deleted_at = null ORDER BY j.created_at DESC")
    List<Application> findByJob(@Param("job") Jobs job);
}
