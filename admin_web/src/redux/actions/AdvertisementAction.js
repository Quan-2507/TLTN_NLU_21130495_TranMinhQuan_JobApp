import { GET_ADVERTISEMENT_DETAIL, GET_ADVERTISEMENT_LIST } from "../constants";
import { history } from "../../App";
import { notification } from "antd";
import { advertisementService } from "../../services/AdvertisementService";


export const getAdvertisementListAction = () => {
    return async (dispatch) => {
        try {
            const result = await advertisementService.getAdvertisementList();
            if (result.status === 200) {
                dispatch({
                    type: GET_ADVERTISEMENT_LIST,
                    arrAdvertisement: result.data
                })
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const getAdvertisementByIdAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await advertisementService.getAdvertisementById(id);
            dispatch({
                type: GET_ADVERTISEMENT_DETAIL,
                advertisementDetail: result.data.data
            })
        } catch (error) {
            console.log('error', error);
        }
    }
}


export const addAdvertisementAction = (formData) => {
    return async (dispatch) => {
        try {
            const result = await advertisementService.addAdvertisement(formData)
            if (result.data.statusCode === 200) {
                notification.success({
                    closeIcon: true,
                    message: 'Success',
                    description: (
                        <>Add new Advertisement successfully.</>
                    ),
                });
                history.push('/admin/advertisementmng/advertisementmng');
            } else {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>Add new Advertisement fail.</>
                    ),
                });
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const updateAdvertisementIdAction = (id, formData) => {
    return async (dispatch) => {
        try {
            const result = await advertisementService.updateAdvertisement(id, formData);
            console.log(result);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Update AddAdvertisement successfully</>
                ),
            });
            history.push('/admin/advertisementmng/advertisementmng');
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const deleteAdvertisementAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await advertisementService.deleteAdvertisement(id);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Delete Advertisement successfully</>
                ),
            });
            dispatch(getAdvertisementListAction())
        } catch (error) {
            console.log('error', error);
        }
    }
}
