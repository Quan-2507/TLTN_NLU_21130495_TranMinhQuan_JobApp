import { driverService } from "../../services/DriverService"
import { GET_DRIVER_DETAIL, GET_DRIVER_LIST, GET_REGISTER_DRIVER_LIST } from "../constants";
import { history } from '../../App';
import { notification } from "antd";
import { displayLoadingAction, hideLoadingAction } from "./LoadingAction";


export const getDriverAction = () => {
    return async (dispatch) => {
        try {
            const result = await driverService.getDriver();
            if (result.data.status === 200) {
                dispatch({
                    type: GET_DRIVER_LIST,
                    arrDriver: result.data.data
                })
            }
        } catch (error) {
            console.log(error)
        }
    }
}

export const getRegisterDriverAction = () => {
    return async (dispatch) => {
        try {
            const result = await driverService.getRegisterDriver();

            if (result.data.status === 200) {
                dispatch({
                    type: GET_REGISTER_DRIVER_LIST,
                    arrRegisterDriver: result.data.data
                })
            }
        } catch (error) {
            console.log(error)
        }
    }
}

export const getDriverByIdAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await driverService.getDriverById(id);
            dispatch({
                type: GET_DRIVER_DETAIL,
                driverDetail: result.data.data[0],
            })
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const addDriverAction = (newDriver) => {
    return async (dispatch) => {
        try {
            const result = await driverService.postDriver(newDriver);


            if (result.data.status === 200) {
                notification.success({
                    closeIcon: true,
                    message: 'Success',
                    description: (
                        <>Add driver successfully.</>
                    ),
                });
                history.push('/admin/drivermng');
            }
            else if (result.data.status === 409) {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>Create Driver Fail!! Email Existed.</>
                    ),
                });
            }
        } catch (error) {
            notification.error({
                closeIcon: true,
                message: 'Error',
                description: (
                    <>Create Driver Fail, Please try again!!.</>
                ),
            });
        }
    }
}
export const addDriverByUserAction = (newDriver) => {
    return async (dispatch) => {
        try {
            const result = await driverService.postDriver(newDriver);

            if (result.data.status === 200) {
                notification.success({
                    closeIcon: true,
                    message: 'Success',
                    description: (
                        <>Add driver successfully.</>
                    ),
                });
                history.push('/loginDriver');
            }
            else if (result.data.status === 409) {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>Create Driver Fail!! Email Existed.</>
                    ),
                });
            }
        } catch (error) {
            notification.error({
                closeIcon: true,
                message: 'Error',
                description: (
                    <>Create Driver Fail, Please try again!!.</>
                ),
            });
        }
    }
}

export const deleteDriver = (id) => {
    return async (dispatch) => {
        try {
            const result = await driverService.deleteDriver(id);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Delete driver successfully.</>
                ),
            });
            dispatch(getDriverAction())
            dispatch(getRegisterDriverAction())
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const updateDriver = (id, newDriver) => {
    return async (dispatch) => {
        try {
            const result = await driverService.putDriver(id, newDriver)
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Update driver successfully.</>
                ),
            });
            history.push('/admin/drivermng');
        } catch (error) {
            alert("Update Fail . Please Try Again")
        }
    }
}
export const updateByDriverAction = (id, newDriver) => {
    return async (dispatch) => {
        try {
            const result = await driverService.putDriver(id, newDriver)
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Change your password successfully!!.</>
                ),
            });
            history.push('/detailTripOfDriver');
        } catch (error) {
            alert("Update Fail . Please Try Again")
        }
    }
}

export const approveDriver = (id) => {
    return async (dispatch) => {
        try {
            const result = await driverService.approveDriver(id)
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Approve driver successfully.</>
                ),
            });
            dispatch(getRegisterDriverAction())
        } catch (error) {
            alert("Approve Fail . Please Try Again")
        }
    }
}
export const forgetPassword = (emailInfo) => {
    return async (dispatch) => {
        try {
            dispatch(displayLoadingAction);
            const result = await driverService.forgetPassword(emailInfo);
            if (result.data.status === 200) {
                //   dispatch({
                //     type: LAY_LAI_MAT_KHAU_ACTION,
                //     emailInfo: result.data.content,
                //   });
                await dispatch(hideLoadingAction);
                notification.success({
                    closeIcon: false,
                    message: "Success",
                    description: (
                        <>
                            Your new password has been sent to your email, please check your email or spam box and login again.
                        </>
                    ),
                });
                history.replace("loginDriver");
            }
        } catch (error) {
            console.log(error);
            await dispatch(hideLoadingAction);
            alert("Account not existed");
        }
    };
};
export const loginDriverAction = (loginDriverInfo) => {
    return async (dispatch) => {
        try {
            const result = await driverService.loginDriver(loginDriverInfo);
            if (result.data.data.isApprove) {
                if (result.status === 200) {
                    localStorage.setItem("driverId", result.data.data.id);
                    notification.success({
                        closeIcon: true,
                        message: "Success",
                        description: (
                            <>
                                Login successfully.<br />
                                Welcom to DREAM JOB.
                            </>
                        ),
                    });
                    history.push("/detailTripOfDriver");
                } else {
                    await dispatch(hideLoadingAction);
                    history.replace("loginDriver");
                }
            } else {
                notification.error({
                    closeIcon: true,
                    message: "Error",
                    description: (
                        <>
                            Your account has not been approved.<br />
                            Please try again.
                        </>
                    ),
                });
            }

            await dispatch(hideLoadingAction);
        } catch (error) {
            dispatch(hideLoadingAction);
            notification.error({
                closeIcon: true,
                message: "Error",
                description: (
                    <>
                        Wrong account or password!!.<br />
                        Please try again.
                    </>
                ),
            });
        }
    };
};

