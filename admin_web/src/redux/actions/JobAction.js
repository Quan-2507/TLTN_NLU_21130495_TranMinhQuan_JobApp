import { GET_JOB_LIST, GET_JOB_DETAIL, GET_CHART_OF_EMPLOYER, GET_CHART_OF_ADMIN, GET_APPLICATION_BY_JOB, GET_CHART_OF_EMPLOYER_BY_ID, GET_CV_SAVED } from "../constants";
import { history } from "../../App";
import { jobService } from "../../services/JobService";
import { notification } from "antd";


export const getJobListAction = () => {
    return async (dispatch) => {
        try {
            const result = await jobService.getListJob();
            console.log(result);
            if (result.status === 200) {
                dispatch({
                    type: GET_JOB_LIST,
                    arrJob: result.data
                })
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const getJobIdAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await jobService.getJobById(id);
            dispatch({
                type: GET_JOB_DETAIL,
                jobDetail: result.data.data
            })
        } catch (error) {
            console.log('error', error);
        }
    }
}


export const addJobAction = (formData) => {
    return async (dispatch) => {
        try {
            const result = await jobService.createJob(formData)
            if (result.data.statusCode === 200) {
                notification.success({
                    closeIcon: true,
                    message: 'Success',
                    description: (
                        <>Add new Job successfully.</>
                    ),
                });
                history.goBack();
            } else {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>Add new Job fail.</>
                    ),
                });
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const addJobOfEmployerAction = (formData) => {
    return async (dispatch) => {
        try {
            const result = await jobService.createJobEmployer(formData)
            if (result.data.statusCode === 200) {
                notification.success({
                    closeIcon: true,
                    message: 'Success',
                    description: (
                        <>Add new Job successfully.</>
                    ),
                });
                history.goBack();
            } else {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>Add new Job fail.</>
                    ),
                });
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}


export const updateJobByIdAction = (id, formData) => {
    return async (dispatch) => {
        try {
            const result = await jobService.updateJob(id, formData);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Update Job successfully</>
                ),
            });
            history.goBack();
        } catch (error) {
            console.log('error', error);
        }
    }
}
export const updateJobByIdForEmployerAction = (id, formData) => {
    return async (dispatch) => {
        try {
            const result = await jobService.updateJobForEmployer(id, formData);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Update Job successfully</>
                ),
            });
            history.goBack();
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const deleteJobAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await jobService.deleteJob(id);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Delete Job successfully</>
                ),
            });
            window.location.reload();
            // history.()
        } catch (error) {
            console.log('error', error);
        }
    }
}

// Get Data chat of Employer By Id
export const getDataChartOfEmployer = (id) => {
    return async (dispatch) => {
        try {
            const result = await jobService.getChartOfEmployerById(id);
            if (result.status === 200) {
                dispatch({
                    type: GET_CHART_OF_EMPLOYER,
                    dataChartOfEmployerById: result.data
                })
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const getDataChartOfEmployerFromAdminById = (id) => {
    return async (dispatch) => {
        try {
            const result = await jobService.getChartOfEmployerFromAdminById(id);
            if (result.status === 200) {
                dispatch({
                    type: GET_CHART_OF_EMPLOYER_BY_ID,
                    chartEmployerFromAdminById: result.data
                })
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}



export const getDataChartOfAdmin = () => {
    return async (dispatch) => {
        try {
            const result = await jobService.getChartOfAdmin();
            if (result.status === 200) {
                dispatch({
                    type: GET_CHART_OF_ADMIN,
                    chartAdmin: result.data
                })
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const getCVSavedAction = () => {
    return async (dispatch) => {
        try {
            const result = await jobService.getCVSave();
            if (result.status === 200) {
                dispatch({
                    type: GET_CV_SAVED,
                    arrCvSaved: result.data
                })
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const getApplicationByJob = (id) => {
    return async (dispatch) => {
        try {
            const result = await jobService.getApplicationByJob(id);
            if (result.status === 200) {
                dispatch({
                    type: GET_APPLICATION_BY_JOB,
                    arrApplication: result.data
                })
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}


export const sendMailToCandidateAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await jobService.sendEmailToCandidate(id);
            console.log(result);
            if (result.status === 200) {
                console.log('data : ', result?.data);
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const updateEnableOfJobByAdmin = (id) => {
    return async (dispatch) => {
        try {
            const result = await jobService.putEnableOfJobByAdmin(id);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>{result.data.data._active ? "Enable successfully" : "Disable successfully"}</>
                ),
            });
            dispatch(getJobListAction())
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const updateEnableOfJobByEmployer = (id) => {
    return async (dispatch) => {
        try {
            const result = await jobService.putEnableOfJobByEmployer(id);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>{result.data.data._active ? "Enable successfully" : "Disable successfully"}</>
                ),
            });
            dispatch(getJobListAction())
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const updateEnableOfApplicationByEmployer = (id, idJob) => {
    return async (dispatch) => {
        try {
            const result = await jobService.putEnableOfApplicationByEmployer(id);
            console.log(result);
            console.log(result.data.data);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>{result.data.data === "Save Success!" ? "Save Success!" : "UnSave Success!"}</>
                ),
            });
            dispatch(getApplicationByJob(idJob))
        } catch (error) {
            console.log('error', error);
        }
    }
}



