import { GET_ADVERTISEMENT_DETAIL, GET_ADVERTISEMENT_LIST } from "../constants";

const initialState = {
    arrAdvertisement: [],
    advertisementDetail: {}
}

export const AdvertisementReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_ADVERTISEMENT_LIST:
            state.arrAdvertisement = action.arrAdvertisement;
            return { ...state }
        case GET_ADVERTISEMENT_DETAIL: {
            state.advertisementDetail = action.advertisementDetail;
            return { ...state }
        }
        default:
            return { ...state }
    }
}
