import React, { useEffect, useState } from 'react';
import { Button } from 'antd';
import { useDispatch, useSelector } from 'react-redux';
import { TOKEN } from '../../../util/settings/config';
import dayjs from 'dayjs';

import { getCurrentUserAction } from '../../../redux/actions/UserAction';
import { getCompanyAndJobByTokenAction } from '../../../redux/actions/AccountAction';
import { buyScriptionPlan, getSubscriptionPlanListAction } from '../../../redux/actions/SubscriptionPlanAction';
import { history } from '../../../App';





const BuySubcriptionPlan = () => {
    const dispatch = useDispatch();
    let { userLogin } = useSelector(state => state.UserReducer);
    let { arrSubscriptionPlan } = useSelector(state => state.SubscriptionPlanReducer);



    let accessToken = {}
    if (localStorage.getItem(TOKEN)) {
        accessToken = localStorage.getItem(TOKEN)
    }

    useEffect(() => {
        if (TOKEN != null) {
            dispatch(getCurrentUserAction(accessToken))
            dispatch(getCompanyAndJobByTokenAction(accessToken))
            dispatch(getSubscriptionPlanListAction())
        }
    }, [dispatch]);

    if (userLogin && (userLogin?.role !== 'EMPLOYER')) {
        history.replace('/')
    }

    const [selectedPackage, setSelectedPackage] = useState();
    const [getPrice, setPrice] = useState(0);
    const handlePackageChange = (event) => {
        setSelectedPackage(event.target.value);
        arrSubscriptionPlan?.data.map((data) => {
            if (data.id == event.target.value)
                setPrice(data.price)
        })
    };
    const handleSubmit = () => {
        if (getPrice != 0) {
            dispatch(buyScriptionPlan(selectedPackage, getPrice))
        }
    }


    return (
        <div >
            <div className=''>
                <div className="">
                    <h1 className='text-center text-lg'>Please choose one subcription plan</h1>
                    <div className="package-options ml-10 row justify-center">
                        {(arrSubscriptionPlan?.data)?.map((pk) => (
                            <div className='col-2 p-4 rounded-md'>
                                <div className="flex row">
                                    <div className="rounded-sm shadow bg-white p-4 border border-gray-200  hover: cursor-pointer">
                                        <h1 className='text-center text-gray-400 text-lg'>
                                            {pk.name}
                                        </h1>
                                        <div className='flex items-center justify-center mt-3'>
                                            <h1 className='text-base'>{pk.price}</h1>
                                            <h4 className='text-sm ml-2 text-gray-400'>/Month</h4>
                                        </div>

                                        <div className='flex items-center  justify-center mt-3 p-2 bg-green-400  rounded-sm hover:bg-green-300'>
                                            <input
                                                type="radio"
                                                name="package"
                                                value={pk.id}
                                                checked={selectedPackage == pk.id}
                                                onChange={handlePackageChange}
                                                className='text-black  hover:cursor-pointer'
                                            ></input>
                                            <h1 className='ml-2 my-0'>Choose</h1>
                                        </div>
                                        <div className='flex items-center justify-center mt-3'>
                                            <h1 className='text-base'>{pk.expiry}</h1>
                                            <h4 className='text-sm ml-2 text-gray-400'>Jobs</h4>
                                        </div>
                                        <div className="text-center ">
                                            <h1 className='text-base m-0'>Description : </h1>
                                            <h4 className='text-sm ml-2 text-gray-400 text-ellipsis overflow-hidden line-clamp-2'>
                                                {pk.description}
                                            </h4>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        ))}
                    </div>
                    <div className="package-description">
                        <div className="flex flex-col items-center justify-center">
                            {(arrSubscriptionPlan?.data)?.map((packageName) => (
                                <>
                                    {packageName.id == selectedPackage ? <> <hr /> <h2 className='text-center text-lg'>Your choose</h2>
                                        <div className='col-5 mb-3 flex flex-col justify-center'>
                                            <div className="flex items-center justify-center">
                                                <div className=" rounded-md bg-white hover:cursor-pointer border shadow-md">
                                                    <h1 className='text-center bg-blue-400 w-[100%] py-2 rounded-t-md'>Code: <span className='font-bold'>{packageName.name}</span></h1>
                                                    <div className='px-3 mb-4'>
                                                        <div className="text-center">
                                                            <h1 className='text-black text-base m-0'>Description : </h1>
                                                            <h2 className='text-gray-400 text-sm'>
                                                                {packageName.description}
                                                            </h2>
                                                        </div>
                                                        <div className="mt-2">
                                                            <div className=" flex items-center justify-center">
                                                                <h1 className='text-black mr-2 text-base'>Expiry :</h1>
                                                                <h2 className='text-gray-400 text-sm'>{packageName.expiry} days</h2>
                                                            </div>

                                                        </div>
                                                        <div className=' flex items-center justify-center text-center'>
                                                            <h1 className='text-black mr-2 text-base m-0'>Price :</h1>
                                                            <h2 className='text-gray-400 text-sm m-0'>{packageName.price} $</h2>
                                                        </div>
                                                        <div className="text-center mt-2">
                                                            <h1 className='text-black mr-2 text-base m-0'>Available job openings : </h1>
                                                            <h2 className='text-gray-400 text-sm m-0 mt-1'>{packageName.expiry} Jobs</h2>

                                                        </div>
                                                        <div className="flex mt-2 items-center justify-center">
                                                            <h1 className='text-black mr-2 text-sm m-0'>Start date : </h1>
                                                            <h2 className='text-gray-400 text-sm m-0'>{(dayjs(new Date())).format('DD-MM-YYYY')} days</h2>
                                                        </div>
                                                        <div className="flex mt-2 items-center justify-center">
                                                            <h1 className='text-black mr-2 text-sm m-0'>End Date : </h1>
                                                            <h2 className='text-gray-400 text-sm m-0'>{(dayjs(new Date()).add(packageName.expiry, 'day')).format('DD-MM-YYYY')} $</h2>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>

                                        <div className='' > <Button type="primary" danger onClick={handleSubmit} >BUY</Button></div></> : ""}
                                </>
                            ))}
                        </div>
                    </div>
                </div>
            </div>


        </div>
    );
};

export default BuySubcriptionPlan;