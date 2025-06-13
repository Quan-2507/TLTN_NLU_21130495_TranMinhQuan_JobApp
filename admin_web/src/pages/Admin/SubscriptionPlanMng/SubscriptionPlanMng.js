import React, { useEffect } from 'react'
import { SearchOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { Button, Input, Space, Table } from 'antd';
import { useRef, useState } from 'react';
import Highlighter from 'react-highlight-words';
import { useDispatch, useSelector } from 'react-redux';
import { deleteSubscriptionPlanAction, getSubscriptionPlanListAction } from '../../../redux/actions/SubscriptionPlanAction';


export default function SubcriptionPlanMng() {
    let { arrSubscriptionPlan } = useSelector(state => state.SubscriptionPlanReducer);
    const dispatch = useDispatch();
    useEffect(() => {
        dispatch(getSubscriptionPlanListAction())
    }, [dispatch])


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


    const data = arrSubscriptionPlan.data;

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
            width: '20%',
            ...getColumnSearchProps('expiry'),
            sorter: (a, b) => a.expiry - b.expiry,
            sortDirections: ['descend', 'ascend'],
        },
        {
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
            width: '30%',
            ...getColumnSearchProps('name'),
            sorter: (a, b) => a.name - b.name,
            sortDirections: ['descend', 'ascend'],
        },
        {
            title: 'Description',
            dataIndex: 'description',
            key: 'description',
            width: '30%',
            ...getColumnSearchProps('description'),
            sorter: (a, b) => a.description - b.description,
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
            title: 'Manage',
            width: '15%',
            render: (text, subpl) => {
                return <>
                    <Button key={1} href={`/admin/subplanmng/edit/${subpl.id}`} type="link" icon={<EditOutlined />} onClick={() => {
                    }}></Button>
                    <Button key={2} type="link" danger icon={<DeleteOutlined />} onClick={() => {
                        if (window.confirm('Do you want to delete ' + subpl.name + '?')) {
                            dispatch(deleteSubscriptionPlanAction(subpl.id))
                        }
                    }}></Button>
                </>

            }
        },
    ]
    return <div>
        <div className='d-flex mb-3'>
            <h3 className='text-lg'>Subcription Plan Management</h3>
            <Button href='/admin/subplanmng/addsubplan' type="primary" className='ml-3 small bg-primary'>+ Add New SubcriptionPlan</Button>
        </div>
        <Table columns={columns} dataSource={data} rowKey={'id'} />
    </div>
}