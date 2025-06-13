import { GET_SUBSCRIPTION_PLAN_DETAIL, GET_SUBSCRIPTION_PLAN_LIST, GET_SUBSCRIPTION_PLAN_BY_ACCOUNT, GET_SUBSCRIPTION_PLAN_FROM_ADMIN_BY_ID_ACCOUNT } from "../constants";
import { history } from "../../App";
import { subcriptionPlanService } from "../../services/SubscriptionPlanService";
import { notification } from "antd";


export const getSubscriptionPlanListAction = () => {
    return async (dispatch) => {
        try {
            const result = await subcriptionPlanService.getSubcriptionPlanList();
            if (result.status === 200) {
                dispatch({
                    type: GET_SUBSCRIPTION_PLAN_LIST,
                    arrSubscriptionPlan: result.data
                })
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const getSubscriptionPlanByIdAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await subcriptionPlanService.getSubcriptionPlanById(id);
            dispatch({
                type: GET_SUBSCRIPTION_PLAN_DETAIL,
                subscriptionPlanDetail: result.data.data
            })
        } catch (error) {
            console.log('error', error);
        }
    }
}
export const buyScriptionPlan = (id, price) => {
    return async (dispatch) => {
        try {
            const result = await subcriptionPlanService.getBuySubcriptionPlan(id, price);
            if (result.status === 200) {
                window.open(result.data.data, '_parent').focus();
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}
export const returnBuyScriptionPlan = (paymentId, payerId) => {
    return async (dispatch) => {
        try {
            const result = await subcriptionPlanService.getReturnSubcriptionPlan(paymentId, payerId);
            if (result.status === 200) {
                notification.success({
                    closeIcon: true,
                    message: 'Buy Subscription Plan Success',
                    description: (
                        <>Add new station successfully.</>
                    ),
                })
                window.open("http://localhost:3000/employer/emprofile", '_parent').focus()
            } else {
                notification.error({
                    closeIcon: true,
                    message: 'Buy Subscription Plan Fail. plase try again!!',
                    description: (
                        <>Add new Subcription Plan successfully.</>
                    ),
                })
                history.push("/employer/emprofile")
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const getSubscriptionPlanByAccountAction = (token) => {
    return async (dispatch) => {
        try {
            const result = await subcriptionPlanService.getSubcriptionPlanByAccount(token);
            dispatch({
                type: GET_SUBSCRIPTION_PLAN_BY_ACCOUNT,
                subscriptionPlanByAccount: result.data.data
            })
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const getSubscriptionPlanFromAdminByIdAccountAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await subcriptionPlanService.getSubcriptionPlanFromAdminByIdAccount(id);
            dispatch({
                type: GET_SUBSCRIPTION_PLAN_FROM_ADMIN_BY_ID_ACCOUNT,
                arrDataSubcriptionPlanFromAdmin: result.data.data
            })
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const addSubscriptionPlanAction = (formData) => {
    return async (dispatch) => {
        try {
            const result = await subcriptionPlanService.addNewSubcriptionPlan(formData)
            console.log(result);
            if (result.data.statusCode === 200) {
                notification.success({
                    closeIcon: true,
                    message: 'Success',
                    description: (
                        <>Add new  Subcription Plan successfully.</>
                    ),
                });
                history.push('/admin/subplanmng');
            } else {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>Add new Subcription Plan fail.</>
                    ),
                });
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const updateSubscriptionPlanByIdAction = (id, formData) => {
    return async (dispatch) => {
        try {
            const result = await subcriptionPlanService.updateSubcriptionPlan(id, formData);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Update Subcription Plan successfully</>
                ),
            });
            history.push('/admin/subplanmng');
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const deleteSubscriptionPlanAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await subcriptionPlanService.deleteSubcriptionPlan(id);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Delete Subcription Plan successfully</>
                ),
            });
            dispatch(getSubscriptionPlanListAction())
        } catch (error) {
            if (error?.response?.data?.statusCode === 500) {
                notification.error({
                    closeIcon: false,
                    message: 'Delete Fail !!!',
                    description: (
                        <>Can't Delete Subcription Plan</>
                    ),
                });
                history.push('/admin/subplanmng');
            }
        }
    }
}