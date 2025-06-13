/* eslint-disable no-useless-constructor */
import { baseService } from "./baseService";

export class JobService extends baseService {
    constructor() {
        super();
    }
    getListJob = () => {
        return this.get(`/api/general/job`);
    };
    getJobById = (id) => {
        return this.get(`/api/general/job/${id}`);
    };
    createJob = (job) => {
        return this.post(`/api/admin/job`, job);
    };
    deleteJob = (id) => {
        return this.delete(`/api/admin/job/${id}`);
    };
    updateJob = (id, job) => {
        return this.put(`/api/admin/job/${id}`, job);
    };

    createJobEmployer = (job) => {
        return this.post(`/api/employer/job`, job);
    };
    deleteJobforEmployer = (id) => {
        return this.delete(`/api/employer/job/${id}`);
    };
    updateJobForEmployer = (id, job) => {
        return this.put(`/api/employer/job/${id}`, job);
    };
    getChartOfAdmin = () => {
        return this.get(`/api/admin/chart`);
    };
    getChartOfEmployerById = (id) => {
        return this.get(`/api/employer/chart/${id}`);
    };
    getChartOfEmployerFromAdminById = (id) => {
        return this.get(`/api/admin/employer_chart/${id}`);
    };
    getApplicationByJob = (id) => {
        return this.get(`/api/employer/application_by_job?job_id=${id}&size=0&page=0`);
    };
    getCVSave = () => {
        return this.get(`/api/employer/cv_save?size=0&page=0`);
    };
    sendEmailToCandidate = (id) => {
        return this.get(`/api/employer/send_mail?application_id=${id}&size=0&page=0`);
    };
    putEnableOfJobByAdmin = (id) => {
        return this.put(`/api/admin/job/on_or_off/${id}`);
    };
    1
    putEnableOfJobByEmployer = (id) => {
        return this.put(`/api/employer/job/on_or_off/${id}`);
    };
    putEnableOfApplicationByEmployer = (id) => {
        return this.post(`/api/employer/cv_save/${id}`);
    };

}

export const jobService = new JobService();
