package com.company.apis.Repository;

import com.company.apis.Models.Entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface IndustryRepo extends JpaRepository<Industry,Integer> {
    Industry getIndustriesById(int id);
    @Query("select i from Industry i Where id = ?1 ORDER BY i.created_at DESC")
    Industry findIdIndustry(int id);
}
