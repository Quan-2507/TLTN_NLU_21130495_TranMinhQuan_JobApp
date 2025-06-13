package com.company.apis.Models.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jobs")
public class Jobs extends BaseModel{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title", columnDefinition = "TEXT", nullable = false)
    private String title="";
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "reponsibility", columnDefinition = "TEXT")
    private String reponsibility;
    @Column(name = "skill_required",columnDefinition = "TEXT")
    private String skill_required;
    @Column(name = "benefit", columnDefinition = "TEXT")
    private String benefit;
    @Column(name = "interview_steps",columnDefinition = "TEXT")
    private String interview_steps;
    @Column(name = "amount")
    private int amount;
    @Column(name = "experience_required",columnDefinition = "TEXT")
    private String experience_required;
    @Column(name = "salary_max")
    private String salary_max;
    @Column(name = "salary_min")
    private String salary_min;
    @Column(name = "start_date")
    private Date start_date;
    @Column(name = "end_date")
    private Date end_date;
    @Column(name = "is_active",nullable = false)
    private boolean is_active=true;
    @Column(name = "gender")
    private int gender;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "job_type_id")
    private JobType jobType;

    @OneToMany(mappedBy = "jobs", cascade = CascadeType.ALL)
    private List<Application> applications;

    @OneToMany(mappedBy = "jobs", cascade = CascadeType.ALL)
    private List<LevelJob> levelJobs;

    @OneToMany(mappedBy = "jobs", cascade = CascadeType.ALL)
    private List<FollowJob> followJobs;

    @OneToMany(mappedBy = "jobs", cascade = CascadeType.ALL)
    private List<ViewedJob> viewedJobs ;

    @OneToMany(mappedBy = "jobs", cascade = CascadeType.ALL)
    private List<SkillJob> skillJobs;

    public Jobs(int id) {
        this.id = id;
    }

    public Jobs(
            int id, String title, String description, String reponsibility,
                String skill_required, String benefit, String interview_steps,
                int amount, String experience_required,
                String salary_max, String salary_min, Date start_date, Date end_date, boolean is_active,
                int gender, Company company,JobType jobType, Date create_at
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reponsibility = reponsibility;
        this.skill_required = skill_required;
        this.benefit = benefit;
        this.interview_steps = interview_steps;
        this.amount = amount;
        this.experience_required = experience_required;
        this.salary_max = salary_max;
        this.salary_min = salary_min;
        this.start_date = start_date;
        this.end_date = end_date;
        this.is_active = is_active;
        this.gender = gender;
        this.company = company;
        this.jobType = jobType;
        this.setCreated_at(create_at);
    }
}
