import { Fragment, useEffect, useState } from "react";
import { Route } from "react-router-dom";
import {
  HomeOutlined, UserOutlined, SearchOutlined, AuditOutlined,
  ApartmentOutlined, BankOutlined, LinkOutlined,
  HomeFilled, ContainerOutlined, SolutionOutlined, IssuesCloseOutlined,
  EditOutlined, DeleteOutlined, BarChartOutlined, FundViewOutlined
} from '@ant-design/icons';

import { Layout, Menu, theme, Button, Input, Modal, Table, Avatar } from 'antd';
import { NavLink } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { TOKEN } from "../util/settings/config";
import { history } from "../App";
import UserAvatar from "../components/UserAvatar/UserAvatar";
import { getCurrentUserAction } from "../redux/actions/UserAction";
import { search } from "../redux/actions/SearchAction";
import { deleteAccountAction, } from "../redux/actions/AccountAction";
import { deleteCompanyAction } from '../redux/actions/CompanyAction';
import { deleteJobAction } from '../redux/actions/JobAction';

const { Header, Content, Sider } = Layout;

function getItem(label, key, icon, children) {
  return {
    key,
    icon,
    children,
    label,
  };
}


export const AdminTemplate = (props) => { //path, exact, Component
  const dispatch = useDispatch();
  let { userLogin } = useSelector(state => state.UserReducer);
  let { arrData } = useSelector(state => state.SearchReducer)
  const { Component, ...restProps } = props;
  const [collapsed, setCollapsed] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  let [code, setCode] = useState(null)
  const { token: { colorBgContainer }, } = theme.useToken();

  const selectedKeys = ['/admin/busmng', '/admin/stationmng', '/admin/theatremng', '/admin/theatrechildmng', '/admin/users',]
  const selectedKey = (selectedKeys.indexOf(props.path) + 1).toString();

  let accessToken = {}
  if (localStorage.getItem(TOKEN)) {
    accessToken = localStorage.getItem(TOKEN)
  } else {
    history.replace('/')
  }

  const showModal = () => {
    setIsModalOpen(true);
  };
  const handleOk = () => {
    setIsModalOpen(false);
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };

  const handleOnChange = (e) => {
    setCode(e.target.value);
  }

  const handleSearch = (e) => {
    e.preventDefault();
    showModal()
    if (code !== null) {
      dispatch(search(code));
    }
  }


  useEffect(() => {
    if (accessToken != null) {
      dispatch(getCurrentUserAction(accessToken))
    }
    window.scrollTo(0, 0);
  }, [dispatch])

  if (userLogin && (userLogin?.role !== 'ADMIN' && userLogin?.role !== 'EMPLOYER')) {
    history.replace('/')
  }


  const columnsCompany = [
    {
      ellipsis: true,
      title: 'Id',
      dataIndex: 'id',
      key: 'id',
      width: "5%",
      ellipsis: true,
      sorter: (a, b) => a.id - b.id,
      sortDirections: ['descend', 'ascend'],
    },
    {
      ellipsis: true,
      title: 'Benifit',
      dataIndex: 'benefit',
      key: 'benefit',
      width: '12%',
      // ...getColumnSearchProps('benefit'),
      sorter: (a, b) => a.benefit - b.benefit,
      sortDirections: ['descend', 'ascend'],
      render: (text, index) => { return <span key={index} >{text != null ? ('' || text.replace(/<[^>]+>/g, '')) : ''}</span> }
    },
    {
      ellipsis: true,
      title: 'Enable',
      dataIndex: 'enable',
      key: 'enable',
      width: '7%',
      // ...getColumnSearchProps('enable'),
      sorter: (a, b) => a.enable - b.enable,
      sortDirections: ['descend', 'ascend'],
    },
    {
      ellipsis: true,
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
      width: '7%',
      // ...getColumnSearchProps('name'),
      sorter: (a, b) => a.name - b.name,
      sortDirections: ['descend', 'ascend'],
      render: (text, index) => { return <span key={index} >{text != null ? ('' || text.replace(/<[^>]+>/g, '')) : ''}</span> }

    },
    {
      ellipsis: true,
      title: 'Nationality',
      dataIndex: 'nationnality',
      key: 'nationnality',
      width: '7%',
      // ...getColumnSearchProps('nationnality'),
      sorter: (a, b) => a.nationnality - b.nationnality,
      sortDirections: ['descend', 'ascend'],
    },
    {
      ellipsis: true,
      title: 'Profession',
      dataIndex: 'profession',
      key: 'profession',
      width: '10%',
      // ...getColumnSearchProps('profession'),
      sorter: (a, b) => a.profession - b.profession,
      sortDirections: ['descend', 'ascend'],
    },
    {
      ellipsis: true,
      title: 'Skill',
      dataIndex: 'skill',
      key: 'skill',
      width: '10%',
      // ...getColumnSearchProps('skill'),
      sorter: (a, b) => a.skill - b.skill,
      sortDirections: ['descend', 'ascend'],

    },
    {
      ellipsis: true,
      title: "Logo",
      dataIndex: "logo_image",
      key: "logo_image",
      width: '7%',

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
      ellipsis: true,
      title: "Background",
      dataIndex: "background_image",
      key: "background_image",
      width: '10%',

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
      ellipsis: true,
      title: 'Account',
      dataIndex: 'account_id ',
      key: 'account_id ',
      width: '9%',
      // ...getColumnSearchProps('account_id '),
      sorter: (a, b) => a.account_id - b.account_id,
      sortDirections: ['descend', 'ascend'],
      render: (text, account) => {
        return (<>
          <span>{account.account?.name}</span>
        </>)
      },
    },
    {
      ellipsis: true,
      title: 'Manage',
      width: '10%',
      render: (text, company) => {
        return <>
          <Button key={1} href={`/admin/companymng/edit/${company.id}`} type="link" icon={<EditOutlined />} onClick={() => {
          }}></Button>
          <Button key={2} type="link" danger icon={<DeleteOutlined />} onClick={() => {
            if (window.confirm('Do you want to delete ' + company.name + '?')) {
              dispatch(deleteCompanyAction(company.id))
            }
          }}></Button>
        </>

      }
    },
  ]
  const columnsAccount = [
    {
      ellipsis: true,
      title: "ID",
      dataIndex: "id",
      key: "id",
      width: "5%",
      sorter: (a, b) => a.id - b.id,
      sortDirections: ["descend", "ascend"],
    },
    {
      ellipsis: true,
      title: "Name",
      dataIndex: "name",
      key: "name",
      width: "20%",
      // ...getColumnSearchProps("name"),
      sorter: (a, b) => a.name.length - b.name.length,
      sortDirections: ["descend", "ascend"],
    },
    {
      ellipsis: true,
      title: "Email",
      dataIndex: "email",
      width: "20%",
      key: "email",
      // ...getColumnSearchProps("email"),
      sorter: (a, b) => a.email.length - b.email.length,
      sortDirections: ["descend", "ascend"],
    },
    {
      ellipsis: true,
      title: "Gender",
      dataIndex: "gender",
      width: "10%",
      key: "gender",
      // ...getColumnSearchProps("gender"),
      sorter: (a, b) => a.gender.length - b.gender.length,
      sortDirections: ["descend", "ascend"],
    },
    {
      ellipsis: true,
      title: "Address",
      dataIndex: "address",
      key: "address",
      width: "15%",
      // ...getColumnSearchProps("address"),
      sorter: (a, b) => a.address.length - b.address.length,
      sortDirections: ["descend", "ascend"],
    },
    {
      ellipsis: true,
      width: "7%",
      title: "Avatar",
      dataIndex: "avatar",
      key: "avatar",
      render: (text, data, index) => {
        return data.image != null ? (
          <img key={index} style={{ width: 40, height: 40, objectFit: "cover", borderRadius: "50%", }} src={`${data.image}`} alt={data.image} />
        ) : (
          <Avatar size={40} style={{ fontSize: "20px", display: "flex", justifyContent: "center", alignItems: "center" }} icon={data.email.substr(0, 1)} />
        );
      },
    },
    {
      ellipsis: true,
      width: "10%",
      title: "Role",
      dataIndex: "role",
      key: "role",
      // ...getColumnSearchProps("role")
    },
    {
      ellipsis: true,
      width: "10%",
      title: "Manage",
      render: (text, data, index) => {
        return (<>
          <Button
            key={1}
            href={`/admin/accmng/edit/` + data.id}
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
                  data.name +
                  "?"
                )
              ) {
                dispatch(deleteAccountAction(data.id));
              }
            }}
          ></Button></>
        );
      },
    },
  ];
  const columnsJob = [
    {
      ellipsis: true,
      title: 'Id',
      dataIndex: 'id',
      key: 'id',
      width: '5%',
      sorter: (a, b) => a.id - b.id,
      sortDirections: ['descend', 'ascend'],
    },
    {
      ellipsis: true,
      title: 'Title',
      dataIndex: 'title',
      key: 'title',
      width: '15%',
      // ...getColumnSearchProps('title'),
      sorter: (a, b) => a.title - b.title,
      sortDirections: ['descend', 'ascend'],
      render: (text, index) => { return <span key={index} >{'' || text.replace(/<[^>]+>/g, '')}</span> }

    },
    {
      title: 'Skill Required',
      dataIndex: 'skill_required',
      ellipsis: true,
      key: 'skill_required',
      width: '15%',
      // ...getColumnSearchProps('skill_required'),
      sorter: (a, b) => a.skill_required - b.skill_required,
      sortDirections: ['descend', 'ascend'],
      render: (text, index) => { return <span key={index} >{'' || text.replace(/<[^>]+>/g, '')}</span> }

    },
    {
      ellipsis: true,
      title: 'Benefit',
      dataIndex: 'benefit',
      key: 'benefit',
      width: '15%',
      // ...getColumnSearchProps('benefit'),
      sorter: (a, b) => a.benefit - b.benefit,
      sortDirections: ['descend', 'ascend'],
      render: (text, index) => { return <span key={index} >{'' || text.replace(/<[^>]+>/g, '')}</span> }

    },
    {
      ellipsis: true,
      title: 'Company',
      dataIndex: 'company_id ',
      key: 'company_id ',
      width: '15%',
      // ...getColumnSearchProps('company_id '),
      sorter: (a, b) => a.company_id - b.company_id,
      sortDirections: ['descend', 'ascend'],
      render: (text, company) => {
        return (<>
          <span>{company.company?.name}</span>
        </>)
      },
    },
    {
      ellipsis: true,

      title: 'Location',
      dataIndex: 'location_id ',
      key: 'location_id ',
      width: '15%',
      // ...getColumnSearchProps('location_id '),
      sorter: (a, b) => a.location_id - b.location_id,
      sortDirections: ['descend', 'ascend'],
      render: (text, location) => {
        return (<>
          <span>{location.location?.name}</span>
        </>)
      },
    },
    {
      ellipsis: true,
      title: 'JobType',
      dataIndex: 'job_type_id ',
      key: 'job_type_id ',
      width: '10%',
      // ...getColumnSearchProps('job_type_id '),
      sorter: (a, b) => a.job_type_id - b.job_type_id,
      sortDirections: ['descend', 'ascend'],
      render: (text, jobType) => {
        return (<>
          <span>{jobType.jobType?.name}</span>
        </>)
      },
    },
    {
      ellipsis: true,
      title: 'Manage',
      width: '10%',
      render: (text, job) => {
        return <>
          <Button key={1} href={`/jobmng/edit/${job.id}`} type="link" icon={<EditOutlined />} onClick={() => {
          }}></Button>

          <Button key={2} type="link" danger icon={<DeleteOutlined />} onClick={() => {
            if (window.confirm('Do you want to delete ' + job.title + '?')) {
              dispatch(deleteJobAction(job.id))
            }
          }}></Button>
        </>

      }
    },
  ]

  const itemsAdmin = [
    getItem('Dashboard', '1', <NavLink className='text-decoration-none' to="/admin/chartmng"><BarChartOutlined /></NavLink>),
    getItem('User Management', 'sub1', <UserOutlined />, [
      getItem('Employer', '2', <NavLink className='text-decoration-none' to="/admin/empmng"><UserOutlined /></NavLink>),
      getItem("Candidate", "3", <NavLink className="text-decoration-none" to="/admin/accmng"><UserOutlined /></NavLink>),
    ]),
    getItem('Customer Management', '4', <NavLink className='text-decoration-none' to="/admin/companymng"><HomeFilled /></NavLink>),
    getItem('City Province ', '5', <NavLink className='text-decoration-none' to="/admin/cityprovincemng"><BankOutlined /></NavLink>),
    getItem('Subscription Plan Management', '6', <NavLink className='text-decoration-none' to="/admin/subplanmng"><ContainerOutlined /></NavLink>),
    getItem('Industry Management', 'sub2', <IssuesCloseOutlined />, [
      getItem('Industry ', '7', <NavLink className='text-decoration-none' to="/admin/industry"><i className="fa fa-check"></i></NavLink>),
      getItem('Skill ', '8', <NavLink className='text-decoration-none' to="/admin/skillmng"><i className="fa fa-check"></i></NavLink>),
    ]),
    getItem('Advertisement ', '9', <NavLink className='text-decoration-none' to="/admin/advertisementmng/advertisementmng"><LinkOutlined /></NavLink>),
    getItem('Job Type Management', '10', <NavLink className='text-decoration-none' to="/admin/jobtypemng"><AuditOutlined /></NavLink>),
    getItem('Level Management', '11', <NavLink className='text-decoration-none' to="/admin/levelmng"><ApartmentOutlined /></NavLink>),


  ]


  const itemsEmployer = [
    getItem('Your Profile Detail', '1', <NavLink className='text-decoration-none' to="/employer/emprofile"><i className="fa-solid fa-user"></i></NavLink>),
    getItem('Your Subcription Plan', '2', <NavLink className='text-decoration-none' to="/employer/employersubmng"><i className="fa fa-tags"></i></NavLink>),
    getItem('Subscription Plan', '3', <NavLink className='text-decoration-none' to="/employer/showsubcriptionplan"><ContainerOutlined /></NavLink>),
    getItem('Curriculum Vitea Saved', '4', <NavLink className='text-decoration-none' to="/employer/cvSaved"><SolutionOutlined /></NavLink>),
    getItem('Follow Company Management', '5', <NavLink className='text-decoration-none' to="/employer/followCompany"><FundViewOutlined /></NavLink>),
  ]


  const operations = <Fragment>
    <div className="d-flex items-center">
      <form className="m-auto" onSubmit={handleSearch}>
        <Input style={{ width: 350 }} onChange={handleOnChange} value={code} placeholder="Search Account, Company or Job" prefix={<SearchOutlined />}></Input>
      </form>
      <Button type="link" href="/"><HomeOutlined style={{ fontSize: '24px' }} /></Button>
      <UserAvatar />
    </div>
  </Fragment>

  return <Route {...restProps} render={(propsRoute, index) => {
    return <Fragment key={index}>
      <Layout style={{ minHeight: '100vh', }}>
        <Sider collapsible width={300} collapsed={collapsed} onCollapse={(value) => setCollapsed(value)}>
          {userLogin?.role === "ADMIN" ? <div className="demo-logo-vertical text-white text-2xl text-center my-10" >Admin Page</div>
            : <div className="demo-logo-vertical text-white text-2xl text-center my-10" >Employer Page</div>}

          <Menu theme="dark" defaultSelectedKeys={selectedKey} mode="inline" items={userLogin?.role === "ADMIN" ? itemsAdmin : itemsEmployer} />
        </Sider>
        <Layout>
          <Header
            style={{
              padding: 0,
              background: colorBgContainer,
              display: 'flex',
              justifyContent: 'end',
              alignItems: 'center',
              paddingRight: '30px'
            }}
          >
            <div>{operations}</div>
          </Header>
          <Content style={{ margin: '16px' }} >
            <div style={{ padding: 24, minHeight: 360, background: colorBgContainer, }} >
              <Component {...propsRoute} />
            </div>
          </Content>
        </Layout>
      </Layout>
      <Modal title={`Search: ${code}`} open={isModalOpen} maskClosable={true} afterClose={() => { code = "" }} footer={null} width={'90%'} onOk={handleOk} onCancel={handleCancel}>
        {arrData?.companies?.length > 0 ? <div>Company:<Table columns={columnsCompany} dataSource={arrData?.companies} rowKey={'id'} pagination={false} /></div> : ""}
        {arrData?.accounts?.length > 0 ? <div> Account:<Table columns={columnsAccount} dataSource={arrData.accounts} rowKey={"id"} pagination={false} /></div> : ""}
        {arrData?.jobs?.length > 0 ? <div>Job: <Table columns={columnsJob} dataSource={arrData.jobs} rowKey={"id"} pagination={false} /></div> : ""}
      </Modal>
    </Fragment>
  }} />
}