import React, { useEffect } from 'react'
import { SearchOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { Button, Input, Space, Table } from 'antd';
import { useRef, useState } from 'react';
import Highlighter from 'react-highlight-words';
import { useDispatch, useSelector } from 'react-redux';
import { deleteLevelAction, getLevelListAction } from '../../../redux/actions/LevelAction';



export default function LevelMng() {
    let { arrLevel } = useSelector(state => state.LevelReducer);
    const dispatch = useDispatch();
    useEffect(() => {
        dispatch(getLevelListAction())
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


    const data = arrLevel.data;

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
            width: '25%',
            sorter: (a, b) => a.id - b.id,
            sortDirections: ['descend', 'ascend'],
        },
        {
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
            width: '50%',
            ...getColumnSearchProps('name'),
            sorter: (a, b) => a.name - b.name,
            sortDirections: ['descend', 'ascend'],
        },
        {
            title: 'Manage',
            width: '25%',
            render: (text, level) => {
                return <>
                    <Button key={1} href={`/admin/levelmng/edit/${level.id}`} type="link" icon={<EditOutlined />} onClick={() => {
                        dispatch(getLevelListAction(level.id))
                    }}></Button>
                    <Button key={2} type="link" danger icon={<DeleteOutlined />} onClick={() => {
                        if (window.confirm('Do you want to delete ' + level.name + '?')) {
                            dispatch(deleteLevelAction(level.id))
                        }
                    }}></Button>
                </>

            }
        },
    ]
    return <div>
        <div className='d-flex mb-3'>
            <h3 className='text-lg'>Level Management</h3>
            <Button href='/admin/levelmng/addlevel' type="primary" className='ml-3 small bg-primary'>+ Add New Level</Button>
        </div>
        <Table columns={columns} dataSource={data} rowKey={'id'} />
    </div>
}
