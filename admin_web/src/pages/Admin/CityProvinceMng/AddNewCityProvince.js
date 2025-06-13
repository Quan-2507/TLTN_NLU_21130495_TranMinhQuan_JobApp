import React, { useEffect, useRef } from 'react';
import { Form, Input, Button, notification } from 'antd';
import { useFormik } from 'formik';
import { useDispatch } from 'react-redux';
import { addCityProvinceAction } from '../../../redux/actions/CityProvinceAction';
import { useState } from 'react';


const AddNewCityProvince = () => {
    const dispatch = useDispatch();
    // tạo ra two way biding để tương tác data (nhập data để tìm kiếm)


    // useEffect(() => {
    //     if (inputValue.startsWith(' ')) {
    //         inputRef.current.focus();
    //     }
    // }, [inputValue]);

    const formik = useFormik({
        initialValues: {
            name: ''
        },
        onSubmit: (values) => {
            if (values?.name?.trim() === "" || values?.name?.startsWith(' ') === true) {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>
                            Please fill in all required fields and No leading spaces!
                        </>
                    ),
                });
            } else {
                let formData = new FormData();
                for (let key in values) {
                    formData.append(key, values[key]);
                }
                dispatch(addCityProvinceAction(formData));
            }
        }
    })


    return (
        <Form
            onSubmitCapture={formik.handleSubmit}
            labelCol={{
                span: 6,
            }}
            wrapperCol={{
                span: 14,
            }}
            layout="horizontal"
        >
            <h3 className="text-2xl">Add New City Province</h3>
            <div className='row'>
                <div className='col-12'>
                    <Form.Item
                        label="Name"
                        name="name"
                        style={{ minWidth: '100%' }}
                        rules={[
                            {
                                required: true,
                                message: 'City Province is required!',
                                transform: (value) => value.trim(),
                            },
                        ]}
                    >
                        <Input name="name"
                            onChange={formik.handleChange}
                        />
                    </Form.Item>

                    <Form.Item label="Action">
                        <Button htmlType="submit" >Add New City Province</Button>
                    </Form.Item>
                </div>
            </div>

        </Form>
    );
};

export default AddNewCityProvince;