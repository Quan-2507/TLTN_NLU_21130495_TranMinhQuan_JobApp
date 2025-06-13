import { GET_ACCOUNT_CANDIDATE_LIST, GET_ACCOUNT_DETAIL, GET_ACCOUNT_EMPLOYER_LIST, GET_ACCOUNT_LIST, GET_ACCOUNT_WITHOUT_COMPANY, GET_COMPANY_FOR_EMPLOYER_FROM_ADMIN, GET_COMPANY_JOB, GET_EMPLOYER_COMPANY_DETAIL } from "../constants";
import { history } from "../../App";
import { accountService } from "../../services/AccountService";
import { notification } from "antd";

export const getListAccountAction = () => {
    return async (dispatch) => {
        try {
            const result = await accountService.getListAccount();
            if (result.status === 200) {
                dispatch({
                    type: GET_ACCOUNT_LIST,
                    arrAccount: result.data,
                });
            }
        } catch (error) {
            console.log(error);
        }
    };
};

export const getListAccountCandidateAction = () => {
    return async (dispatch) => {
        try {
            const result = await accountService.getListAccountCandidate();
            if (result.status === 200) {
                dispatch({
                    type: GET_ACCOUNT_CANDIDATE_LIST,
                    arrAccountCandidate: result.data,
                });
            }
        } catch (error) {
            console.log(error);
        }
    };
};
export const getListAccountEmployerAction = () => {
    return async (dispatch) => {
        try {
            const result = await accountService.getListAccountEmployer();
            if (result.status === 200) {
                dispatch({
                    type: GET_ACCOUNT_EMPLOYER_LIST,
                    arrAccountEmployer: result.data,
                });
            }
        } catch (error) {
            console.log(error);
        }
    };
};


export const getAccountByIdAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await accountService.getAccountById(id);
            if (result.status === 200) {
                dispatch({
                    type: GET_ACCOUNT_DETAIL,
                    accountDetail: result.data.data,
                });
            }
        } catch (error) {
            console.log(error);
        }
    };
};



export const getCompanyAndJobByTokenAction = (token) => {
    return async (dispatch) => {
        try {
            const result = await accountService.getCompanyandJob(token);
            if (result.status === 200) {
                dispatch({
                    type: GET_COMPANY_JOB,
                    employerCompanyJob: result.data.data,
                });
            }
        } catch (error) {
            console.log(error);
        }
    };
};

export const getEmployerOfCompanyByIAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await accountService.getEmployerOfCompanyById(id);
            if (result.status === 200) {
                dispatch({
                    type: GET_EMPLOYER_COMPANY_DETAIL,
                    employerCompany: result.data.data,
                });
            }
        } catch (error) {
            console.log(error);
        }
    };
};

export const getCompanyForEmployerFromAdminById = (id) => {
    return async (dispatch) => {
        try {
            const result = await accountService.getCompanyForEmployerFromAdminById(id);
            if (result.status === 200) {
                dispatch({
                    type: GET_COMPANY_FOR_EMPLOYER_FROM_ADMIN,
                    dataCompanyForEmployerFromAdmin: result.data.data,
                });
            }
        } catch (error) {
            console.log(error);
        }
    };
};


export const getListAccountWithoutCompanyAction = () => {
    return async (dispatch) => {
        try {
            const result = await accountService.getListAccountWithoutCompany();
            if (result.status === 200) {
                dispatch({
                    type: GET_ACCOUNT_WITHOUT_COMPANY,
                    arrAccountWithoutCompany: result.data,
                });
            }
        } catch (error) {
            console.log(error);
        }
    };
};

export const updateAccountByIdAction = (id, formData) => {
    return async (dispatch) => {
        try {
            const result = await accountService.updateAccount(id, formData);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Update Account successfully</>
                ),
            });
            history.goBack();
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const createAccountAction = (newAc) => {
    return async (dispatch) => {
        try {
            const result = await accountService.createAccount(newAc);
            notification.success({
                closeIcon: true,
                message: "Success",
                description: <>Create New Account Successfully.</>,
            });
            history.goBack();
        } catch (error) {
            if (error?.response?.data?.statusCode === 409) {
                notification.error({
                    closeIcon: true,
                    message: "Error",
                    description: <>Create New Account Fail! . Email Already In Use</>,
                });
            } else {
                notification.error({
                    closeIcon: true,
                    message: "Error",
                    description: <>Create New Account Fail!</>,
                });
            }
        }
    };
};
export const deleteAccountAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await accountService.deleteAccount(id);
            notification.success({
                closeIcon: true,
                message: "Success",
                description: <>Delete Account successfully</>,
            });
            dispatch(getListAccountEmployerAction());
        } catch (error) {
            console.log("error", error);
        }
    };
};