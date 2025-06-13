package com.company.apis.Models.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "curriculum_vitae")
public class CurriculumVitae extends BaseModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "file_name")
    private String file_name;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "curriculumVitae")
    private List<Application> applications;

    public CurriculumVitae(int id) {
        this.id = id;
    }

    public CurriculumVitae(int id, String file_name, String name,Account account) {
        this.id = id;
        this.file_name = file_name;
        this.name = name;
        this.account = account;

    }
}
