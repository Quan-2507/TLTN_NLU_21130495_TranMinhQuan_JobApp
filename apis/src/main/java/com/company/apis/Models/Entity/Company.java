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
@Table(name = "company")
public class Company extends BaseModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false)
    private String name = "";
    @Column(name = "introduction", columnDefinition = "TEXT")
    private String introduction;
    @Column(name = "benefit", columnDefinition = "TEXT")
    private String benefit;
    @Column(name = "profession", columnDefinition = "TEXT")
    private String profession;
    @Column(name = "size")
    private String size;
    @Column(name = "link_website")
    private String link_website;
    @Column(name = "nationnality")
    private String nationnality;
    @Column(name = "logo_image")
    private String logo_image;
    @Column(name = "background_image")
    private String background_image;
    @Column(name = "list_image", columnDefinition = "TEXT")
    private String list_image;
    @Column(name = "enable")
    private int enable;
    @Column(name = "contract")
    private int contract;
    @Column(name = "location")
    private String location;

    @Column(name = "count_job")
    private int count_job = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_province_id")
    private CityProvince cityProvince;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<FollowCompany> followCompany;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Jobs> jobs;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<SubcriptionPlanCompany> subcritionPlanCompanies;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<SkillCompany> skillCompanies;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<SaveCV> saveCVs;

    public Company(int id) {
        this.id = id;
    }

    public Company(int id, String name, String introduction, String benefit, String profession,
            String size, String link_website, String nationnality, String logo_image,
            String background_image, String list_image, int enable, int contract, Account account, String locatiton,
            CityProvince cityProvince,int count_job) {
        this.id = id;
        this.name = name;
        this.introduction = introduction;
        this.benefit = benefit;
        this.profession = profession;
        this.size = size;
        // this.skill = skill;
        this.link_website = link_website;
        this.nationnality = nationnality;
        this.logo_image = logo_image;
        this.background_image = background_image;
        this.list_image = list_image;
        this.enable = enable;
        this.contract = contract;
        this.account = account;
        this.location = locatiton;
        this.cityProvince = cityProvince;
        this.count_job = count_job;
    }
}
