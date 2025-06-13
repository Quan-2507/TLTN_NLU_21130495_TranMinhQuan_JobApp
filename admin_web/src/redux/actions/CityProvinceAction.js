import { GET_CITY_PROVINCE_LIST, GET_CITY_PROVINCE_DETAIL } from "../constants";
import { history } from "../../App";
import { cityProvinceService } from "../../services/CityProvinceService";
import { notification } from "antd";


export const getCityProvinceListAction = () => {
    return async (dispatch) => {
        try {
            const result = await cityProvinceService.getCityProvinceList();
            if (result.status === 200) {
                dispatch({
                    type: GET_CITY_PROVINCE_LIST,
                    arrCityProvince: result.data
                })
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const getCityProvinceByIdAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await cityProvinceService.getCityProvinceById(id);
            dispatch({
                type: GET_CITY_PROVINCE_DETAIL,
                cityProvinceDetail: result.data.data
            })
        } catch (error) {
            console.log('error', error);
        }
    }
}


export const addCityProvinceAction = (formData) => {
    return async (dispatch) => {
        try {
            const result = await cityProvinceService.addNewCityProvince(formData)
            if (result.data.statusCode === 200) {
                notification.success({
                    closeIcon: true,
                    message: 'Success',
                    description: (
                        <>Add new City Province successfully.</>
                    ),
                });
                history.push('/admin/cityprovincemng');
            } else {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>Add new City Province fail.</>
                    ),
                });
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const updateCityProvinceByIdAction = (id, formData) => {
    return async (dispatch) => {
        try {
            const result = await cityProvinceService.updateCityProvince(id, formData);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Update City Province successfully</>
                ),
            });
            history.push('/admin/cityprovincemng');
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const deleteCityProvinceAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await cityProvinceService.deleteCityProvince(id);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Delete City Province successfully</>
                ),
            });
            dispatch(getCityProvinceListAction())
        } catch (error) {
            console.log('error', error);
        }
    }
}