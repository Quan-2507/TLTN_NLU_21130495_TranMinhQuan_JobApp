import React, { useEffect } from 'react'
import { SearchOutlined, EditOutlined, DeleteOutlined, LineChartOutlined } from '@ant-design/icons';
import { Button, Input, Modal, Space, Switch, Table } from 'antd';
import { useRef, useState } from 'react';
import Highlighter from 'react-highlight-words';
import { useDispatch, useSelector } from 'react-redux';
import dayjs from "dayjs";
import { getCompanyListAction, deleteCompanyAction, updateEnableOfCompanyByAdmin } from '../../../redux/actions/CompanyAction';
import ModalSubcriptionPlanOfEmployer from './Modal/ModalSubcriptionPlanOfEmployer';
// import ModalListJobOfCompany from './Modal/ModalListJobOfCompany';


export default function CompanyMng() {
    let { arrCompany } = useSelector(state => state.CompanyReducer);

    const dispatch = useDispatch();
    useEffect(() => {
        dispatch(getCompanyListAction())
    }, [dispatch])

    const [isModalOpenSubcriptionPlan, setIsModalOpenSubcriptionPlan] = useState(false);
    const [idEmployer, setIdEmployer] = useState();
    const showModalSubcriptopnPlan = () => {
        setIsModalOpenSubcriptionPlan(true);
    };

    const handleOkSubcriptopnPlan = () => {
        setIsModalOpenSubcriptionPlan(false);
    };

    const handleCancelSubcriptopnPlan = () => {
        setIsModalOpenSubcriptionPlan(false);
    };

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
    const data = arrCompany.data;


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
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
            width: '5%',
            ...getColumnSearchProps('name'),
            sorter: (a, b) => a.name - b.name,
            sortDirections: ['descend', 'ascend'],
            render: (text, index) => { return <p key={index} className='text-ellipsis overflow-hidden line-clamp-2'>{text = null ? "" : text.replace(/<[^>]+>/g, '')}</p> }

        },
        {
            title: 'Subcription Plan',
            dataIndex: 'subcriptionPlan_id',
            key: 'subcriptionPlan_id ',
            width: '10%',
            ...getColumnSearchProps('subcriptionPlan_id '),
            sorter: (a, b) => a.subcriptionPlan_id - b.subcriptionPlan_id,
            sortDirections: ['descend', 'ascend'],
            render: (text, data) => {
                return (<div>
                    {data?.subcriptionPlan === null ? <h2 className='font-normal text-sm m-0 px-[15px] py-[4px] text-red-500'>Don't Have Subcription Plan</h2> : <Button type="link" onClick={() => {
                        showModalSubcriptopnPlan()
                        setIdEmployer(data?.account?.id)
                    }}>
                        {data?.subcriptionPlan?.name}
                    </Button>}

                </div>)
            },
        },
        {
            title: "Logo Company",
            dataIndex: "logo_image",
            key: "logo_image",
            width: '5%',

            render: (text, data, index) => {
                return data.logo_image != "null" && data.logo_image != null ? (
                    <img key={index} style={{ width: 80, height: 80, objectFit: "cover", borderRadius: "10%", }}
                        src={`${data.logo_image}`} alt={data.logo_image}
                    />
                ) : (
                    <div>No Image</div>
                );
            },
        },
        {
            title: "Background Company",
            dataIndex: "background_image",
            key: "background_image",
            width: '5%',

            render: (text, data, index) => {
                return data.background_image != "null" && data.background_image != null ? (
                    <img key={index} style={{ width: 80, height: 80, objectFit: "cover", borderRadius: "10%", }}
                        src={`${data.background_image}`} alt={data.background_image}
                    />
                ) : (
                    <div>No Image</div>
                );
            },
        },
        {
            title: 'Account',
            dataIndex: 'account_id',
            key: 'account_id ',
            width: '5%',
            ...getColumnSearchProps('account_id '),
            sorter: (a, b) => a.account_id - b.account_id,
            sortDirections: ['descend', 'ascend'],
            render: (text, data) => {
                return (<>
                    <span>{data.account?.name}</span>
                </>)
            },
        },
        {
            title: 'Enable',
            dataIndex: '_active',
            width: '5%',
            key: '_active',
            sortDirections: ['descend', 'ascend'],
            render: (text, company) => {
                return <Switch size="small"
                    defaultChecked={company?.enable === 1 ? true : false}
                    checked={company?.company?.enable}
                    onClick={() => {
                        dispatch(updateEnableOfCompanyByAdmin(company?.id),
                        )
                    }} />
            },
        },
        {
            title: 'Manage',
            width: '15%',
            render: (text, company) => {
                return <>
                    <Button key={1} href={`/admin/companymng/edit/${company.id}`} type="link" icon={<EditOutlined />} onClick={() => {
                    }}></Button>

                    <Button key={2} type="link" danger icon={<DeleteOutlined />} onClick={() => {
                        if (window.confirm('Do you want to delete ' + company.name + '?')) {
                            dispatch(deleteCompanyAction(company.id))
                        }
                    }}></Button>
                    <Button key={3} href={`/employer/emprofile/${company?.account?.id}`} type='link' title='Dashboard Of Customer' icon={<LineChartOutlined />}></Button >
                </>

            }
        },
    ]
    return <div>
        <div className='d-flex mb-3'>
            <h3 className='text-lg'>Company Management</h3>
            <Button href='/admin/companymng/addcom' type="primary" className='ml-3 small bg-primary'>+ Add New Company</Button>
        </div>
        <Table columns={columns} dataSource={data} rowKey={'id'} />
        <Modal title="Detail Subcription Plan" open={isModalOpenSubcriptionPlan} onOk={handleOkSubcriptopnPlan} onCancel={handleCancelSubcriptopnPlan}>
            <ModalSubcriptionPlanOfEmployer idEmployer={idEmployer}></ModalSubcriptionPlanOfEmployer>
        </Modal>
    </div>
}
