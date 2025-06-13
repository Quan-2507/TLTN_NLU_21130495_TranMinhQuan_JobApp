import React, { useEffect } from 'react'
import { SearchOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { Button, Input, Space, Table, Tabs, Modal, Switch } from 'antd';
import { useRef, useState } from 'react';
import Highlighter from 'react-highlight-words';
import { useDispatch, useSelector } from 'react-redux';

import { TOKEN } from '../../../util/settings/config';
import { getCompanyAndJobByTokenAction, getCompanyForEmployerFromAdminById } from '../../../redux/actions/AccountAction';
import { deleteJobAction, updateEnableOfJobByAdmin, updateEnableOfJobByEmployer } from '../../../redux/actions/JobAction';
import ModalApplicationByJob from '../Modal/ModalApplicationJob';
import moment from 'moment';



export default function EmployerJobMng(props) {

    const dispatch = useDispatch();
    const idOfEmployer = props?.idOfEmployer?.id
    let { employerCompanyJob } = useSelector(state => state.AccountReducer);
    let { userLogin } = useSelector(state => state.UserReducer);
    let { dataCompanyForEmployerFromAdmin } = useSelector(state => state.AccountReducer);
    let accessToken = {}
    if (localStorage.getItem(TOKEN)) {
        accessToken = localStorage.getItem(TOKEN)
    }
    useEffect(() => {
        if (userLogin?.role === "EMPLOYER") {
            dispatch(getCompanyAndJobByTokenAction(accessToken))
        } else if (userLogin?.role === "ADMIN") {
            dispatch(getCompanyForEmployerFromAdminById(idOfEmployer))
        }
    }, [userLogin])
    const today = moment();
    const currentDay = today.format("YYYY-MM-DD")
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [idJob, setIdJob] = useState(0);
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
    const showModal = () => {
        setIsModalOpen(true);
    };
    const handleOk = () => {
        setIsModalOpen(false);
    };
    const handleCancel = () => {
        setIsModalOpen(false);
    };
    const data = employerCompanyJob?.companyForEmployer || dataCompanyForEmployerFromAdmin?.companyForEmployer;


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
            title: 'Title',
            dataIndex: 'title',
            key: 'title',
            width: '5%',
            ...getColumnSearchProps('title'),
            sorter: (a, b) => a.title - b.title,
            sortDirections: ['descend', 'ascend'],
            render: (text, index) => { return <p key={index} className='text-ellipsis overflow-hidden line-clamp-2'>{text == null ? "" : text.replace(/<[^>]+>/g, '')}</p> }

        },
        {
            title: 'Skill Required',
            dataIndex: 'skill_required',
            key: 'skill_required',
            width: '15%',
            ...getColumnSearchProps('skill_required'),
            sorter: (a, b) => a.skill_required - b.skill_required,
            sortDirections: ['descend', 'ascend'],
            render: (text, index) => { return <p key={index} className='text-ellipsis overflow-hidden line-clamp-2'>{text == null ? "" : text.replace(/<[^>]+>/g, '')}</p> }

        },
        {
            title: 'Company',
            dataIndex: 'company_id',
            key: 'company_id ',
            width: '10%',
            ...getColumnSearchProps('company_id '),
            sorter: (a, b) => a.company_id - b.company_id,
            sortDirections: ['descend', 'ascend'],
            render: (text, company) => {
                return (<div className='text-ellipsis overflow-hidden line-clamp-2'>
                    <span>{company.company?.name}</span>
                </div>)
            },
        },
        {
            title: 'Location',
            dataIndex: 'location_id ',
            key: 'location_id ',
            width: '10%',
            ...getColumnSearchProps('location_id '),
            sorter: (a, b) => a.location_id - b.location_id,
            sortDirections: ['descend', 'ascend'],
            render: (text, company) => {
                return (
                    <div className='text-ellipsis overflow-hidden line-clamp-2'>
                        <span>{company.company?.location}</span>
                    </div>
                )
            },
        },
        {
            title: 'JobType',
            dataIndex: 'job_type_id ',
            key: 'job_type_id ',
            width: '5%',
            ...getColumnSearchProps('job_type_id '),
            sorter: (a, b) => a.job_type_id - b.job_type_id,
            sortDirections: ['descend', 'ascend'],
            render: (text, jobType) => {
                return (<>
                    <span>{jobType.jobType?.name}</span>
                </>)
            },
        },
        {
            title: 'Enable',
            dataIndex: '_active',
            width: '5%',
            key: '_active',
            sortDirections: ['descend', 'ascend'],
            render: (text, job) => {
                return <Switch size="small" defaultChecked={job?._active} checked={job?.job?._active} onClick={() => {
                    userLogin?.role === 'ADMIN' ? dispatch(updateEnableOfJobByAdmin(job?.id)) : dispatch(updateEnableOfJobByEmployer(job?.id))
                }} />
            },
        },
        {
            title: 'Manage',
            width: '15%',
            render: (text, job) => {
                return <div className='flex items-center gap-1'>
                    {
                        // Job are recruting
                        job?.start_date >= currentDay ?
                            <div>
                                <Button key={1} href={`/jobmng/edit/${job.id}`} type="link" icon={<EditOutlined />} onClick={() => { }}></Button>
                            </div> :
                            <Button key={3} onClick={() => {
                                setIdJob(job.id)
                                showModal()
                            }}>View Application</Button>
                    }

                    {
                        //job posted
                        job?.end_date >= currentDay ? <Button key={2} type="link" danger icon={<DeleteOutlined />} onClick={() => {
                            if (window.confirm('Do you want to delete ' + job.title + '?')) {
                                dispatch(deleteJobAction(job.id))
                            }
                        }}></Button> : <div></div>}


                </div>

            }
        },
    ]
    const onChange = (key) => {
    };
    const items = [
        {
            key: '1',
            children: data?.jobsOpening ? <Table columns={columns} dataSource={data.jobsOpening} rowKey={'id'} /> : <Table columns={columns} dataSource={[]} rowKey={'id'} />,
            label: 'Jobs Are Recruiting',
        },
        {
            key: '2',
            label: 'Job Posted',
            children: data?.jobsOpened ? <Table columns={columns} dataSource={data.jobsOpened} defaultDataSource={[]} rowKey={'id'} /> : <Table columns={columns} dataSource={[]} rowKey={'id'} />,
        },
        {
            key: '3',
            label: 'Upcoming Job',
            children: data?.jobsNotOpen ? <Table columns={columns} dataSource={data.jobsNotOpen} rowKey={'id'} /> : <Table columns={columns} dataSource={[]} rowKey={'id'} />,
        },
    ];

    return <div>
        {/* d-flex mb-3 items-center */}
        <div className=''>
            <div className='d-flex mb-1 items-center'>
                {
                    userLogin?.role === "EMPLOYER" ? employerCompanyJob?.count_jobs >= employerCompanyJob?.limit_job || employerCompanyJob?.companyForEmployer?.subcriptionPlan == null ?
                        <h3 className='alert ml-2 text-base text-yellow-800 rounded-lg bg-yellow-50 w-100 text-left'>
                            <a href='/employer/buyScPl'>You've reached your creation limit for job or Don't register Subcription Plan !!!.</a>
                        </h3> :
                        <Button href={`/jobmng/addjob/${employerCompanyJob?.companyForEmployer?.id}`} type="primary" className='ml-3 small bg-primary'>+ Add New Job</Button> :
                        <Button href={`/jobmng/addjob/${dataCompanyForEmployerFromAdmin?.companyForEmployer?.id}`} type="primary" className='ml-3 small bg-primary'>+ Add New Job</Button>
                }
            </div>
            {userLogin?.role === "EMPLOYER" && <div className='flex items-center '>
                <h3 className='text-lg text-gray-600 italic'> Jobs created : </h3>
                <h5 className='ml-2 text-base text-gray-500'>{employerCompanyJob?.count_jobs < 0 ? 0 : employerCompanyJob?.count_jobs} / {employerCompanyJob?.limit_job}</h5>
            </div>}

        </div>
        <Tabs defaultActiveKey="1" items={items} onChange={onChange} />

        {/* Application from Job */}
        <Modal width={'90%'} title="" open={isModalOpen} onOk={handleOk} onCancel={handleCancel}>
            <ModalApplicationByJob jobId={idJob}></ModalApplicationByJob>
        </Modal>
    </div>
}
