import React, { useState } from "react";
import { Form, Input, Button, Select, Checkbox, notification } from "antd";
import { useDispatch, useSelector } from "react-redux";
import { useFormik } from "formik";
import { useEffect } from "react";
import {
    getAccountByIdAction,
    updateAccountByIdAction,
} from "../../../../redux/actions/AccountAction";
import { DOMAIN } from "../../../../util/settings/config";

const { Option } = Select;



const AccountEdit = (props) => {
    const dispatch = useDispatch();
    const [checked, setChecked] = useState(false);
    const [imgSrc, setImgSrc] = useState("");
    const { accountDetail } = useSelector((state) => state.AccountReducer);
    let { id } = props.match.params;
    useEffect(() => {
        dispatch(getAccountByIdAction(id));
    }, [dispatch, id]);

    const formik = useFormik({
        enableReinitialize: true,
        initialValues: {
            name: accountDetail?.name,
            email: accountDetail?.email,
            password: null,
            gender: accountDetail?.gender,
            address: accountDetail?.address,
            image: accountDetail?.image,
            role: accountDetail?.role
        },
        onSubmit: async (values) => {
            if (
                values?.name?.trim() === "" || values?.name?.startsWith(' ') === true ||
                // values?.email?.trim() === "" || values?.email?.startsWith(' ') === true
                values?.address?.trim() === "" || values?.address?.startsWith(' ') === true
                || values?.password?.trim() === "" || values?.password?.startsWith(' ') === true
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
                    if (key !== "image") {
                        formData.append(key, values[key]);
                    } else {
                        formData.append("image", values["image"]);
                    }
                }
                dispatch(updateAccountByIdAction(id, formData));
            }
        },
    });

    const handleChangeFile = (e) => {
        let file = e.target.files[0];

        if (file.type === 'image/jpeg' || file.type === 'image/jpg' || file.type === 'image/png') {
            let reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = (e) => {
                setImgSrc(e.target.result); //Hình base 64
            };
            formik.setFieldValue("UploadFile", file);
        }
    };


    const onChangeCheck = (e) => {
        setChecked(e.target.checked);
    };

    const handleChangeGender = (value) => {
        formik.setFieldValue("gender", value);
    };

    return (
        <div>
            <h3 className="mb-5">Update infomation {formik.values.role === 'EMPLOYER' ? 'Employer' : 'Candidate'} : {formik.values.name}</h3>
            <Form
                labelCol={{
                    span: 4,
                }}
                wrapperCol={{
                    span: 14,
                }}
                layout="horizontal"
                onSubmitCapture={formik.handleSubmit}
            >
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
                    label="Email"
                    rules={[
                        {
                            type: "email",
                            message: " E-mail is not in the correct format!",
                        },
                        {
                            required: true,
                            message: "E-mail  cannot be blank!",
                        },
                    ]}
                >
                    <Input
                        disabled
                        className="text-dark"
                        name="email"
                        onChange={formik.handleChange}
                        value={formik.values.email}
                        placeholder="Email"
                        readOnly
                    />
                </Form.Item>

                <Form.Item label="Change password?">
                    <Checkbox checked={checked} onChange={onChangeCheck}></Checkbox>
                </Form.Item>

                {checked ? (
                    <Form.Item
                        label="Password"
                        rules={[
                            {
                                required: true,
                                message: "Password  cannot be blank!",
                            },
                        ]}
                    >
                        <Input.Password
                            name="password"
                            onChange={formik.handleChange}
                            value={formik.values.password}
                            placeholder="Password"
                        />
                    </Form.Item>
                ) : (
                    ""
                )}

                <Form.Item
                    label="Gender"
                    rules={[
                        {
                            required: true,
                            message: "Gender cannot be blank!",
                        },
                    ]}
                >
                    <Select name="gender" onChange={handleChangeGender} placeholder="Choose Gender" value={formik.values.gender}>
                        <Option value={1}>Male</Option>
                        <Option value={2}>FeMale</Option>
                    </Select>
                </Form.Item>

                <Form.Item
                    label="Address"
                    style={{ minWidth: '100%' }}
                    rules={[
                        {
                            required: true,
                            message: 'Address is required!',
                            transform: (value) => value.trim(),
                        },
                    ]}
                >
                    <Input name="address" onChange={formik.handleChange} value={formik.values.address} />
                </Form.Item>


                <Form.Item label="Image">
                    <input
                        name="UploadFile"
                        type="file"
                        onChange={handleChangeFile}
                        accept="image/png, image/jpeg,image/gif,image/png"
                    />
                    <br />
                    <img style={{ width: 200, height: 200, objectFit: 'cover', borderRadius: '50%', border: "0.1px solid #ccc" }} src={imgSrc === '' ? accountDetail?.image : imgSrc} alt="..." />
                </Form.Item>

                <Form.Item label="Action">
                    <Button
                        htmlType="submit"
                        className="btn-primary bg-primary"
                        type="primary"
                    >
                        {" "}
                        Update{" "}
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );
};

export default AccountEdit;
