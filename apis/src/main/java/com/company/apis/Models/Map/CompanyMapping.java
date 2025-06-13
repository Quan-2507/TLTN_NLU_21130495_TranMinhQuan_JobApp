package com.company.apis.Models.Map;

import com.company.apis.Models.DTO.CompanyDTO;
import com.company.apis.Models.DTO.CompanyForEmployerDTO;
import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Entity.Company;
import com.company.apis.Models.Request.RequestCompany;

public class CompanyMapping {
    public static CompanyDTO CompanyDTO(Company c) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(c.getId());
        companyDTO.setName(c.getName());
        companyDTO.setIntroduction(c.getIntroduction());
        companyDTO.setBenefit(c.getBenefit());
        companyDTO.setProfession(c.getProfession());
        companyDTO.setSize(c.getSize());
        // companyDTO.setSkill(c.getSkill());
        companyDTO.setLink_website(c.getLink_website());
        companyDTO.setNationnality(c.getNationnality());
        companyDTO.setLogo_image(c.getLogo_image());
        companyDTO.setBackground_image(c.getBackground_image());
        companyDTO.setList_image(c.getList_image());
        companyDTO.setLocation(c.getLocation());
        companyDTO.setEnable(c.getEnable());
        companyDTO.setAccount(AccountMapping.accountDTO(c.getAccount()));
        if (c.getCityProvince() != null && c.getCityProvince().getDeleted_at() == null) {
            companyDTO.setCity_province(CityProvinceMapping.cityProvinceDTO(c.getCityProvince()));
        }

        return companyDTO;

    }

    public static CompanyForEmployerDTO CompanyForEmployerDTO(Company c) {
        CompanyForEmployerDTO companyDTO = new CompanyForEmployerDTO();
        companyDTO.setId(c.getId());
        companyDTO.setName(c.getName());
        companyDTO.setIntroduction(c.getIntroduction());
        companyDTO.setBenefit(c.getBenefit());
        companyDTO.setProfession(c.getProfession());
        companyDTO.setSize(c.getSize());
        // companyDTO.setSkill(c.getSkill());
        companyDTO.setLink_website(c.getLink_website());
        companyDTO.setNationnality(c.getNationnality());
        companyDTO.setLogo_image(c.getLogo_image());
        companyDTO.setBackground_image(c.getBackground_image());
        companyDTO.setList_image(c.getList_image());
        companyDTO.setLocation(c.getLocation());
        companyDTO.setEnable(c.getEnable());

        return companyDTO;

    }

    public static Company Company(RequestCompany c) {

        Company company = new Company();
        company.setName(c.getName());
        company.setIntroduction(c.getIntroduction());
        company.setBenefit(c.getBenefit());
        company.setProfession(c.getProfession());
        company.setSize(c.getSize());
        company.setLink_website(c.getLink_website());
        company.setNationnality(c.getNationnality());
        // company.setLogo_image(c.getLogo_image());
        // company.setBackground_image(c.getBackground_image());
        company.setLocation(c.getLocation());
        company.setEnable(c.getEnable());
        return company;
    }

    public static Company CompanyPut(RequestCompany rc, Company c) {
        if (rc.getName() != null) {
            c.setName(rc.getName());
        }
        if (rc.getIntroduction() != null) {
            c.setIntroduction(rc.getIntroduction());
        }
        if (rc.getBenefit() != null) {
            c.setBenefit(rc.getBenefit());
        }
        if (rc.getProfession() != null) {
            c.setProfession(rc.getProfession());
        }
        if (rc.getSize() != null) {
            c.setSize(rc.getSize());
        }
        if (rc.getLink_website() != null) {
            c.setLink_website(rc.getLink_website());
        }
        if (rc.getNationnality() != null) {
            c.setNationnality(rc.getNationnality());
        }
        if (c.getLogo_image() != null) {
            c.setLogo_image(c.getLogo_image());
        }
        if (c.getBackground_image() != null) {
            c.setBackground_image(c.getBackground_image());
        }
        if (rc.getEnable() != -1) {
            c.setEnable(rc.getEnable());
        }
        if (rc.getAccount_id() != 0) {
            c.setAccount(new Account(0));
        }
        if (rc.getList_image() != null) {
            c.setList_image(rc.getList_image());
        }
        if (rc.getLocation() != null) {
            c.setLocation(rc.getLocation());
        }
        return c;
    }
}
