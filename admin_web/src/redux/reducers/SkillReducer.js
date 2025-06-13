import { GET_SKILL_DETAIL, GET_SKILL_LIST } from "../constants";

const initialState = {
    arrSkill: [],
    skillDetail: {}
}
export const SkillReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_SKILL_LIST:
            state.arrSkill = action.arrSkill;
            return { ...state }
        case GET_SKILL_DETAIL: {
            state.skillDetail = action.skillDetail;
            return { ...state }
        }
        default:
            return { ...state }
    }
}