import {
    GET_ACCOUNT_DETAIL,
    GET_ACCOUNT_LIST,
    GET_EMPLOYER_COMPANY_DETAIL,
    GET_COMPANY_JOB,
    GET_COMPANY_FOR_EMPLOYER_FROM_ADMIN,
    GET_ACCOUNT_CANDIDATE_LIST,
    GET_ACCOUNT_EMPLOYER_LIST,
    GET_ACCOUNT_WITHOUT_COMPANY,

} from "../constants";


const initialState = {
    arrAccount: [],
    arrAccountCandidate: [],
    arrAccountEmployer: [],
    arrAccountWithoutCompany: [],

    accountDetail: {},
    employerCompany: {},
    employerCompanyJob: {},
    dataCompanyForEmployerFromAdmin: {},
    dataCompanyByAccountId: {},
};

export const AccountReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_ACCOUNT_LIST:
            state.arrAccount = action.arrAccount;
            return { ...state };
        case GET_ACCOUNT_CANDIDATE_LIST:
            state.arrAccountCandidate = action.arrAccountCandidate;
            return { ...state };
        case GET_ACCOUNT_EMPLOYER_LIST:
            state.arrAccountEmployer = action.arrAccountEmployer;
            return { ...state };
        case GET_ACCOUNT_WITHOUT_COMPANY:
            state.arrAccountWithoutCompany = action.arrAccountWithoutCompany;
            return { ...state };
        case GET_ACCOUNT_DETAIL:
            state.accountDetail = action.accountDetail;
            return { ...state };

        case GET_EMPLOYER_COMPANY_DETAIL:
            state.employerCompany = action.employerCompany;
            return { ...state };
        case GET_COMPANY_JOB:
            state.employerCompanyJob = action.employerCompanyJob;
            return { ...state };
        case GET_COMPANY_FOR_EMPLOYER_FROM_ADMIN:
            state.dataCompanyForEmployerFromAdmin = action.dataCompanyForEmployerFromAdmin;
            return { ...state };


        // case GET_PROFILE_DETAIL:
        //     state.profile = action.profile;
        //     return { ...state };

        // case GET_CURRENT_USER_ACTION:
        //     state.userLogin = action.userLogin;
        //     return { ...state };

        default:
            return state;
    }
};
