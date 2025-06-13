import { GET_INDUSTRY_LIST, GET_INDUSTRY_DETAIL } from "../constants";

const initialState = {
    arrIndustry: [],
    industryDetail: {}
}

export const IndustryReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_INDUSTRY_LIST:
            state.arrIndustry = action.arrIndustry;
            return { ...state }
        case GET_INDUSTRY_DETAIL: {
            state.industryDetail = action.industryDetail;
            return { ...state }
        }
        default:
            return { ...state }
    }
}
