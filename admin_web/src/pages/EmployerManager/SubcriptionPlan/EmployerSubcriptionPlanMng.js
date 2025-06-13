import React, { useEffect } from 'react'
import { SearchOutlined, TagOutlined } from '@ant-design/icons';
import { Button, Input, Space, Table } from 'antd';
import { useRef, useState } from 'react';
import Highlighter from 'react-highlight-words';
import { useDispatch, useSelector } from 'react-redux';

import { getSubscriptionPlanByAccountAction } from '../../../redux/actions/SubscriptionPlanAction';
import { TOKEN } from '../../../util/settings/config';
import dayjs from 'dayjs';



export default function EmployerSubcriptionPlanMng() {
    let { subscriptionPlanByAccount } = useSelector(state => state.SubscriptionPlanReducer);

    const dispatch = useDispatch();
    useEffect(() => {
        dispatch(getSubscriptionPlanByAccountAction(accessToken))
    }, [dispatch])


    let accessToken = {}
    if (localStorage.getItem(TOKEN)) {
        accessToken = localStorage.getItem(TOKEN)
    }

    const [searchText, setSearchText] = useState('');
    const [searchedColumn, setSearchedColumn] = useState('');
    const searchInput = useRef(null);
    const handleSearch = (selectedKeys, confirm, dataIndex) => {
        confirm();
        setSearchText(selectedKeys[0]);
        setSearchedColumn(dataIndex);
    };

    const resetSearch = (selectedKeys, confirm, dataIndex) => {
        confirm();
        setSearchText(selectedKeys[0] = '');
        setSearchedColumn(dataIndex);
    };

    const parseSubsObjecTotArray = [subscriptionPlanByAccount?.subcriptionPlanDTO]

    const data2 = parseSubsObjecTotArray;
    const data1 = subscriptionPlanByAccount?.subcriptionPlanDTOs;

    const getColumnSearchProps = (dataIndex) => ({
        filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters, close }) => (
            <div style={{ padding: 8, }} onKeyDown={(e) => e.stopPropagation()} >
                <Input
                    ref={searchInput}
                    placeholder={`Search ${dataIndex}`}
                    value={selectedKeys[0]}
                    onChange={(e) => setSelectedKeys(e.target.value ? [e.target.value] : [])}
                    onPressEnter={() => handleSearch(selectedKeys, confirm, dataIndex)}
                    style={{
                        marginBottom: 8,
                        display: 'block',
                    }}
                />
                <Space>
                    <Button
                        type="primary"
                        onClick={() => handleSearch(selectedKeys, confirm, dataIndex)}
                        icon={<SearchOutlined />}
                        size="small"
                        className='bg-primary'
                        style={{
                            width: 90,
                        }}
                    >
                        Search
                    </Button>
                    <Button
                        onClick={() => clearFilters && resetSearch(selectedKeys, confirm, dataIndex)}
                        size="small"
                        style={{
                            width: 90,
                        }}
                    >
                        Reset
                    </Button>

                </Space>
            </div>
        ),
        filterIcon: (filtered) => (
            <SearchOutlined
                style={{
                    color: filtered ? '#1677ff' : undefined,
                }}
            />
        ),
        onFilter: (value, record) =>
            record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()),
        onFilterDropdownOpenChange: (visible) => {
            if (visible) {
                setTimeout(() => searchInput.current?.select(), 100);
            }
        },
        render: (text, index) =>
            searchedColumn === dataIndex ? (
                <Highlighter
                    key={index}
                    highlightStyle={{
                        backgroundColor: '#ffc069',
                        padding: 0,
                    }}
                    searchWords={[searchText]}
                    autoEscape
                    textToHighlight={text ? text.toString() : ''}
                />
            ) : (
                text
            ),
    });
    const columns = [
        {
            title: 'Id',
            dataIndex: 'id',
            key: 'id',
            width: '15%',
            sorter: (a, b) => a.id - b.id,
            sortDirections: ['descend', 'ascend'],
        },
        {
            title: 'Expiry',
            dataIndex: 'expiry',
            key: 'expiry',
            width: '10%',
            ...getColumnSearchProps('expiry'),
            sorter: (a, b) => a.expiry - b.expiry,
            sortDirections: ['descend', 'ascend'],
        },
        {
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
            width: '15%',
            ...getColumnSearchProps('name'),
            sorter: (a, b) => a.name - b.name,
            render: (a) => {
                if (data2[0]?.id) {
                    return <div className='bg-yellow-300 rounded-md shadow-md  p-2 shadow-yellow-300   text-center'> {a} </div>
                } else {
                    return <div className='bg-gray-200 rounded-md shadow-md  p-2 shadow-gray-300   text-center'> {a} </div>
                }

            },
            sortDirections: ['descend', 'ascend'],
        },
        {
            title: 'Price',
            dataIndex: 'price',
            key: 'price',
            width: '20%',
            ...getColumnSearchProps('price'),
            sorter: (a, b) => a.price - b.price,
            render: (data) => {
                return <>{data} $</>
            },
            sortDirections: ['descend', 'ascend'],
        },
        {
            title: 'Start date',
            dataIndex: 'start_date',
            key: 'start_date',
            width: '20%',
            ...getColumnSearchProps('start_date'),
            sorter: (a, b) => a.start_date - b.start_date,
            render: (data) => {
                return <>{dayjs(data).format("DD-MM-YYYY")}</>
            },
            sortDirections: ['descend', 'ascend'],
        },
        {
            title: 'End date',
            dataIndex: 'end_date',
            key: 'end_date',
            width: '20%',
            ...getColumnSearchProps('end_date'),
            sorter: (a, b) => a.end_date - b.end_date,
            render: (data) => {
                return <>{dayjs(data).format("DD-MM-YYYY")}</>
            },
            sortDirections: ['descend', 'ascend'],
        },

    ]
    return <div>
        <div className='d-flex mb-3 h-15'>
            <h3 className='text-xl'>Subcription Plan Management</h3>
        </div>
        {data1 === undefined || data1?.length === 0 ? <div>
            <div className='flex items-center justify-center'>
                <h2 className='text-lg font-light text-gray-500 flex items-center justify-center'>
                    <div className='flex items-center justify-center mr-2'>
                        < TagOutlined />
                    </div>
                    No Register Subcription Plan .
                    <a href='/employer/buyScPl' className='font-normal text-blue-400 hover:cursor-pointer hover:text-red-500'>Please Register Subcription Plan !
                    </a>
                </h2>
            </div>
        </div> : <div>
            <div className='mb-5'>

                <h3 className='text-lg italic text-gray-500'>Registered</h3>
                <Table columns={columns} dataSource={data2} rowKey={'id'} pagination={false} />

            </div>
            <div className=''>
                <h3 className='text-lg italic text-gray-500 '>Out of Date</h3>
                <Table columns={columns} dataSource={data1} rowKey={'id'} />
            </div>
        </div>}


    </div>
}