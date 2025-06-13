/* eslint-disable no-useless-constructor */
import { baseService } from "./baseService";

export class IndustryService extends baseService {
    constructor() {
        super();
    }

    getIndustryList = () => {
        return this.get(`/api/general/industry`);
    }
    addNewIndustry = (formData) => {
        return this.post(`/api/admin/industry`, formData);
    }
    getIndustryById = (id) => {
        return this.get(`/api/general/industry/${id}`);
    }
    updateIndustry = (id, formData) => {
        return this.put(`/api/admin/industry/${id}`, formData);
    }
    deleteIndustry = (industryId) => {
        return this.delete(`/api/admin/industry/${industryId}`);
    }

    // getEnableBusList = () => {
    //     return this.get(`/api/Bus/enablebus`);
    // }
    // getBusById = (id) => {
    //     return this.get(`/api/Bus/${id}`);
    // }

    // updateBus = (id, formData) => {
    //     return this.put(`/api/Bus?Id=${id}`, formData);
    // }

    // deleteBus = (busId) => {
    //     return this.delete(`/api/Bus/${busId}`);
    // }

    // enableBus = (id) => {
    //     return this.put(`/api/Bus/enable?Id=${id}`);
    // }




    //Bus Type
    getBusTypeList = () => {
        return this.get(`/api/BusType`);
    }

    getBusTypeById = (id) => {
        return this.get(`/api/BusType/${id}`);
    }

    addNewBusType = (formData) => {
        return this.post(`/api/BusType`, formData);
    }

    updateBusType = (id, formData) => {
        return this.put(`/api/BusType?Id=${id}`, formData);
    }

    deleteBusType = (busTypeId) => {
        return this.delete(`/api/BusType/${busTypeId}`);
    }
}

export const industryService = new IndustryService();