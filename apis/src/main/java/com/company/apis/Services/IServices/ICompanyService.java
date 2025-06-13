package com.company.apis.Services.IServices;

import com.company.apis.Models.DTO.CompanyDTO;
import com.company.apis.Models.Request.RequestCompany;
import com.company.apis.Models.Request.RequestFilterCompany;

import java.util.List;

public interface ICompanyService {
    List<CompanyDTO> getAll(int size, int page);

    CompanyDTO create(RequestCompany requestCompany);

    CompanyDTO put(int id, RequestCompany requestCompany);

    CompanyDTO getById(int id);

    String delete(int id);

    List<CompanyDTO> companyContractAll(int size, int page);

    List<CompanyDTO> CompanyByProvinceAndIndustry(RequestFilterCompany requestFilterCompany, int size, int page);

}
