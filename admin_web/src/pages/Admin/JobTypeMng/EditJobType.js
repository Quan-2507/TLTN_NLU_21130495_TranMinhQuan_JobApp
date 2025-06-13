import React, { useEffect } from 'react';
import { Form, Input, Button, notification } from 'antd';
import { useFormik } from 'formik';
import { useDispatch, useSelector } from 'react-redux';
import { getJobTypeByIdAction, updateJobTypeByIdAction } from '../../../redux/actions/JobTypeAction';


const EditJobType = (props) => {
    const dispatch = useDispatch();
    const { JobTypeDetail } = useSelector(state => state.JobTypeReducer)
    let { id } = props.match.params;
    useEffect(() => {
        dispatch(getJobTypeByIdAction(id));
    }, [dispatch, id])

    const formik = useFormik({
        enableReinitialize: true,
        initialValues: {
            name: JobTypeDetail?.name
        },
        onSubmit: (values) => {
            if (values.name === "" || values?.name?.trim() === "") {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>
                            Please fill in all required fields.
                        </>
                    ),
                });
            } else {
                let formData = new FormData();
                for (let key in values) {
                    formData.append(key, values[key]);
                }
                dispatch(updateJobTypeByIdAction(id, formData))
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
            <h3 className="text-2xl">Edit Job Type:</h3>
            <div className='row'>
                <div className='col-12'>
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
                    <Form.Item label="Action">
                        <Button htmlType="submit">Update Job Type</Button>
                    </Form.Item>
                </div>
            </div>
        </Form>
    );
};

export default EditJobType;