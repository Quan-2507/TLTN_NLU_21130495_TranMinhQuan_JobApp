import React, { useEffect } from 'react';
import { Form, Input, Button, notification, Select } from 'antd';
import { useFormik } from 'formik';
import { useDispatch, useSelector } from 'react-redux';
import { addSkillAction } from '../../../redux/actions/SkillAction';
import { getIndustryListAction } from '../../../redux/actions/IndustryAction';
// import { getCompanyListAction } from '../../../redux/actions/CompanyAction';



const AddNewSkill = () => {
    const dispatch = useDispatch();
    let { arrIndustry } = useSelector(state => state.IndustryReducer);
    useEffect(() => {
        dispatch(getIndustryListAction())
    }, [dispatch]);


    const formik = useFormik({
        initialValues: {
            name: '',
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
                dispatch(addSkillAction(formData));
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
            <h3 className="text-2xl">Add New Skill</h3>
            <div className='row'>
                <div className='col-12'>
                    <Form.Item
                        label="Name"
                        name="name"
                        style={{ minWidth: '100%' }}
                        rules={[
                            {
                                required: true,
                                message: 'Skill is required!',
                                transform: (value) => value.trim(),
                            },
                        ]}
                    >
                        <Input name="name" onChange={formik.handleChange} />
                    </Form.Item>

                    <Form.Item
                        label="Industry"
                        name='industry'
                        style={{ minWidth: '100%' }}
                        rules={[
                            {
                                required: true,
                                message: 'Industry is required!',
                                transform: (value) => value.trim(),
                            },
                        ]}
                    >
                        <Select
                            rules={[{ required: true }]}
                            options={arrIndustry ? arrIndustry?.data?.map((item, index) => ({ key: index, label: item.name, value: item.id })) : ''}
                            onChange={handleChangeIndustry}
                        />
                    </Form.Item>

                    <Form.Item label="Action">
                        <Button htmlType="submit" >Add New Skill</Button>
                    </Form.Item>
                </div>
            </div>

        </Form>
    );
};

export default AddNewSkill;