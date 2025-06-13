import React, { useState } from "react";
import { Form, Input, Button, Select, notification } from "antd";
import { useDispatch } from "react-redux";
import { useFormik } from "formik";

import { addAdvertisementAction } from "../../../redux/actions/AdvertisementAction";


const AddAdvertisement = () => {
    const dispatch = useDispatch();
    const [imgSrc, setImgSrc] = useState("");
    const formik = useFormik({
        initialValues: {
            path: "",
        },
        onSubmit: async (values) => {
            if (values?.path === '' || values?.path?.trim() === '') {
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
                    if (key !== "image") {
                        formData.append(key, values[key]);
                    } else {
                        formData.append("image", values["image"]);
                    }
                }
                dispatch(addAdvertisementAction(formData));
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
            formik.setFieldValue("image", file);
        }
    };

    // const handleChangeRole = (value) => {
    //     formik.setFieldValue("role", value);
    // };

    return (
        <div>
            <h3>Add Advertisement</h3>
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
                    label="Path"
                    name="path"
                    style={{ minWidth: '100%' }}
                    rules={[
                        {
                            required: true,
                            message: 'Path is required!',
                            transform: (value) => value?.trim(),
                        },
                    ]}
                >
                    <Input name="path" onChange={formik.handleChange} />
                </Form.Item>

                <Form.Item label="Image">
                    <input
                        name="image"
                        type="file"
                        onChange={handleChangeFile}
                        accept="image/png, image/jpeg,image/gif,image/png"
                    />
                    <br />
                    {imgSrc ? (
                        <img style={{ width: 200, height: 200, objectFit: "cover", borderRadius: "50%", }} src={imgSrc} alt="..." />
                    ) : (
                        <img style={{ width: 200, height: 200, border: "0.1px solid #ccc", borderRadius: "50%", }} src="/img/placeholder-image.jpg" alt="..." />
                    )}
                </Form.Item>


                <Form.Item label="Action">
                    <Button htmlType="submit" className="btn-primary bg-primary" type="primary" > Add Advertisement </Button>
                </Form.Item>
            </Form>
        </div>

    );
};


export default AddAdvertisement;
