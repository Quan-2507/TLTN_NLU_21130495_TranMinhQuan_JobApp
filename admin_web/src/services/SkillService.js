/* eslint-disable no-useless-constructor */
import { baseService } from "./baseService";

export class SkillService extends baseService {
    constructor() {
        super();
    }
    getListSkill = () => {
        return this.get(`/api/general/skill`);
    };
    getSkillById = (id) => {
        return this.get(`/api/general/skill/${id}`);
    };
    createSkill = (skill) => {
        return this.post(`/api/admin/skill`, skill);
    };
    deleteSkill = (id) => {
        return this.delete(`/api/admin/skill/${id}`);
    };
    updateSkill = (id, skill) => {
        return this.put(`/api/admin/skill/${id}`, skill);
    };
}

export const skillService = new SkillService();