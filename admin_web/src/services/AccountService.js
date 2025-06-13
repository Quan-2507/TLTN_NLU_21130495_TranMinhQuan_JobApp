/* eslint-disable no-useless-constructor */
import { baseService } from "./baseService";

export class AccountService extends baseService {
    constructor() {
        super();
    }
    getListAccountCandidate = () => {
        return this.get(`/api/admin/account/candidate`);
    };
    getListAccountEmployer = () => {
        return this.get(`/api/admin/account/employer`);
    };
    getListAccountWithoutCompany = () => {
        return this.get(`/api/admin/account/non_company_account`);
    };
    getAccountById = (id) => {
        return this.get(`/api/admin/account/${id}`);
    }
    getEmployerOfCompanyById = (id) => {
        return this.get(`/api/employer/company/${id}`);
    }
    getCompanyForEmployerFromAdminById = (id) => {
        return this.get(`/api/admin/employer_account/${id}`);
    }
    createAccount = (ac) => {
        return this.post(`/api/admin/account`, ac);
    };
    deleteAccount = (id) => {
        return this.delete(`/api/admin/account/${id}`);
    };
    updateAccount = (id, ac) => {
        return this.put(`/api/admin/account/${id}`, ac);
    };
    getCompanyandJob = (token) => {
        return this.get(`/api/employer/account`);
    }

}

export const accountService = new AccountService();


