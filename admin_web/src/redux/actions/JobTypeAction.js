import { GET_JOB_TYPE_DETAIL, GET_JOB_TYPE_LIST } from "../constants";
import { history } from "../../App";
import { jobTypeService } from "../../services/JobTypeService";
import { notification } from "antd";


export const getJobTypeListAction = () => {
    return async (dispatch) => {
        try {
            const result = await jobTypeService.getJobTypeList();
            // console.log(result);
            if (result.status === 200) {
                dispatch({
                    type: GET_JOB_TYPE_LIST,
                    arrJobType: result.data
                })
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const getJobTypeByIdAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await jobTypeService.getJobTypeById(id);
            dispatch({
                type: GET_JOB_TYPE_DETAIL,
                JobTypeDetail: result.data.data
            })
        } catch (error) {
            console.log('error', error);
        }
    }
}


export const addJobTypeAction = (formData) => {
    return async (dispatch) => {
        try {
            const result = await jobTypeService.addNewJobType(formData)
            console.log(result);
            if (result.data.statusCode === 200) {
                notification.success({
                    closeIcon: true,
                    message: 'Success',
                    description: (
                        <>Add new Job Type successfully.</>
                    ),
                });
                history.push('/admin/jobtypemng');
            } else {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>Add new Job Type fail.</>
                    ),
                });
            }


        } catch (error) {
            console.log('error', error);
        }
    }
}

export const updateJobTypeByIdAction = (id, formData) => {
    return async (dispatch) => {
        try {
            const result = await jobTypeService.updateJobType(id, formData);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Update Job Type successfully</>
                ),
            });
            history.push('/admin/jobtypemng');
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const deleteJobTypeAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await jobTypeService.deleteJobType(id);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Delete Job Type successfully</>
                ),
            });
            dispatch(getJobTypeListAction())
        } catch (error) {
            console.log('error', error);
        }
    }
}