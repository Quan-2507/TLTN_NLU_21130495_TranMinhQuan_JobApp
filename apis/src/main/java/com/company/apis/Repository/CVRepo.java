package com.company.apis.Repository;

import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Entity.CurriculumVitae;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CVRepo extends JpaRepository<CurriculumVitae, Integer> {

    @Query("select j from CurriculumVitae j  Where j.account =:account ORDER BY j.created_at DESC")
    List<CurriculumVitae> findByAccount(Account account);
}
