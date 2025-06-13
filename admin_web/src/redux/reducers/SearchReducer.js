import { GET_SEARCH_COMPANY_JOB_ACCOUNT} from "../constants"

const initialState = {
    arrData: [],
}

export const  SearchReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_SEARCH_COMPANY_JOB_ACCOUNT:
            state.arrData = action.arrData;
            return { ...state }

        default:
            return state
    }
}
