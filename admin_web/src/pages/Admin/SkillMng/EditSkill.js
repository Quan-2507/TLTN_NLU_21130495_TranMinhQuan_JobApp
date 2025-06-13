import React, { useEffect } from 'react';
import { Form, Input, Button, notification, Select } from 'antd';
import { useFormik } from 'formik';
import { useDispatch, useSelector } from 'react-redux';
import { getSkillIdAction, updateSkillByIdAction } from '../../../redux/actions/SkillAction';
import { getIndustryListAction } from '../../../redux/actions/IndustryAction';

const EditSkill = (props) => {
    const dispatch = useDispatch();
    const { skillDetail } = useSelector(state => state.SkillReducer)
    let { arrIndustry } = useSelector(state => state.IndustryReducer);

    let { id } = props.match.params;
    useEffect(() => {
        dispatch(getSkillIdAction(id));
        dispatch(getIndustryListAction());
    }, [dispatch, id])

    const formik = useFormik({
        enableReinitialize: true,
        initialValues: {
            name: skillDetail?.name,
            industry_id: skillDetail?.id

        },
        onSubmit: (values) => {
            if (values.name.trim() === '' || values?.name?.startsWith(' ') === true) {
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
                dispatch(updateSkillByIdAction(id, formData))
            }
        }
    })

    const handleChangeIndustry = (value) => {
        formik.setFieldValue('industry_id', value)
    }



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
            <h3 className="text-2xl">Edit Skill:</h3>
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

                    <Form.Item
                        label="Industry"
                        style={{ minWidth: '100%' }}
                        rules={[
                            {
                                required: true,
                                message: 'Industry is required!',
                                transform: (value) => value.trim(),
                            },
                        ]}
                    >
                        <Select value={skillDetail?.industry?.name} options={arrIndustry?.data?.map((item, index) => ({ key: index, label: item?.name, value: item.id }))} onChange={handleChangeIndustry} />
                    </Form.Item>


                    <Form.Item label="Action">
                        <Button htmlType="submit">Update Skill</Button>
                    </Form.Item>
                </div>
            </div>
        </Form>
    );
};

export default EditSkill;