import { notification } from "antd";
import { OrderDetail } from "../../_core/models/OrderDetail";
import { searchService } from "../../services/SearchService";
import { GET_SEARCH_COMPANY_JOB_ACCOUNT } from "../constants";
import { displayLoadingAction, hideLoadingAction } from './LoadingAction';
import { TOKEN } from "../../util/settings/config";
import { userService } from "../../services/UserService";
import { history } from "../../App";

const userLoginId = null;

let accessToken = {}
if (localStorage.getItem(TOKEN)) {
    accessToken = localStorage.getItem(TOKEN)
}


export const search = (search) => {
    return async (dispatch) => {
        try {
            const result = await searchService.search(search);
            if (result.status === 200) {
                    dispatch({
                        type: GET_SEARCH_COMPANY_JOB_ACCOUNT,
                        arrData: result.data.data,
                    });
            } 
        } catch (error) {
            dispatch({
                type: GET_SEARCH_COMPANY_JOB_ACCOUNT,
                arrData: null,
            });
            // notification.error({
            //     closeIcon: true,
            //     message: "Error",
            //     description: (
            //         <>
            //             Ticket is not found! <br></br>Please re-check your ticket number.
            //         </>
            //     ),
            // });
            console.log(error);
        }
    };
};

