import { GET_JOB_TYPE_LIST, GET_JOB_TYPE_DETAIL } from "../constants";

const initialState = {
    arrJobType: [],
    JobTypeDetail: {}
}

export const JobTypeReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_JOB_TYPE_LIST:
            state.arrJobType = action.arrJobType;
            return { ...state }
        case GET_JOB_TYPE_DETAIL: {
            state.JobTypeDetail = action.JobTypeDetail;
            return { ...state }
        }
        default:
            return { ...state }
    }
}
