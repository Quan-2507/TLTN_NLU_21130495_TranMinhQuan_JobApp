package com.company.apis.Repository;

import com.company.apis.Models.Entity.SubcriptionPlan;
import com.company.apis.Models.Entity.SubcriptionPlanCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubcriptionPlanCompanyRepo extends JpaRepository<SubcriptionPlanCompany,Integer> {
    @Query("select sc from SubcriptionPlanCompany sc Where sc.id = ?1 ORDER BY sc.created_at DESC")
    SubcriptionPlanCompany findIdBySubcriptionPlanCompany(int id);
}
