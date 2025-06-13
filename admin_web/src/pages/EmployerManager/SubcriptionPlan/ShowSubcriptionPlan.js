import React, { useEffect, useState } from 'react';
import {
    CheckOutlined, DollarOutlined, FileSearchOutlined, LikeOutlined, FileProtectOutlined
} from '@ant-design/icons';
import { getSubscriptionPlanListAction } from '../../../redux/actions/SubscriptionPlanAction';
import { useDispatch, useSelector } from 'react-redux';

const backgroundColor1 = 'bg-green-400 col-2 py-2 rounded-tl-[80px] rounded-br-[80px] '
const backgroundColor2 = 'col-2 py-2 bg-blue-400 rounded-tl-[80px] rounded-br-[80px]'
const backgroundColor3 = 'col-2 py-2 bg-orange-400 rounded-tl-[80px] rounded-br-[80px]'
const backgroundColor4 = 'col-2 py-2 bg-yellow-400 rounded-tl-[80px] rounded-br-[80px]'

const backgroundIcon1 = 'bg-green-400  text-3xl shadow-2xl text-center rounded-full px-3 py-3 flex items-center justify-center'
const backgroundIcon2 = 'bg-blue-400  text-3xl shadow-2xl text-center rounded-full px-3 py-3 flex items-center justify-center'
const backgroundIcon3 = 'bg-orange-400  text-3xl shadow-2xl text-center rounded-full px-3 py-3 flex items-center justify-center'
const backgroundIcon4 = 'bg-yellow-400  text-3xl shadow-2xl text-center rounded-full px-3 py-3 flex items-center justify-center'

const colorFooter1 = 'text-green-400 font-xl font-bold m-0'
const colorFooter2 = 'text-blue-400 font-xl font-bold m-0'
const colorFooter3 = 'text-orange-400 font-xl font-bold m-0'
const colorFooter4 = 'text-yellow-400 font-xl font-bold m-0'


const icon1 = <DollarOutlined />
const icon2 = <FileSearchOutlined />
const icon3 = <LikeOutlined />
const icon4 = <FileProtectOutlined />


export default function ShowSubcriptionPlan() {
    const dispatch = useDispatch();
    useEffect(() => {
        dispatch(getSubscriptionPlanListAction())
    }, [])
    let { arrSubscriptionPlan } = useSelector(state => state.SubscriptionPlanReducer);
    return (
        <div className='row flex justify-around'>
            {arrSubscriptionPlan?.data?.length > 0 && (arrSubscriptionPlan?.data)?.map((sb) => (

                <
                    div className={sb?.name === 'COMBO 30' ? `${backgroundColor1}` :
                        sb?.name === 'COMBO 50' ? `${backgroundColor2}` :
                            sb?.name === 'COMBO 100' ? `${backgroundColor3}` :
                                `${backgroundColor4}`}
                >

                    <div className='bg-white py-3 rounded-tl-[80px] rounded-br-[80px]'>
                        <div className='flex flex-col justify-center items-center' >
                            <h1 className='font-bold text-xl text-green-300 text-center mb-3'>${sb?.price}</h1>
                            <div className='flex items-center justify-center border-b pb-2 border-gray-400 border-dashed mx-2 h-[75px] '>
                                <h1 className='text-gray-500 text-base mr-3 font-bold'><CheckOutlined /></h1>
                                <h4 className='text-base font-light mb-0 text-ellipsis overflow-hidden line-clamp-2'>{sb.description}</h4>
                            </div>
                            <div className='flex items-center justify-center border-b mb-2 border-gray-400 border-dashed mx-2'>
                                <h1 className='text-gray-500 text-base mr-3 font-bold'><CheckOutlined /></h1>
                                <h4 className='text-base font-light mb-0'>
                                    {sb?.expiry} jobs </h4>
                            </div>
                            <div className='text-white mt-2 w-[40%] flex items-center justify-center'>
                                <div className={sb?.name === 'COMBO 30' ? `${backgroundIcon1}` : sb?.name === 'COMBO 50' ? `${backgroundIcon2}` : sb?.name === 'COMBO 100' ? `${backgroundIcon3}` : `${backgroundIcon4}`}>
                                    {sb?.name === 'COMBO 30' ? icon1 : sb?.name === 'COMBO 50' ? icon2 : sb?.name === 'COMBO 100' ? icon3 : icon4}
                                </div>
                            </div>
                        </div>

                    </div>
                    <div className='bg-white mt-3 w-[80%] p-2 flex justify-center items-center rounded-xl'>
                        <h1 className={sb?.name === 'COMBO 30' ? `${colorFooter1}` :
                            sb?.name === 'COMBO 50' ? `${colorFooter2}` :
                                sb?.name === 'COMBO 100' ? `${colorFooter3}` : `
                        ${colorFooter4}`}>{sb?.name}</h1>
                    </div>
                </div>
            ))}
        </div>
    );
};
