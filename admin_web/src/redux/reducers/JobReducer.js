import { GET_JOB_DETAIL, GET_JOB_LIST, GET_CV_SAVED, GET_CHART_OF_EMPLOYER, GET_CHART_OF_ADMIN, GET_APPLICATION_BY_JOB, GET_CHART_OF_EMPLOYER_BY_ID } from "../constants";

const initialState = {
    arrJob: [],
    jobDetail: {},
    dataChartOfEmployerById: {},
    chartAdmin: {},
    arrApplication: [],
    chartEmployerFromAdminById: {},
    arrCvSaved: []
}

export const JobReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_JOB_LIST:
            state.arrJob = action.arrJob;
            return { ...state }
        case GET_JOB_DETAIL: {
            state.jobDetail = action.jobDetail;
            return { ...state }
        }
        case GET_CHART_OF_ADMIN:
            state.chartAdmin = action.chartAdmin;
            return { ...state }
        case GET_CHART_OF_EMPLOYER:
            state.dataChartOfEmployerById = action.dataChartOfEmployerById;
            return { ...state }
        case GET_CHART_OF_EMPLOYER_BY_ID:
            state.chartEmployerFromAdminById = action.chartEmployerFromAdminById;
            return { ...state }
        case GET_APPLICATION_BY_JOB:
            state.arrApplication = action.arrApplication;
            return { ...state }
        case GET_CV_SAVED:
            state.arrCvSaved = action.arrCvSaved;
            return { ...state }
        default:
            return { ...state }
    }
}