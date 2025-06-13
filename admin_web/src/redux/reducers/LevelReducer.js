import { GET_LEVEL_LIST, GET_LEVEL_DETAIL } from "../constants";

const initialState = {
    arrLevel: [],
    levelDetail: {}
}

export const LevelReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_LEVEL_LIST:
            state.arrLevel = action.arrLevel;
            return { ...state }
        case GET_LEVEL_DETAIL: {
            state.levelDetail = action.levelDetail;
            return { ...state }
        }
        default:
            return { ...state }
    }
}
