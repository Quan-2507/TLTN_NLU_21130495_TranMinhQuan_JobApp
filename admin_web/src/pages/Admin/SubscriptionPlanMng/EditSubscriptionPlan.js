import React, { useEffect } from 'react';
import { Form, Input, Button, notification } from 'antd';
import { useFormik } from 'formik';
import { useDispatch, useSelector } from 'react-redux';
import { getSubscriptionPlanByIdAction, updateSubscriptionPlanByIdAction } from '../../../redux/actions/SubscriptionPlanAction';


const EditSubscriptionPlan = (props) => {
    const dispatch = useDispatch();
    const { subscriptionPlanDetail } = useSelector(state => state.SubscriptionPlanReducer)

    let { id } = props.match.params;
    useEffect(() => {
        dispatch(getSubscriptionPlanByIdAction(id));
    }, [dispatch, id])

    const formik = useFormik({
        enableReinitialize: true,
        initialValues: {
            expiry: subscriptionPlanDetail?.expiry,
            name: subscriptionPlanDetail?.name,
            price: subscriptionPlanDetail?.price,
            description: subscriptionPlanDetail?.description,
        },
        onSubmit: (values) => {
            if (values?.expiry === ""
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
            } else {
                let formData = new FormData();
                for (let key in values) {
                    formData.append(key, values[key]);
                }
                dispatch(updateSubscriptionPlanByIdAction(id, formData))
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
            <h3 className="text-2xl">Edit SubcriptionPlan:</h3>
            <div className='row'>
                <div className='col-12'>
                    <Form.Item
                        label="Expiry"
                        style={{ minWidth: '100%' }}
                        rules={[
                            {
                                required: true,
                                message: 'Expiry is required!',
                                transform: (value) => value.trim(),
                            },
                        ]}
                    >
                        <Input name="expiry" onChange={formik.handleChange} value={formik.values.expiry} type='number' />
                    </Form.Item>
                    <Form.Item
                        label="Name"
                        style={{ minWidth: '100%' }}
                        rules={[
                            {
                                required: true,
                                message: 'Name is required!',
                                transform: (value) => value.trim(),
                            },
                        ]}
                    >
                        <Input name="name" onChange={formik.handleChange} value={formik.values.name} />
                    </Form.Item>
                    <Form.Item
                        label="Description"
                        style={{ minWidth: '100%' }}
                        rules={[
                            {
                                required: true,
                                message: 'description is required!',
                                transform: (value) => value.trim(),
                            },
                        ]}
                    >
                        <Input name="description" onChange={formik.handleChange} value={formik.values.description} />
                    </Form.Item>
                    <Form.Item
                        label="Price"
                        style={{ minWidth: '100%' }}
                        rules={[
                            {
                                required: true,
                                message: 'Price is required!',
                                transform: (value) => value.trim(),
                            },
                        ]}
                    >
                        <Input name="price" onChange={formik.handleChange} value={formik.values.price} type='number' />
                    </Form.Item>
                    <Form.Item label="Action">
                        <Button htmlType="submit">Update SubcriptionPlan</Button>
                    </Form.Item>
                </div>
            </div>
        </Form>
    );
};

export default EditSubscriptionPlan;