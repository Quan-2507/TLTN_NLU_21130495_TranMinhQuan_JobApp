/* eslint-disable no-useless-constructor */
import { baseService } from "./baseService";

export class LevelService extends baseService {
    constructor() {
        super();
    }

    getLevelList = () => {
        return this.get(`/api/general/level`);
    }

    getLevelById = (id) => {
        return this.get(`/api/general/level/${id}`);
    }

    addNewLevel = (formData) => {
        return this.post(`/api/admin/level`, formData);
    }

    updateLevel = (id, formData) => {
        return this.put(`/api/admin/level/${id}`, formData);
    }

    deleteLevel = (levelId) => {
        return this.delete(`/api/admin/level/${levelId}`);
    }
}

export const levelService = new LevelService();