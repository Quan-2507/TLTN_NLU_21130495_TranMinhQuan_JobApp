import React, { useEffect, useState } from "react";
import { Modal, Table, Tabs } from "antd";
import { useDispatch, useSelector } from "react-redux";
import TabPane from "antd/es/tabs/TabPane";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    BarElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js'
import { Bar, Line } from 'react-chartjs-2'


import { getDataChartOfEmployer, getDataChartOfEmployerFromAdminById } from '../../../redux/actions/JobAction';
import { getCurrentUserAction } from '../../../redux/actions/UserAction';
import { TOKEN } from "../../../util/settings/config";


ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    BarElement,
    LineElement,
    Title,
    Tooltip,
    Legend
)

const ChartOfEmployer = (props) => {
    const dispatch = useDispatch();
    const idOfEmployer = props?.idOfEmployer?.id
    let { userLogin } = useSelector(state => state.UserReducer);
    let { dataChartOfEmployerById } = useSelector(state => state.JobReducer);
    let { chartEmployerFromAdminById } = useSelector(state => state.JobReducer);
    // console.log(dataChartOfEmployerById);
    // console.log(dataChartOfEmployerById?.data?.price_for_subcription_plan);
    let accessToken = {}
    if (localStorage.getItem(TOKEN)) {
        accessToken = localStorage.getItem(TOKEN)
    }

    useEffect(() => {
        dispatch(getCurrentUserAction(accessToken));
    }, [dispatch])


    useEffect(() => {
        if (userLogin?.role === "EMPLOYER") {
            dispatch(getDataChartOfEmployer(userLogin?.id))
        } else if (userLogin?.role === "ADMIN") {
            dispatch(getDataChartOfEmployerFromAdminById(idOfEmployer))

        }
    }, [userLogin])



    // Data chart
    const labels = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    const dataChartOfEmployerFromAdmin = {
        labels: labels,
        datasets: [
            {
                label: "Price Subcription Plan By Month",
                backgroundColor: "rgb(255, 99, 132)",
                borderColor: "rgb(255, 99, 132)",
                data: chartEmployerFromAdminById?.data?.price_for_subcription_plan || dataChartOfEmployerById?.data?.price_for_subcription_plan,

            },
        ],
    };

    const dataChartLineOfEmployerFromAdmin = {
        labels: labels,
        datasets: [
            {
                label:
                    "Job Applicated By Month ",
                backgroundColor: "rgb(255, 99, 132)",
                borderColor: "rgb(255, 99, 132)",
                tension: 0.4,
                data: chartEmployerFromAdminById?.data?.number_of_job_applicated || dataChartOfEmployerById?.data?.number_of_job_applicated,
            },
            {
                label: "Job Save By Month",
                backgroundColor: "rgb(75, 192, 192)",
                borderColor: "rgb(75, 192, 192)",
                data: chartEmployerFromAdminById?.data?.number_of_job_saved || dataChartOfEmployerById?.data?.number_of_job_saved,
                tension: 0.4,

            },
            {
                label: "Job Viewed By Month",
                backgroundColor: "rgb(255, 205, 86)",
                borderColor: "rgb(255, 205, 86)",
                data: chartEmployerFromAdminById?.data?.number_of_job_viewed || dataChartOfEmployerById?.data?.number_of_job_viewed,
                tension: 0.4,

            }
        ]
    };

    return (
        <div>
            <div className="grid gap-4 grid-cols-2">
                <div className="rounded-xl p-8 mr-4 border-2 border-gray-300 ">
                    <div className="mb-10 font-bold">Manager Price Of Subcription Plan </div>
                    <Bar height={100} data={dataChartOfEmployerFromAdmin} />
                </div>
                <div className="rounded-xl p-8 ml-4 border-2 border-gray-300 ">
                    <div className="mb-10 font-bold">Manager Jobs</div>
                    <Line height={100} data={dataChartLineOfEmployerFromAdmin} />
                </div>
            </div>
        </div>
    );
};

export default ChartOfEmployer;