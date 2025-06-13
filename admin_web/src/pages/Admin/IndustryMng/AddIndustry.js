import React from 'react';
import { Form, Input, Button, notification } from 'antd';
import { useFormik } from 'formik';
import { useDispatch } from 'react-redux';
import { addIndustryAction } from '../../../redux/actions/IndustryAction';


const AddNewIndustry = () => {
    const dispatch = useDispatch();

    const formik = useFormik({
        initialValues: {
            name: ''
        },
        onSubmit: (values) => {
            if (values?.name?.trim() === '' || values?.name?.startsWith(' ') === true) {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>
                            Please fill in all required fields. No leading spaces!
                        </>
                    ),
                });
            } else {
                let formData = new FormData();
                for (let key in values) {
                    formData.append(key, values[key]);
                }
                dispatch(addIndustryAction(formData));
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
            <h3 className="text-2xl">Add New Job Type</h3>
            <div className='row'>
                <div className='col-12'>
                    <Form.Item
                        label="Name"
                        name="name"
                        style={{ minWidth: '100%' }}
                        rules={[
                            {
                                required: true,
                                message: 'Name  is required!',
                                transform: (value) => value.trim(),
                            },
                        ]}
                    >
                        <Input name="name" onChange={formik.handleChange} />
                    </Form.Item>

                    <Form.Item label="Action">
                        <Button htmlType="submit" >Add New Industry</Button>
                    </Form.Item>
                </div>
            </div>

        </Form>
    );
};

export default AddNewIndustry;