/* eslint-disable no-useless-constructor */
import { baseService } from "./baseService";

export class LocationService extends baseService {
    constructor() {
        super();
    }
    getListLocation = () => {
        return this.get(`/api/general/location`);
    };
    getLocationById = (id) => {
        return this.get(`/api/general/location/${id}`);
    };
    createLocation = (location) => {
        return this.post(`/api/admin/location`, location);
    };
    deleteLocation = (id) => {
        return this.delete(`/api/admin/location/${id}`);
    };
    updateLocation = (id, location) => {
        return this.put(`/api/admin/location/${id}`, location);
    };
}

export const locationService = new LocationService();