package com.company.apis.Models.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "level_job")
public class LevelJob extends BaseModel{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "level_id")
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "job_id")
    private Jobs jobs;

    public LevelJob(int id, Level level, Jobs jobs) {
        this.id = id;
        this.level = level;
        this.jobs = jobs;
    }
    public LevelJob( Level level, Jobs jobs) {
        this.level = level;
        this.jobs = jobs;
    }
}
