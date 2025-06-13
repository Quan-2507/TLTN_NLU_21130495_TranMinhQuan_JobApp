import React, { useEffect } from 'react';
import { Form, Input, Button, notification } from 'antd';
import { useFormik } from 'formik';
import { useDispatch, useSelector } from 'react-redux';
import { getLevelByIdAction, updateLevelByIdAction } from '../../../redux/actions/LevelAction';


const EditLevel = (props) => {
    const dispatch = useDispatch();
    const { levelDetail } = useSelector(state => state.LevelReducer);

    let { id } = props.match.params;
    useEffect(() => {
        dispatch(getLevelByIdAction(id));
    }, [dispatch, id])

    const formik = useFormik({
        enableReinitialize: true,
        initialValues: {
            name: levelDetail?.name
        },
        onSubmit: (values) => {
            if (values.name.trim() === '') {
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
                dispatch(updateLevelByIdAction(id, formData))
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
            <h3 className="text-2xl">Edit Level:</h3>
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
                        <Button htmlType="submit">Update Level</Button>
                    </Form.Item>
                </div>
            </div>
        </Form>
    );
};

export default EditLevel;