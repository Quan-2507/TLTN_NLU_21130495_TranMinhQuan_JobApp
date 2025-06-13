package com.company.apis.Models.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "follow_job")
public class FollowJob extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "job_id")
    private Jobs jobs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "account_id")
    private Account account;

    public FollowJob(int id, Jobs jobs, Account account, Date create_at) {
        this.id = id;
        this.jobs = jobs;
        this.account = account;
        this.setCreated_at(create_at);
    }
}
