/* eslint-disable no-useless-constructor */
import { baseService } from "./baseService";

export class SubcriptionPlanService extends baseService {
    constructor() {
        super();
    }

    getSubcriptionPlanList = () => {
        return this.get(`/api/general/subcription_plan`);
    }

    getSubcriptionPlanById = (id) => {
        return this.get(`/api/general/subcription_plan/${id}`);
    }
    getBuySubcriptionPlan = (id, price) => {
        return this.post(`/api/employer/paypal/pay?id=${id}&price=${price}`);
    }
    getReturnSubcriptionPlan = (paymentId, payerId) => {
        return this.get(`/api/employer/paypal/?paymentId=${paymentId}&PayerID=${payerId}`);
    }

    getSubcriptionPlanByAccount = (token) => {
        return this.get(`/api/employer/subcription_plan`, token);
    }
    getSubcriptionPlanFromAdminByIdAccount = (id) => {
        // http://localhost:8080/api/admin/subcription_plan/employer/2
        return this.get(`/api/admin/subcription_plan/employer/${id}`);
    }
    addNewSubcriptionPlan = (formData) => {
        return this.post(`/api/admin/subcription_plan`, formData);
    }

    updateSubcriptionPlan = (id, formData) => {
        return this.put(`/api/admin/subcription_plan/${id}`, formData);
    }

    deleteSubcriptionPlan = (id) => {
        return this.delete(`/api/admin/subcription_plan/${id}`);
    }
}

export const subcriptionPlanService = new SubcriptionPlanService();