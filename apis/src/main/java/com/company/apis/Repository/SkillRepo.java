package com.company.apis.Repository;

import com.company.apis.Models.Entity.Skill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SkillRepo extends JpaRepository<Skill, Integer> {
    @Query("select s from Skill s Where s.id = ?1 ORDER BY s.created_at DESC")
    Skill findIdSkill(int id);

    @Query("select s from Skill s Where s.name = ?1 ORDER BY s.created_at DESC")
    List<Skill> findSkillByNameContaining(String name);
}
