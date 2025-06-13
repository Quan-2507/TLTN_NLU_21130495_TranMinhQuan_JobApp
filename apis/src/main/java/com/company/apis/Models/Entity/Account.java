package com.company.apis.Models.Entity;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.company.apis.Enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account extends BaseModel implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private int gender;

    @Column(name = "address")
    private String address;

    @Column(name = "image")
    private String image;

    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<FollowCompany> followCompanies;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<CurriculumVitae> curriculumVitaes;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Application> applications;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Company> companies;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<FollowJob> followJobs;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<ViewedJob> viewedJobs;

    public Account(int id, String name, String email, String password, int gender, String address, String image,
                   Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.address = address;
        this.image = image;
        this.role = role;
    }

    public Account(int id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}