import React, { useState } from "react";
import { Form, Input, Button, notification } from "antd";
import { useDispatch, useSelector } from "react-redux";
import { useFormik } from "formik";
import { useEffect } from "react";

import { updateAdvertisementIdAction, getAdvertisementByIdAction } from "../../../redux/actions/AdvertisementAction";

const AdvertisementEdit = (props) => {
    const dispatch = useDispatch();
    const [imgSrc, setImgSrc] = useState("");
    const { advertisementDetail } = useSelector((state) => state.AdvertisementReducer);
    let { id } = props.match.params;
    useEffect(() => {
        dispatch(getAdvertisementByIdAction(id));
    }, [dispatch, id]);

    const formik = useFormik({
        enableReinitialize: true,
        initialValues: {
            path: advertisementDetail?.path,
            // image: advertisementDetail?.image,

        },
        onSubmit: async (values) => {
            if (values?.path === "" || values?.path?.trim() === "") {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>
                            Please fill in all required fields.
                        </>
                    ),
                });
            }
            else {
                let formData = new FormData();
                for (let key in values) {
                    if (key !== "image") {
                        formData.append(key, values[key]);
                    } else {
                        formData.append("image", values["image"]);
                    }
                }
                dispatch(updateAdvertisementIdAction(id, formData));
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

    return (
        <div>
            <h3 className="mb-5">Update AddAdvertisement :   {advertisementDetail?.path}</h3>
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
                    style={{ minWidth: '100%' }}
                    rules={[
                        {
                            required: true,
                            message: 'Path is required!',
                            transform: (value) => value.trim(),
                        },
                    ]}
                >
                    <Input name="path" onChange={formik.handleChange} value={formik.values.path} />
                </Form.Item>

                <Form.Item label="Image">
                    <input
                        name="image"
                        type="file"
                        onChange={handleChangeFile}
                        accept="image/png, image/jpeg,image/gif,image/png"
                    />
                    <br />
                    <img style={{ width: 200, height: 200, objectFit: 'cover', borderRadius: '10%', border: "0.1px solid #ccc" }}
                        src={imgSrc === '' ? `${advertisementDetail?.image}` : imgSrc} alt="..." />
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

export default AdvertisementEdit;
