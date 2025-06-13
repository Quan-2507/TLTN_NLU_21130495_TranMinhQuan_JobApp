import React, { useEffect } from 'react';
import {
    FileProtectOutlined, CreditCardFilled, BookOutlined
} from '@ant-design/icons';



const SummaryDetailOfEmployer = (props) => {
    const dataChartOfEmployerFromAdminById = props.chartEmployerFromAdminById?.data
    const dataChartOfEmployer = props.dataChartOfEmployer?.data

    return <div className="">
        {dataChartOfEmployer === undefined ? <div className='grid gap-4 grid-cols-3'>
            <div className="flex items-center  my-6 bg-gray-100 p-6 rounded-md shadow-md h-36">
                <div className=" bg-purple-400 mr-4  py-4 px-8 rounded-2xl text-2xl text-white"><FileProtectOutlined /></div>
                <div>
                    <h1 className="text-base text-gray-400 font-bold">Job Created</h1>
                    <h4 className="text-gray-700 text-2xl font-bold">{dataChartOfEmployerFromAdminById?.jobs_has_been_created}</h4>
                </div>
            </div>
            <div className="flex items-center  my-6 bg-gray-100 p-6 rounded-md shadow-md h-36">
                <div className="bg-red-400 mr-4  py-4 px-8 rounded-2xl text-2xl text-white"><CreditCardFilled /></div>
                <div>
                    <h1 className="text-base text-gray-400 font-bold">Overall Payment</h1>
                    <h4 className="text-gray-700 text-2xl font-bold">{Math.round(dataChartOfEmployerFromAdminById?.overall_payment * 100) / 100} $</h4>
                </div>
            </div>
            <div className="flex items-center  my-6 bg-gray-100 p-6 rounded-md shadow-md h-36">
                <div className="bg-green-400 mr-4  py-4 px-8 rounded-2xl text-2xl text-white"><BookOutlined /></div>
                <div>
                    <h1 className="text-base text-gray-400 font-bold">Application By Month</h1>
                    <h4 className="text-gray-700 text-2xl font-bold">{dataChartOfEmployerFromAdminById?.total_applicated_by_month}</h4>

                </div>
            </div>
        </div> : <div className='grid gap-4 grid-cols-3'>
            <div className="flex items-center  my-6 bg-gray-100 p-6 rounded-md shadow-md h-36">
                <div className=" bg-purple-400 mr-4  py-4 px-8 rounded-2xl text-2xl text-white"><FileProtectOutlined /></div>
                <div>
                    <h1 className="text-base text-gray-400 font-bold">Job Created</h1>
                    <h4 className="text-gray-700 text-2xl font-bold">{dataChartOfEmployer?.jobs_has_been_created}</h4>
                </div>
            </div>
            <div className="flex items-center  my-6 bg-gray-100 p-6 rounded-md shadow-md h-36">
                <div className="bg-red-400 mr-4  py-4 px-8 rounded-2xl text-2xl text-white"><CreditCardFilled /></div>
                <div>
                    <h1 className="text-base text-gray-400 font-bold">Overall Payment</h1>
                    <h4 className="text-gray-700 text-2xl font-bold">{Math.round(dataChartOfEmployer?.overall_payment * 100) / 100} $</h4>
                </div>
            </div>
            <div className="flex items-center  my-6 bg-gray-100 p-6 rounded-md shadow-md h-36">
                <div className="bg-green-400 mr-4  py-4 px-8 rounded-2xl text-2xl text-white"><BookOutlined /></div>
                <div>
                    <h1 className="text-base text-gray-400 font-bold">Application By Month</h1>
                    <h4 className="text-gray-700 text-2xl font-bold">{dataChartOfEmployer?.total_applicated_by_month}</h4>

                </div>
            </div>
        </div>}
    </div>
}

export default SummaryDetailOfEmployer