/* eslint-disable no-useless-constructor */
import { baseService } from "./baseService";

export class CityProvinceService extends baseService {
    constructor() {
        super();
    }

    getCityProvinceList = () => {
        return this.getNotBearer(`/api/general/city_province`);
    }

    getCityProvinceById = (id) => {
        return this.get(`/api/general/city_province/${id}`);
    }

    // getCityProvinceById = (id) => {
    //     return this.get(`/api/cityProvince/${id}`);
    // }

    addNewCityProvince = (formData) => {
        return this.post(`/api/admin/city_province`, formData);
    }

    updateCityProvince = (id, formData) => {
        return this.put(`/api/admin/city_province/${id}`, formData);
    }

    deleteCityProvince = (cityProvinceId) => {
        return this.delete(`/api/admin/city_province/${cityProvinceId}`);
    }
}

export const cityProvinceService = new CityProvinceService();