import { GET_CITY_PROVINCE_DETAIL, GET_CITY_PROVINCE_LIST } from "../constants";

const initialState = {
    arrCityProvince: [],
    cityProvinceDetail: {}
}

export const CityProvinceReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_CITY_PROVINCE_LIST:
            state.arrCityProvince = action.arrCityProvince;
            return { ...state }
        case GET_CITY_PROVINCE_DETAIL: {
            state.cityProvinceDetail = action.cityProvinceDetail;
            return { ...state }
        }
        default:
            return { ...state }
    }
}
