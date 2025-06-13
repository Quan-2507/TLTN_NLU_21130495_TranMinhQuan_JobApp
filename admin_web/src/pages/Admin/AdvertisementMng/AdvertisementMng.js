import React, { Fragment, useEffect } from "react";
import {
    SearchOutlined,
    EditOutlined,
    DeleteOutlined,
} from "@ant-design/icons";
import { Button, Input, Space, Table, Avatar } from "antd";
import { useRef, useState } from "react";
import Highlighter from "react-highlight-words";
import { useDispatch, useSelector } from "react-redux";

import { deleteAdvertisementAction, getAdvertisementListAction } from "../../../redux/actions/AdvertisementAction";

export default function AdvertisementMng() {
    const dispatch = useDispatch();
    let { arrAdvertisement } = useSelector((state) => state.AdvertisementReducer);
    useEffect(() => {
        dispatch(getAdvertisementListAction())
    }, [dispatch]);
    const [searchText, setSearchText] = useState("");
    const [searchedColumn, setSearchedColumn] = useState("");
    const searchInput = useRef(null);
    const handleSearch = (selectedKeys, confirm, dataIndex) => {
        confirm();
        setSearchText(selectedKeys[0]);
        setSearchedColumn(dataIndex);
    };

    const resetSearch = (selectedKeys, confirm, dataIndex) => {
        confirm();
        setSearchText((selectedKeys[0] = ""));
        setSearchedColumn(dataIndex);
    };

    const data = arrAdvertisement?.data;

    const getColumnSearchProps = (dataIndex) => ({
        filterDropdown: ({
            setSelectedKeys,
            selectedKeys,
            confirm,
            clearFilters,
            close,
        }) => (
            <div style={{ padding: 8 }} onKeyDown={(e) => e.stopPropagation()}>
                <Input
                    ref={searchInput}
                    placeholder={`Search`}
                    value={selectedKeys[0]}
                    onChange={(e) =>
                        setSelectedKeys(e.target.value ? [e.target.value] : [])
                    }
                    onPressEnter={() => handleSearch(selectedKeys, confirm, dataIndex)}
                    style={{
                        marginBottom: 8,
                        display: "block",
                    }}
                />
                <Space>
                    <Button
                        type="primary"
                        onClick={() => handleSearch(selectedKeys, confirm, dataIndex)}
                        icon={<SearchOutlined />}
                        size="small"
                        className="bg-primary"
                        style={{ width: 90 }}
                    >
                        Search
                    </Button>
                    <Button
                        onClick={() =>
                            clearFilters && resetSearch(selectedKeys, confirm, dataIndex)
                        }
                        size="small"
                        style={{ width: 90 }}
                    >
                        Reset
                    </Button>
                </Space>
            </div>
        ),
        filterIcon: (filtered) => (
            <SearchOutlined
                style={{
                    color: filtered ? "#1677ff" : undefined,
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
                        backgroundColor: "#ffc069",
                        padding: 0,
                    }}
                    searchWords={[searchText]}
                    autoEscape
                    textToHighlight={text ? text.toString() : ""}
                />
            ) : (
                text
            ),
    });
    const columns = [
        {
            title: "ID",
            dataIndex: "id",
            width: '10%',
            key: "id",
            sorter: (a, b) => a.id - b.id,
            sortDirections: ["descend", "ascend"],
        },
        {
            title: "Path",
            dataIndex: "path",
            width: '50%',
            key: "path",
            ...getColumnSearchProps("path"),
            sorter: (a, b) => a.path.length - b.path.length,
            sortDirections: ["descend", "ascend"],
            render: (text, ad) => {
                return (<div className="flex">
                    <a href={ad?.path} target="_blank" rel="noopener noreferrer" className="text-sm hover:no-underline text-blue flex items-end justify-center hover:cursor-pointer no-underline">
                        {ad?.path}
                    </a>

                </div>)
            },
        },
        {
            title: "Image",
            dataIndex: "avatar",
            key: "avatar",
            width: '20%',
            render: (text, data, index) => {
                return data.image != null ? (
                    <img key={index} style={{ width: 40, height: 40, objectFit: "cover", borderRadius: "10%", }} src={`${data.image}`} alt={data.image} />
                ) : (
                    <Avatar size={40} style={{ fontSize: "20px", display: "flex", justifyContent: "center", alignItems: "center" }} icon={data?.email?.substr(0, 1)} />
                );
            },
        },
        {
            title: "Manage",
            width: '20%',
            render: (text, data, index) => {
                return (
                    <Fragment key={index}>
                        <Button
                            key={1}
                            href={`/admin/advertisementmng/edit/` + data.id}
                            type="link"
                            icon={<EditOutlined />}
                            onClick={() => {
                                // dispatch(updateEmployerByIdAction(data.id));
                            }}
                        ></Button>
                        <Button
                            key={2}
                            type="link"
                            danger
                            icon={<DeleteOutlined />}
                            onClick={() => {
                                if (
                                    window.confirm(
                                        "Are you sure you want to delete " +
                                        data.path +
                                        "?"
                                    )
                                ) {
                                    dispatch(deleteAdvertisementAction(data.id));
                                }
                            }}
                        ></Button>
                    </Fragment>
                );
            },
        },
    ];
    return (
        <div>
            <div className="d-flex mb-3">
                <h3 className="text-lg">Advertisement Management</h3>
                <Button
                    href="/admin/advertisementmng/addadvertisement"
                    type="primary"
                    className="ml-3 small bg-primary"
                >
                    {" "}
                    + Add New Advertisement{" "}
                </Button>
            </div>
            <Table columns={columns} dataSource={data} rowKey={"id"} />
        </div>
    );
}
