package com.company.apis.Models.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "level")
public class Level extends BaseModel{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name",nullable = false)
    private String name="";

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private List<LevelJob> levelJobs;

    public Level(int id) {
        this.id = id;
    }

    public Level(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
