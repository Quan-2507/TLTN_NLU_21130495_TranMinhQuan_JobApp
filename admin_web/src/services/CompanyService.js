/* eslint-disable no-useless-constructor */
import { baseService } from "./baseService";

export class CompanyService extends baseService {
    constructor() {
        super();
    }
    getListCompany = () => {
        return this.get(`/api/general/company?size=0&page=0`);
    };
    getFollowCompanyByCandidate = () => {
        return this.get(`/api/employer/candidate_followed_by_company?size=0&page=0`);
    };
    getCompanyById = (id) => {
        return this.get(`/api/general/company/${id}`);
    };
    createCompany = (Company) => {
        return this.post(`/api/admin/company`, Company);
    };
    deleteCompany = (id) => {
        return this.delete(`/api/admin/company/${id}`);
    };
    updateCompany = (id, company) => {
        return this.put(`/api/admin/company/${id}`, company);
    };
    createCompanyForEmployer = (Company) => {
        return this.post(`/api/employer/company`, Company);
    };
    deleteCompanyForEmployer = (id) => {
        return this.delete(`/api/employer/company/${id}`);
    };
    updateCompanyForEmployer = (id, company) => {
        return this.put(`/api/employer/company/${id}`, company);
    };

    updateEnableFromAdmin = (id) => {
        return this.put(`/api/admin/company/on_or_off/${id}`);
    };
    // 'http://localhost:8080/api/general/company/register
    registerCompany = (Company) => {
        return this.postNotBearer(`/api/general/company/register`, Company);
    };
}

export const companyService = new CompanyService();
