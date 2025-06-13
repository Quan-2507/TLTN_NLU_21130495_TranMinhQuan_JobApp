import React, { useEffect, useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Avatar, Button, Input, Space, Table, } from "antd";
import { SearchOutlined, EyeOutlined } from '@ant-design/icons';
import Highlighter from "react-highlight-words";
import dayjs from 'dayjs';

import { getFollowCompanyAction } from "../../redux/actions/CompanyAction";



const FollowCompanyByCandidate = () => {
    const dispatch = useDispatch();
    const { arrFollowCompany } = useSelector(state => state.CompanyReducer)

    useEffect(() => {
        dispatch(getFollowCompanyAction())
    }, [dispatch])
    const data = arrFollowCompany?.data;

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
            title: 'Name of Candidate Follow',
            dataIndex: 'account',
            key: 'account',
            width: '15%',
            ...getColumnSearchProps('account'),
            sorter: (a, b) => a.account - b.account,
            sortDirections: ['descend', 'ascend'],
            render: (text, data) => {
                return (<>
                    <span>{data?.name}</span>
                </>)
            },

        },
        {
            title: 'Email of Candidate',
            dataIndex: 'data',
            key: 'data',
            width: '15%',
            ...getColumnSearchProps('data'),
            sorter: (a, b) => a.account - b.account,
            sortDirections: ['descend', 'ascend'],
            render: (text, data) => {
                return (<>
                    <span>{data?.email}</span>
                </>)
            },

        },
        {
            title: "Avatar",
            dataIndex: "avatar",
            key: "avatar",
            width: '10%',
            render: (text, data, index) => {
                return data?.image != null ? (
                    <img key={index} style={{ width: 40, height: 40, objectFit: "cover", borderRadius: "50%", }} src={`${data?.image}`} alt={data?.account?.image} />
                ) : (
                    <Avatar size={40} style={{ fontSize: "20px", display: "flex", justifyContent: "center", alignItems: "center" }} icon={data?.account?.email?.substr(0, 1)} />
                );
            },
        },
    ]
    return <div>
        <div className="flex items-center mb-2">
            <div className="text-xl flex items-center mr-2 text-blue-600"><EyeOutlined></EyeOutlined></div>
            <h2 className="text-lg text-gray-500 mr-2 my-0">Followers By Candidate : </h2>
            <h4 className="text-lg text-gray-400 my-0">{data?.length}</h4>
        </div>
        <div className='d-flex mb-3'>
            <h3 className='text-lg'>Manage Followers Company By Candidate : </h3>
        </div>
        <Table columns={columns} dataSource={data} rowKey={'id'} />
    </div>

};

export default FollowCompanyByCandidate;