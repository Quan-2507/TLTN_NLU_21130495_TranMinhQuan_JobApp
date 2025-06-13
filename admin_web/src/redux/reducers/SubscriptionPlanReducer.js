import { GET_SUBSCRIPTION_PLAN_DETAIL, GET_SUBSCRIPTION_PLAN_LIST, GET_SUBSCRIPTION_PLAN_BY_ACCOUNT, GET_SUBSCRIPTION_PLAN_FROM_ADMIN_BY_ID_ACCOUNT } from "../constants";

const initialState = {
    arrSubscriptionPlan: [],
    subscriptionPlanDetail: {},
    subscriptionPlanByAccount: {},
    arrDataSubcriptionPlanFromAdmin: {},
}

export const SubscriptionPlanReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_SUBSCRIPTION_PLAN_LIST:
            state.arrSubscriptionPlan = action.arrSubscriptionPlan;
            return { ...state }
        case GET_SUBSCRIPTION_PLAN_DETAIL: {
            state.subscriptionPlanDetail = action.subscriptionPlanDetail;
            return { ...state }
        }
        case GET_SUBSCRIPTION_PLAN_BY_ACCOUNT: {
            state.subscriptionPlanByAccount = action.subscriptionPlanByAccount;
            return { ...state }
        }
        case GET_SUBSCRIPTION_PLAN_FROM_ADMIN_BY_ID_ACCOUNT: {
            state.arrDataSubcriptionPlanFromAdmin = action.arrDataSubcriptionPlanFromAdmin;
            return { ...state }
        }
        default:
            return { ...state }
    }
}