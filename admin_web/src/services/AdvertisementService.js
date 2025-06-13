/* eslint-disable no-useless-constructor */
import { baseService } from "./baseService";

export class AdvertisementService extends baseService {
    constructor() {
        super();
    }

    getAdvertisementList = () => {
        return this.get(`/api/general/advertisement`);
    }

    getAdvertisementById = (id) => {
        return this.get(`/api/general/advertisement/${id}`);
    }

    addAdvertisement = (formData) => {
        return this.post(`/api/admin/advertisement`, formData);
    }

    updateAdvertisement = (id, formData) => {
        return this.put(`/api/admin/advertisement/${id}`, formData);
    }

    deleteAdvertisement = (id) => {
        return this.delete(`/api/admin/advertisement/${id}`);
    }
}

export const advertisementService = new AdvertisementService();