import { GET_INDUSTRY_DETAIL, GET_INDUSTRY_LIST } from "../constants";
import { history } from "../../App";
import { industryService } from "../../services/IndustryService";
import { notification } from "antd";


export const getIndustryListAction = () => {
    return async (dispatch) => {
        try {
            const result = await industryService.getIndustryList();
            // console.log(result);
            if (result.status === 200) {
                dispatch({
                    type: GET_INDUSTRY_LIST,
                    arrIndustry: result.data
                })
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const getIndustryByIdAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await industryService.getIndustryById(id);
            console.log(result);
            console.log(result.data.data);
            dispatch({
                type: GET_INDUSTRY_DETAIL,
                industryDetail: result.data.data
            })
        } catch (error) {
            console.log('error', error);
        }
    }
}


export const addIndustryAction = (formData) => {
    return async (dispatch) => {
        try {
            const result = await industryService.addNewIndustry(formData)
            console.log(result);
            if (result.data.statusCode === 200) {
                notification.success({
                    closeIcon: true,
                    message: 'Success',
                    description: (
                        <>Add new Industry successfully.</>
                    ),
                });
                history.push('/admin/industry');
            } else {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>Add new Industry fail.</>
                    ),
                });
            }


        } catch (error) {
            console.log('error', error);
        }
    }
}

export const updateIndustryByIdAction = (id, formData) => {
    return async (dispatch) => {
        try {
            const result = await industryService.updateIndustry(id, formData);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Update Industry successfully</>
                ),
            });
            history.push('/admin/industry');
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const deleteIndustryAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await industryService.deleteIndustry(id);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Delete Industry successfully</>
                ),
            });
            dispatch(getIndustryListAction())
        } catch (error) {
            console.log('error', error);
        }
    }
}