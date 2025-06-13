package com.company.apis.Repository;

import com.company.apis.Models.Entity.Jobs;
import com.company.apis.Models.Entity.Skill;
import com.company.apis.Models.Entity.SkillJob;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SkillJobRepo extends JpaRepository<SkillJob,Integer> {
    @Query("select j from SkillJob j Where j.jobs= :job and j.skills = :skill ")
    SkillJob findByJobAndSkill(@Param("job")Jobs job,@Param("skill") Skill skill);

    @Query("select j from SkillJob j Where j.jobs= :job  ")
    List<SkillJob> findByJob(@Param("job")Jobs job);
}
