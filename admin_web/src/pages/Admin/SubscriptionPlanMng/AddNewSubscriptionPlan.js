import React from 'react';
import { Form, Input, Button, notification } from 'antd';
import { useFormik } from 'formik';
import { useDispatch } from 'react-redux';
import { addSubscriptionPlanAction } from '../../../redux/actions/SubscriptionPlanAction';

const AddNewSubcriptionPlan = () => {
    const dispatch = useDispatch();

    const formik = useFormik({
        initialValues: {
            expiry: '',
            name: '',
            price: '',
            description: ''
        },
        onSubmit: (values) => {
            if (
                values?.expiry === ""
                || values.name.trim() === '' || values?.name?.startsWith(' ') === true
                || values?.price === ""
                || values.description.trim() === '' || values?.description?.startsWith(' ') === true
            ) {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>
                            Please fill in all required fields. No leading spaces!
                        </>
                    ),
                });
            }
            else if (values?.price < 0 || values?.expiry < 0) {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>
                            Price & Expiry must be than 0
                        </>
                    ),
                });
            }
            else {
                let formData = new FormData();
                for (let key in values) {
                    formData.append(key, values[key]);
                }
                dispatch(addSubscriptionPlanAction(formData));
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
            <h3 className="text-2xl">Add New SubcriptionPlan</h3>
            <div className='row'>
                <div className='col-12'>
                    <Form.Item
                        label="Expiry"
                        name="expiry"
                        style={{ minWidth: '100%' }}
                        rules={[
                            {
                                required: true,
                                message: 'Expiry of seat is required!',
                                transform: (value) => value.trim(),
                            },
                        ]}
                    >
                        <Input name="expiry" type='number' onChange={formik.handleChange} />
                    </Form.Item>

                    <Form.Item
                        label="Name"
                        name="name"
                        style={{ minWidth: '100%' }}
                        rules={[
                            {
                                required: true,
                                message: 'Name of seat is required!',
                                transform: (value) => value.trim(),
                            },
                        ]}
                    >
                        <Input name="name" onChange={formik.handleChange} />
                    </Form.Item>
                    <Form.Item
                        label="Description"
                        name="description"
                    >

                        <Input name="description" onChange={formik.handleChange} />
                    </Form.Item>

                    <Form.Item
                        label="Price"
                        name="price"
                        style={{ minWidth: '100%' }}
                        rules={[
                            {
                                required: true,
                                message: 'Price of seat is required!',
                                transform: (value) => value.trim(),
                            },

                        ]}
                    >
                        <Input name="price" type='number' onChange={formik.handleChange} />
                    </Form.Item>


                    <Form.Item label="Action">
                        <Button htmlType="submit" >Add New SubcriptionPlan</Button>
                    </Form.Item>
                </div>
            </div>

        </Form>
    );
};

export default AddNewSubcriptionPlan;