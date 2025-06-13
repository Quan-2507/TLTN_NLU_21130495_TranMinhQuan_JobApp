/* eslint-disable no-useless-constructor */
import { baseService } from "./baseService";

export class JobTypeService extends baseService {
    constructor() {
        super();
    }

    getJobTypeList = () => {
        return this.get(`/api/general/job_type`);
    }

    getJobTypeById = (id) => {
        return this.get(`/api/general/job_type/${id}`);
    }

    addNewJobType = (formData) => {
        return this.post(`/api/admin/job_type`, formData);
    }

    updateJobType = (id, formData) => {
        return this.put(`/api/admin/job_type/${id}`, formData);
    }

    deleteJobType = (JobTypeId) => {
        return this.delete(`/api/admin/job_type/${JobTypeId}`);
    }
}

export const jobTypeService = new JobTypeService();