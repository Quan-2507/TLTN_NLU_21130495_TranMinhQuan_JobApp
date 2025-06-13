import { GET_COMPANY_DETAIL, GET_COMPANY_LIST, GET_DATA_CHART_BY_COMPANYID_OF_EMPLOYER, GET_FOLLOW_COMPANY_BY_CANDIDATE } from "../constants";

const initialState = {
    arrCompany: [],
    companyDetail: {},
    dataChartByCompanyIdForEmployer: {},
    arrFollowCompany: []
}

export const CompanyReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_COMPANY_LIST:
            state.arrCompany = action.arrCompany;
            return { ...state }
        case GET_COMPANY_DETAIL: {
            state.companyDetail = action.companyDetail;
            return { ...state }
        }
        case GET_DATA_CHART_BY_COMPANYID_OF_EMPLOYER: {
            state.dataChartByCompanyIdForEmployer = action.dataChartByCompanyIdForEmployer;
            return { ...state }
        }
        case GET_FOLLOW_COMPANY_BY_CANDIDATE:
            state.arrFollowCompany = action.arrFollowCompany;
            return { ...state }
        default:
            return { ...state }
    }
}