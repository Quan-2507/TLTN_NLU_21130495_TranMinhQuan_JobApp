import React from 'react';
import {
    FileProtectOutlined, RiseOutlined, UserOutlined, BankOutlined, CreditCardFilled, BookOutlined
} from '@ant-design/icons';



const SummaryDetail = (props) => {
    const chartAdmin = props.chartAdmin
    const overallPayment = chartAdmin?.data?.overall_payment
    return <div className="mb-10">
        <div>
            <h1 className="text-base text-gray-700 mb-2">Wellcome To Admin !!!</h1>
            <div className="grid gap-4 grid-cols-5">
                <div className="flex items-center  my-6 bg-gray-100 p-6 rounded-md shadow-md">
                    <div className=" bg-purple-400 mr-4  py-4 px-8 rounded-2xl text-2xl text-white"><FileProtectOutlined /></div>
                    <div>
                        <h1 className="text-base text-gray-400 font-bold">Job Created</h1>
                        <h4 className="text-gray-700 text-2xl font-bold">{chartAdmin?.data?.jobs_has_been_created}</h4>
                    </div>
                </div>
                <div className="flex items-center  my-6 bg-gray-100 p-6 rounded-md shadow-md">
                    <div className="bg-red-400 mr-4  py-4 px-8 rounded-2xl text-2xl text-white"><UserOutlined /></div>
                    <div>
                        <h1 className="text-base text-gray-400 font-bold">Account Created</h1>
                        <h4 className="text-gray-700 text-2xl font-bold">{chartAdmin?.data?.account_has_been_created}</h4>
                    </div>
                </div>
                <div className="flex items-center  my-6 bg-gray-100 p-6 rounded-md shadow-md">
                    <div className="bg-green-400 mr-4  py-4 px-8 rounded-2xl text-2xl text-white"><BankOutlined /></div>
                    <div>
                        <h1 className="text-base text-gray-400 font-bold">Company Created</h1>
                        <h4 className="text-gray-700 text-2xl font-bold">{chartAdmin?.data?.companys_has_been_created}</h4>
                    </div>
                </div>
                <div className="flex items-center  my-6 bg-gray-100 p-6 rounded-md shadow-md">
                    <div className="bg-orange-400 mr-4  py-4 px-8 rounded-2xl text-2xl text-white"><CreditCardFilled /></div>
                    <div>
                        <h1 className="text-base text-gray-400 font-bold">Overall Payment</h1>
                        <h4 className="text-gray-700 text-2xl font-bold">{Math.round(overallPayment * 100) / 100}  $</h4>
                    </div>
                </div>
                <div className="flex items-center  my-6 bg-gray-100 p-6 rounded-md shadow-md">
                    <div className="bg-blue-400 mr-4 py-4 px-8 rounded-2xl text-2xl text-white "><RiseOutlined /></div>
                    <div>
                        <h1 className="text-base text-gray-400 font-bold">Top Grossing Of Month</h1>
                        <h4 className="text-gray-700 text-2xl font-bold">{chartAdmin?.data?.top_grossing_month}</h4>
                    </div>
                </div>
            </div>
        </div>

    </div>
}

export default SummaryDetail