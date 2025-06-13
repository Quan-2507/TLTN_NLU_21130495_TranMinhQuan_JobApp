package com.company.apis.Models.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "application")
public class Application extends BaseModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "note", length = 1000)
    private String note;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "job_id")
    private Jobs jobs;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cv_id")
    private CurriculumVitae curriculumVitae;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<SaveCV> saveCVs;

    public Application(int id, String note, Account account, Jobs jobs, CurriculumVitae curriculumVitae, Date create_at) {
        this.id = id;
        this.note = note;
        this.account = account;
        this.jobs = jobs;
        this.curriculumVitae = curriculumVitae;
        this.setCreated_at(create_at);
    }
}
