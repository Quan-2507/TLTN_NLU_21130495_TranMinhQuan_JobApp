import { GET_LOCATION_LIST, GET_LOCATION_DETAIL } from "../constants";

const initialState = {
    arrLocation: [],
    locationDetail: {}
}

export const LocationReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_LOCATION_LIST:
            state.arrLocation = action.arrLocation;
            return { ...state }
        case GET_LOCATION_DETAIL: {
            state.locationDetail = action.locationDetail;
            return { ...state }
        }
        default:
            return { ...state }
    }
}
