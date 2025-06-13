/* eslint-disable no-useless-constructor */
import { baseService } from "./baseService";
import { ThongTinDatVe } from '../_core/models/OrderDetail';

export class SearchService extends baseService {
    constructor() {
        super();
    }

    search = (search) =>{
        return this.get(`/api/admin/search/${search}`)
    }
}

export const searchService = new SearchService();