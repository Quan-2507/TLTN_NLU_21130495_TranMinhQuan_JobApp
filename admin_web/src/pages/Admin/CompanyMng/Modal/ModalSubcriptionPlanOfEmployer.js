import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";

import { getSubscriptionPlanFromAdminByIdAccountAction } from "../../../../redux/actions/SubscriptionPlanAction";
import dayjs from "dayjs";



const ModalSubcriptionPlanOfEmployer = (props) => {
    const dispatch = useDispatch();
    const { arrDataSubcriptionPlanFromAdmin } = useSelector(state => state.SubscriptionPlanReducer)
    const id = props.idEmployer;

    useEffect(() => {
        dispatch(getSubscriptionPlanFromAdminByIdAccountAction(id))
    }, [dispatch, id])

    const data = arrDataSubcriptionPlanFromAdmin?.subcriptionPlanDTO;

    return <div>
        <div className="flex flex-col gap-2">
            <div className="flex items-center text-gray-500 mt-2">
                <h2>Name : </h2>
                <h4 className="ml-2">{data?.name}</h4>
            </div>

            <div className="flex items-center text-gray-500">
                <h2>Description : </h2>
                <h4 className="ml-2">{data?.description}</h4>
            </div>

            <div className="flex items-center text-gray-500">
                <div className="flex items-center">
                    <h2>From Date : </h2>
                    <h4 className="ml-2">{dayjs(data?.start_date).format("DD-MM-YYYY")} </h4>
                </div>
                <div className="flex items-center ml-5">
                    <h2>To Date : </h2>
                    <h4 className="ml-2">{dayjs(data?.end_date).format("DD-MM-YYYY")} </h4>
                </div>
            </div>
        </div>

    </div>

};

export default ModalSubcriptionPlanOfEmployer;