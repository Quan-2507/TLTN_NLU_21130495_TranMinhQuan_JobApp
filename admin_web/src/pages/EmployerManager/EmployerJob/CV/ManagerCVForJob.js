import React, { useEffect, useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Avatar, Button, Input, Space, Table, } from "antd";
import { SearchOutlined, EyeOutlined } from '@ant-design/icons';
import Highlighter from "react-highlight-words";
import dayjs from 'dayjs';

import { getCVSavedAction } from "../../../../redux/actions/JobAction";



const ManagerCVForJob = () => {
    const dispatch = useDispatch();
    const { arrCvSaved } = useSelector(state => state.JobReducer)

    useEffect(() => {
        dispatch(getCVSavedAction())
    }, [dispatch])
    const data = arrCvSaved?.data;



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
            width: '5%',
            sorter: (a, b) => a.id - b.id,
            sortDirections: ['descend', 'ascend'],
        },
        {
            title: 'Name of Candidate',
            dataIndex: 'account',
            key: 'account',
            width: '15%',
            ...getColumnSearchProps('account'),
            sorter: (a, b) => a.account - b.account,
            sortDirections: ['descend', 'ascend'],
            render: (text, account) => {
                return (<>
                    <span>{account.account?.name}</span>
                </>)
            },

        },
        {
            title: 'Email of Candidate',
            dataIndex: 'account',
            key: 'account',
            width: '15%',
            ...getColumnSearchProps('account'),
            sorter: (a, b) => a.account - b.account,
            sortDirections: ['descend', 'ascend'],
            render: (text, account) => {
                return (<>
                    <span>{account.account?.email}</span>
                </>)
            },

        },
        {
            title: "Avatar",
            dataIndex: "avatar",
            key: "avatar",
            width: '10%',
            render: (text, data, index) => {
                return data?.account?.image != null ? (
                    <img key={index} style={{ width: 40, height: 40, objectFit: "cover", borderRadius: "50%", }} src={`${data?.account?.image}`} alt={data?.account?.image} />
                ) : (
                    <Avatar size={40} style={{ fontSize: "20px", display: "flex", justifyContent: "center", alignItems: "center" }} icon={data?.account?.email?.substr(0, 1)} />
                );
            },
        },
        {
            title: 'Date Applicated',
            dataIndex: 'account',
            key: 'account',
            width: '15%',
            ...getColumnSearchProps('account'),
            sorter: (a, b) => a.account - b.account,
            sortDirections: ['descend', 'ascend'],
            render: (text, account) => {
                return (<>
                    <span>{dayjs(account?.date_applied).format("DD-MM-YYYY")}</span>
                </>)
            },

        },
        {
            title: 'Job Applicated',
            dataIndex: 'account',
            key: 'account',
            width: '20%',
            ...getColumnSearchProps('account'),
            sorter: (a, b) => a.account - b.account,
            sortDirections: ['descend', 'ascend'],
            render: (text, account) => {
                return (<>
                    <span>{account?.job?.title}</span>

                </>)
            },

        },
        {
            title: 'Curriculum Vitae of Candidate',
            dataIndex: 'account',
            key: 'account',
            width: '20%',
            ...getColumnSearchProps('account'),
            sorter: (a, b) => a.account - b.account,
            sortDirections: ['descend', 'ascend'],
            render: (text, cv) => {
                return (
                    <div className="flex items-center" key={cv.id}>
                        <a href={cv.cv?.file_name} target="_blank" rel="noopener noreferrer" className="text-xl hover:no-underline text-blue flex items-end justify-center hover:cursor-pointer no-underline  pr-4">
                            <EyeOutlined /> <p className="text-sm ml-2 my-0 py-0 "> View CV</p>
                        </a>
                    </div>
                )
            },

        },
    ]
    return <div>
        <div className='d-flex mb-3'>
            <h3 className='text-lg'>Curriculum Vitae Saved Management</h3>
        </div>
        <Table columns={columns} dataSource={data} rowKey={'id'} />
    </div>

};

export default ManagerCVForJob;