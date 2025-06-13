package com.company.apis.Repository;

import com.company.apis.Models.Entity.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobTypeRepo extends JpaRepository<JobType,Integer> {
    @Query("select j from JobType j Where id = ?1 ORDER BY j.created_at DESC")
    JobType findIdJobType(int id);

}
