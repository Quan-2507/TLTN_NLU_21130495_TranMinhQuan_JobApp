package com.company.apis.Models.Entity;

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
@Table(name = "advertisement")
public class Advertisement extends BaseModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "path")
    private String path;
    @Column(name = "image")
    private String image;

    public Advertisement(String path, String image) {
        this.image = image;
        this.path = path;
    }

}
