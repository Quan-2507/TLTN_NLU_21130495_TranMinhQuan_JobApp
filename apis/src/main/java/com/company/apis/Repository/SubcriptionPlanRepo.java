package com.company.apis.Repository;

import com.company.apis.Models.Entity.SubcriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubcriptionPlanRepo extends JpaRepository<SubcriptionPlan,Integer> {
    @Query("select sp from SubcriptionPlan sp Where id = ?1 ORDER BY sp.created_at DESC")
    SubcriptionPlan findIdBySubcriptionPlan(int id);
}
