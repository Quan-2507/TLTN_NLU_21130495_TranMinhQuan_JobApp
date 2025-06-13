package com.company.apis.Repository;

import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Entity.Company;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
    Company getCompanyById(int id);

    @Query("select c from Company c Where  c.id = ?1")
    Company findCompanyById(int id);

    @Query("select c from  Company c  where c.account = :account and c.deleted_at IS NULL  ORDER BY c.created_at DESC")
    Company findOneCompanyWithAccount(@Param("account") Account account);

    List<Company> findCompanyByNameContaining(String name);

    @Query("select c from  Company c  where  c.deleted_at IS NULL  ORDER BY c.created_at DESC")
    List<Company> findCompanyByIndustryAndProvinceCity();

    @Query("select c from  Company c  where  c.contract =1  ORDER BY c.created_at DESC")
    List<Company> findCompanyByContract();

}
