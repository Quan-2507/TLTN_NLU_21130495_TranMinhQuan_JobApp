import React, { useEffect, useState } from 'react';
import { Button, Modal, Carousel } from 'antd';
import { useDispatch, useSelector } from 'react-redux';
import {
    CheckOutlined, DollarOutlined
} from '@ant-design/icons';
import dayjs from 'dayjs';

import { TOKEN } from '../../util/settings/config';
import { getCurrentUserAction } from '../../redux/actions/UserAction';
import { getCompanyAndJobByTokenAction, getCompanyForEmployerFromAdminById } from '../../redux/actions/AccountAction';
import { getSubscriptionPlanByAccountAction, getSubscriptionPlanFromAdminByIdAccountAction, returnBuyScriptionPlan } from '../../redux/actions/SubscriptionPlanAction';
import { getDataChartOfEmployer, getDataChartOfEmployerFromAdminById } from '../../redux/actions/JobAction';
import EmployerJobMng from './EmployerJob/EmployerJobMng';
import ChartOfEmployer from './Chart/ChartOfEmployer';
import SummaryDetailOfEmployer from './Summary/summaryDetailOfEmployer';




const EmployerProfile = (props) => {
    const dispatch = useDispatch();
    let id = props.match.params;
    let { userLogin } = useSelector(state => state.UserReducer);
    let { employerCompanyJob } = useSelector(state => state.AccountReducer);
    let { subscriptionPlanByAccount } = useSelector(state => state.SubscriptionPlanReducer);
    let { dataCompanyForEmployerFromAdmin } = useSelector(state => state.AccountReducer);
    let { chartEmployerFromAdminById } = useSelector(state => state.JobReducer);
    let { dataChartOfEmployerById } = useSelector(state => state.JobReducer);
    let { arrDataSubcriptionPlanFromAdmin } = useSelector(state => state.SubscriptionPlanReducer);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isModalOfCompanyOpen, setIsModalOfCompany] = useState(false);
    let accessToken = {}
    if (localStorage.getItem(TOKEN)) {
        accessToken = localStorage.getItem(TOKEN)
    }

    useEffect(() => {
        if (TOKEN != null) {
            dispatch(getCurrentUserAction(accessToken))
            dispatch(getCompanyAndJobByTokenAction(accessToken))
            dispatch(getSubscriptionPlanByAccountAction(accessToken))
        }
    }, [dispatch]);

    useEffect(() => {
        if (userLogin?.role === "EMPLOYER") {
            dispatch(getDataChartOfEmployer(userLogin?.id))
        } else if (userLogin?.role === "ADMIN") {
            dispatch(getCompanyForEmployerFromAdminById(id?.id))
            dispatch(getDataChartOfEmployerFromAdminById(id?.id))
            dispatch(getSubscriptionPlanFromAdminByIdAccountAction(id?.id))
        }
    }, [userLogin])

    let URL = window.location.href;
    //subcription plan
    useEffect(() => {
        const paymentIdRegex = /paymentId=([^&]+)/;
        const payerIdRegex = /PayerID=([^&]+)/;
        const paymentIdMatch = URL.match(paymentIdRegex);
        const payerIdMatch = URL.match(payerIdRegex);
        const paymentId = paymentIdMatch ? paymentIdMatch[1] : null;
        const payerId = payerIdMatch ? payerIdMatch[1] : null;
        if (payerId != null && paymentId != null) {
            dispatch(returnBuyScriptionPlan(paymentId, payerId))
        }
    }, [URL])

    const showModal = () => {
        setIsModalOpen(true);
    };


    const showModalOfCompany = () => {
        setIsModalOfCompany(true);
    }
    const handleOk = () => {
        setIsModalOpen(false);
    };

    const handleCancel = () => {
        setIsModalOpen(false);
    };

    const handleOfCompanyOk = () => {
        setIsModalOfCompany(false);
    };

    const handleOfCompanyCancel = () => {
        setIsModalOfCompany(false);
    };



    return (
        <div className='p-4'>
            <div className="grid grid-cols-3 gap-2">
                <div className="col-span-2">
                    <SummaryDetailOfEmployer chartEmployerFromAdminById={chartEmployerFromAdminById} dataChartOfEmployer={dataChartOfEmployerById}></SummaryDetailOfEmployer>
                </div>

                <div className=''>
                    <div className='bg-gray-100 my-6 p-6 rounded-md shadow-md h-36'>
                        <div className='flex justify-between mb-2'>
                            {
                                userLogin?.role === "ADMIN" ? <div className='mr-2 font-extrabold text-xl'>Hello {dataCompanyForEmployerFromAdmin?.email}</div>
                                    : <div className='mr-2 font-extrabold text-xl'>Hello {employerCompanyJob?.email}</div>
                            }
                            <div >
                                <div className='font-bold'>
                                    <Button
                                        className='text-xl font-bold'
                                        onClick={() => {
                                            showModalOfCompany()
                                        }}
                                        title='Show Company Detail'
                                    > <i className="fa-solid fa-arrow-up-right-from-square"></i></Button>
                                </div>
                                {/* Modal of Company Detail */}
                                <Modal
                                    width={'90%'}
                                    title="Your Company Detail"
                                    open={isModalOfCompanyOpen}
                                    onOk={handleOfCompanyOk}
                                    onCancel={handleOfCompanyCancel}
                                >
                                    <div className='mt-1 '>
                                        <div className='w-[100%] h-[100%] px-20 bg-white mb-10'>
                                            <Carousel style={{ padding: '20px' }} autoplay>

                                                {
                                                    employerCompanyJob?.companyForEmployer?.list_image?.length > 4 ||
                                                        dataCompanyForEmployerFromAdmin?.companyForEmployer?.list_image?.length > 4 ?
                                                        JSON.parse(employerCompanyJob?.companyForEmployer?.list_image ||
                                                            dataCompanyForEmployerFromAdmin?.companyForEmployer?.list_image)?.map((image, i) => {
                                                                return (
                                                                    <div className='' key={i}>
                                                                        <img
                                                                            key={i}
                                                                            className=' w-[100%] object-cover  rounded-lg border-solid border-gray-300 flex items-center h-[700px]'
                                                                            src={image}
                                                                            alt="..." />

                                                                    </div>
                                                                )
                                                            }
                                                            ) :
                                                        <img className=' w-[100%] object-cover  rounded-lg border-solid border-gray-300 flex items-center h-[700px]' src="/img/placeholder-image.jpg" alt="..." />
                                                }
                                            </Carousel>
                                        </div>
                                        <div className='flex items-center px-20 mt-10 mb-4'>

                                            <div className='flex items-center'>
                                                <h2 className='text-lg font-bold m-0 mr-2 p-0 '>Company Management : </h2>
                                                <h4 className='text-lg m-0 p-0 mr-2'>{employerCompanyJob?.companyForEmployer?.name || dataCompanyForEmployerFromAdmin?.companyForEmployer?.name}</h4>
                                            </div>
                                            {userLogin?.role === "EMPLOYER" && <Button
                                                className='btn-primary bg-primary ml-4'
                                                key={1} href={`/admin/companymng/edit/${employerCompanyJob.companyForEmployer?.id}`} type="link"

                                            >Update Company</Button>}
                                        </div>

                                        <div className='  py-3  px-20 w-[100%] '>
                                            <div className='px-6 py-6 rounded-xl  bg-gray-100 '>
                                                <div className='flex'>
                                                    <h2 className='font-bold text-lg flex gap-2 justify-center'>
                                                        <h2 className='flex-2'>Introduction :</h2>
                                                        <p className='text-base font-normal ml-2 flex-1'
                                                            dangerouslySetInnerHTML={{ __html: employerCompanyJob?.companyForEmployer?.introduction || dataCompanyForEmployerFromAdmin?.companyForEmployer?.introduction }}
                                                        >
                                                        </p>
                                                    </h2>
                                                </div>
                                                <div>
                                                    <h2 className='font-bold text-lg flex gap-2 justify-center'>
                                                        <h2 className='flex-2'>Profession :</h2>
                                                        <p className='text-base font-normal ml-2 flex-1'>
                                                            {employerCompanyJob.companyForEmployer?.profession || dataCompanyForEmployerFromAdmin?.companyForEmployer?.profession}
                                                        </p>
                                                    </h2>
                                                </div>
                                                <div>
                                                    <h2 className='font-bold text-lg flex gap-2 justify-center'>
                                                        <h2 className='flex-2'> Link Website :</h2>
                                                        <a className='text-base font-normal ml-2 flex-1' href={employerCompanyJob.companyForEmployer?.link_website || dataCompanyForEmployerFromAdmin?.companyForEmployer?.link_website}>
                                                            {employerCompanyJob.companyForEmployer?.link_website || dataCompanyForEmployerFromAdmin?.companyForEmployer?.link_website}
                                                        </a>
                                                    </h2>
                                                </div>
                                                <div>
                                                    <h2 className='font-bold text-lg flex gap-2 justify-center'>
                                                        <h2 className='flex-2'> National :</h2>
                                                        <p className='text-base font-normal ml-2 flex-1'>
                                                            {employerCompanyJob.companyForEmployer?.nationnality || dataCompanyForEmployerFromAdmin?.companyForEmployer?.nationnality}
                                                        </p>
                                                    </h2>
                                                </div>
                                                <div>
                                                    <h2 className='font-bold text-lg flex gap-2 justify-center'>
                                                        <h2 className='flex-2'> Location :</h2>
                                                        <p className='text-base font-normal ml-2 flex-1'>
                                                            {employerCompanyJob.companyForEmployer?.location || dataCompanyForEmployerFromAdmin?.companyForEmployer?.location}
                                                        </p>
                                                    </h2>
                                                </div>
                                                <div>
                                                    <h2 className='font-bold text-lg flex gap-2 justify-center'>
                                                        <h2 className='flex-2'>Benefit :</h2>
                                                        <p className='text-base font-normal ml-2 flex-1'
                                                            dangerouslySetInnerHTML={{ __html: employerCompanyJob.companyForEmployer?.benefit || dataCompanyForEmployerFromAdmin?.companyForEmployer?.benefit }}
                                                        >
                                                        </p>
                                                    </h2>
                                                </div>
                                                <div>
                                                    <h2 className='font-bold text-lg flex gap-2 justify-center'>
                                                        <h2 className='flex-2'>Size :</h2>
                                                        <p className='text-base font-normal ml-2 flex-1'>
                                                            {employerCompanyJob.companyForEmployer?.size || dataCompanyForEmployerFromAdmin?.companyForEmployer?.size || 'None'}
                                                        </p>
                                                    </h2>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </Modal>
                            </div>
                        </div>

                        {/* Subcription Plan */}
                        {userLogin?.role === "EMPLOYER" ? <div>
                            {subscriptionPlanByAccount?.subcriptionPlanDTO
                                ?
                                <div className='w-[60%] cursor-pointer'>
                                    <div onClick={showModal} className='bg-yellow-300  rounded-md shadow-md  p-2 shadow-yellow-300 flex flex-col gap-2 text-center'>
                                        <p className='m-0 p-0'>{subscriptionPlanByAccount?.subcriptionPlanDTO?.name}</p>
                                        <p className='m-0 p-0'>From {dayjs(subscriptionPlanByAccount?.subcriptionPlanDTO?.start_date).format("DD-MM-YYYY")} To {dayjs(subscriptionPlanByAccount?.subcriptionPlanDTO?.end_date).format("DD-MM-YYYY")} </p>
                                    </div>
                                </div>
                                : <Button href={`/employer/buyScPl`} className='btn-primary bg-primary mt-1 px-5' type='primary' onClick={() => { }}>Buy subscription plan</Button>}

                            <Modal title="Current subscription plan" onOk={handleOk} onCancel={handleCancel} open={isModalOpen} footer={
                                <div className='text-center'>
                                    <Button className='btn-primary bg-primary mt-2 px-5' type='primary' onClick={handleOk}>OK</Button>
                                </div>}
                            >
                                <div className='flex flex-col items-center justify-center mt-6'>
                                    <div className="bg-blue-900 col-6 py-2 rounded-tl-[80px] rounded-br-[80px]">

                                        <div className='bg-white py-3 rounded-tl-[80px] rounded-br-[80px]'>
                                            <div className='flex flex-col justify-center items-center' >
                                                <h1 className='font-bold text-xl text-blue-900 text-center mb-3'>${subscriptionPlanByAccount?.subcriptionPlanDTO?.price}</h1>
                                                <div className='flex items-center justify-center border-b pb-2 border-gray-400 border-dashed mx-2'>
                                                    <h1 className='text-gray-500 text-base mr-3 font-bold'><CheckOutlined /></h1>
                                                    <h4 className='text-base font-light mb-0'>{subscriptionPlanByAccount?.subcriptionPlanDTO?.description}</h4>
                                                </div>
                                                <div className='flex items-center justify-center border-b mb-2 border-gray-400 border-dashed mx-2'>
                                                    <h1 className='text-gray-500 text-base mr-3 font-bold'><CheckOutlined /></h1>
                                                    <h4 className='text-base font-light mb-0'>
                                                        {subscriptionPlanByAccount?.subcriptionPlanDTO?.expiry} jobs </h4>
                                                </div>
                                                <div className='text-white mt-2 w-[40%] flex items-center justify-center'>
                                                    <div className="bg-blue-900  text-3xl shadow-2xl text-center rounded-full px-3 py-3 flex items-center justify-center">
                                                        <DollarOutlined />
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                        <div className='bg-white mt-3 w-[80%] p-2 flex justify-center items-center rounded-xl'>
                                            <h1 className="text-blue-900 font-xl font-bold m-0"> {subscriptionPlanByAccount?.subcriptionPlanDTO?.name}</h1>
                                        </div>
                                    </div>
                                </div>

                            </Modal>
                        </div> :
                            <div className='w-[60%]'>
                                <div className='bg-yellow-300  rounded-md shadow-md  p-2 shadow-yellow-300   text-center'>
                                    <p className='text-gray-600 p-0 m-0'>{arrDataSubcriptionPlanFromAdmin?.subcriptionPlanDTO?.name || "Don't Have Subcription Plan"}</p>
                                </div>
                            </div>
                        }

                    </div>
                </div>
            </div>

            <div className=''>
                <ChartOfEmployer idOfEmployer={id}></ChartOfEmployer>
            </div>
            <div className='mt-10 rounded-xl p-4 border-2  border-gray-300'>
                <EmployerJobMng idOfEmployer={id}></EmployerJobMng>
            </div>

        </div>
    );
};

export default EmployerProfile;