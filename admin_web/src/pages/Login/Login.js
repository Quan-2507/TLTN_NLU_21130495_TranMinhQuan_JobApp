import React from 'react'
import { Form, Input } from 'antd';
import { useDispatch } from 'react-redux';
import { EnvironmentOutlined, EuroCircleOutlined, AuditOutlined } from '@ant-design/icons';

import { loginAction } from '../../redux/actions/UserAction';



export default function Login(props) {

  const dispatch = useDispatch();

  const onFinish = (values) => {
    const action = loginAction(values);
    dispatch(action)
  };
  const onFinishFailed = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };



  return (

    // 
    <div className="rounded-2xl shadow-xl z-20 flex" style={{ backgroundColor: "rgb(255 255 255 / 80%)" }}>
      <div className='flex flex-col gap-4 w-[500px] bg-blue-500/30 p-9'>
        <div className='flex justify-center items-center gap-4'>
          <div className='p-9 rounded-full bg-red-700'>
            <div className='text-white text-4xl font-bold flex items-center justify-center'>
              <AuditOutlined />
            </div>
          </div>
          <div>
            <h1 className='text-xl text-gray-600 uppercase' style={{ textShadow: '0 0 1px #999' }}>Job announcement</h1>
            <h1 className='text-lg font-normal text-gray-600' style={{ textShadow: '0 0 1px #999' }}>Get updated with the latest job opportunities from many leading companies</h1>
          </div>
        </div>
        <div className='flex justify-center items-center gap-4'>
          <div className='p-9 rounded-full bg-green-500'>
            <div className='text-white text-4xl font-bold flex items-center justify-center'>
              <EuroCircleOutlined />
            </div>
          </div>
          <div>
            <h1 className='text-xl text-gray-600 uppercase' style={{ textShadow: '0 0 1px #999' }}>Find a job easily</h1>
            <h1 className='text-lg font-normal text-gray-600' style={{ textShadow: '0 0 1px #999' }}>Find a job you love that matches your skills and interests</h1>
          </div>
        </div>

        <div className='flex justify-center items-center gap-4'>
          <div className='p-9 rounded-full bg-orange-400'>
            <div className='text-white text-4xl font-bold flex items-center justify-center'>
              <EnvironmentOutlined />
            </div>
          </div>
          <div>
            <h1 className='text-xl text-gray-600 uppercase' style={{ textShadow: '0 0 1px #999' }}>Apply quickly</h1>
            <h1 className='text-lg font-normal text-gray-600' style={{ textShadow: '0 0 1px #999' }}>Easily apply for multiple positions without wasting time</h1>
          </div>
        </div>
      </div>
      <div className='py-8 px-8 '>
        <Form
          name="basic"
          className='d-flex flex-col'
          labelCol={{
            span: 8,
          }}
          wrapperCol={{
            span: 24,
          }}
          style={{
            maxWidth: 350,
            width: 350,
            minWidth: '100%',
          }}
          initialValues={{
            remember: false,
          }}
          onFinish={onFinish}
          onFinishFailed={onFinishFailed}
          autoComplete="on"
        >
          <div>
            <h1 className="text-3xl font-bold text-center mb-4 cursor-pointer">Sign In</h1>
            <p className="text-center text-sm mb-8 font-semibold text-gray-700 tracking-wide">Sign in to continue to DREAM JOB</p>
          </div>
          <Form.Item
            label=""
            name="email"
            style={{ minWidth: '100%' }}
            rules={[
              {
                type: 'email',
                message: 'E-mail is invalid!',
              },
              {
                required: true,
                message: 'E-mail is required!',
                transform: (value) => value.trim(),
              },
            ]}
          >
            <Input className="block text-sm py-2.5 px-4 rounded-lg w-full border outline-none" placeholder="Email" />
          </Form.Item>

          <Form.Item
            label=""
            name="password"
            rules={[
              {
                required: true,
                message: 'Password is required!',
                transform: (value) => value.trim(),
              },
            ]}
          >
            <Input.Password className="d-flex block text-sm py-2.5 px-4 mt-3 rounded-lg w-full border outline-none" placeholder="Password" />
          </Form.Item>



          <div className="text-center">
            <button type="submit" className="py-2 w-64 text-base text-white bg-red-400 rounded-full">Sign In</button>
            <div className="mt-2 text-sm">You want to register company?  <a href='registerCompany' className="underline  cursor-pointer"> Sign Up Company</a></div>
          </div>
        </Form>
      </div>
    </div>
  )
}
