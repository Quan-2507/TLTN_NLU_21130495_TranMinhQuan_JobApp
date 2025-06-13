import { GET_LOCATION_LIST, GET_LOCATION_DETAIL } from "../constants";
import { history } from "../../App";
import { locationService } from "../../services/LocationService";
import { notification } from "antd";


export const getLocationListAction = () => {
    return async (dispatch) => {
        try {
            const result = await locationService.getListLocation();
            console.log(result);
            if (result.status === 200) {
                dispatch({
                    type: GET_LOCATION_LIST,
                    arrLocation: result.data
                })
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const getLocationIdAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await locationService.getLocationById(id);
            dispatch({
                type: GET_LOCATION_DETAIL,
                locationDetail: result.data.data
            })
        } catch (error) {
            console.log('error', error);
        }
    }
}


export const addLocationAction = (formData) => {
    return async (dispatch) => {
        try {
            const result = await locationService.createLocation(formData)
            console.log(result);
            if (result.data.statusCode === 200) {
                notification.success({
                    closeIcon: true,
                    message: 'Success',
                    description: (
                        <>Add new Location successfully.</>
                    ),
                });
                history.push('/admin/locationmng');
            } else {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>Add new Location fail.</>
                    ),
                });
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const updateLocationByIdAction = (id, formData) => {
    return async (dispatch) => {
        try {
            const result = await locationService.updateLocation(id, formData);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Update Location successfully</>
                ),
            });
            history.push('/admin/locationmng');
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const deleteLocationAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await locationService.deleteLocation(id);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Delete Location successfully</>
                ),
            });
            dispatch(getLocationListAction())
        } catch (error) {
            console.log('error', error);
        }
    }
}