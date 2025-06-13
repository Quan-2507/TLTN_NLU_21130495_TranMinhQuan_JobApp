import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { Button, Checkbox, Form, Input, notification, Select } from 'antd';
import { useFormik } from 'formik';
import { CKEditor } from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";

import './company.css'
import { registerCompanyAction } from '../../../redux/actions/CompanyAction';
import { getCityProvinceListAction } from '../../../redux/actions/CityProvinceAction';

const { Option } = Select;

export default function RegisterCompany() {
    const dispatch = useDispatch();

    let { arrCityProvince } = useSelector(state => state.CityProvinceReducer);
    const [checked, setChecked] = useState(false);
    const [logoSrc, setLogoSrc] = useState("");
    const [backgroundSrc, setBackgroundSrc] = useState("");



    useEffect(() => {
        dispatch(getCityProvinceListAction());
    }, []);

    const formik = useFormik({
        initialValues: {
            email: '',
            password: '',
            name_company: '',
            introduction: '',
            benefit: '',
            profession: '',
            size: '',
            link_website: '',
            nationnality: '',
            location: '',
            logo: "",
            background: "",
        },
        onSubmit: async (values) => {
            if (
                values.email === "" || values?.email?.startsWith(' ') === true ||
                values.password === "" || values?.password?.startsWith(' ') === true ||
                values.name_company === "" || values?.name_company?.startsWith(' ') === true ||
                values.nationnality === "" || values?.nationnality?.startsWith(' ') === true ||
                values.link_website === "" || values?.link_website?.startsWith(' ') === true ||
                values.location === "" || values?.location?.startsWith(' ') === true ||
                values.profession === "" || values?.profession?.startsWith(' ') === true
            ) {
                notification.error({
                    closeIcon: true,
                    message: "Error",
                    description: <>Please fill in all required fields. No leading spaces! </>,
                });
            } else {
                let formData = new FormData();
                for (let key in values) {
                    formData.append(key, values[key]);
                }
                console.table("formData", [...formData]);
                dispatch(registerCompanyAction(formData));
            }
        }
    })

    const handleChangeCityProvince = (value) => {
        formik.setFieldValue("city_province_id", value);
    };

    const handleChangeFileLogo = (e) => {
        let file = e.target.files[0];
        if (file.type === 'image/jpeg' || file.type === 'image/jpg' || file.type === 'image/png') {
            let reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = (e) => {
                setLogoSrc(e.target.result); //Hình base 64
            };
            formik.setFieldValue("logo", file);
        }
    };

    const handleChangeFileBackground = (e) => {
        let file = e.target.files[0];
        if (file.type === 'image/jpeg' || file.type === 'image/jpg' || file.type === 'image/png') {
            let reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = (e) => {
                setBackgroundSrc(e.target.result); //Hình base 64
            };
            formik.setFieldValue("background", file);
        }
    };

    const handleChangeSize = (value) => {
        formik.setFieldValue("size", value);
    };

    const handleChangeInput = (e, editor, name) => {
        const data = editor.getData();
        formik.setFieldValue(name, data);
    };

    const onChange = (e) => {
        setChecked(e.target.checked);
    };
    return (
        // py-8 px-8  rounded-2xl mt-5 
        <div className="shadow-xl z-10 flex" >
            <div
                className='w-[500px]  py-8 px-8 rounded-l-lg flex flex-col items-center bg-gray-400/60 justify-around' style={{
                    backgroundImage: 'url(./img/signUpImage.gif)',
                    backgroundSize: 'contain',
                    backgroundRepeat: 'no-repeat',
                    backgroundPosition: 'center',
                }}
            >

            </div>
            <div className='py-8 px-8  bg-gray-50/80 rounded-r-lg'>
                <h1 className="text-2xl font-bold text-black text-center mb-4">Sign Up Company</h1>
                <Form
                    onSubmitCapture={formik.handleSubmit}
                    className="myScrollBar"
                    id="myScrollBar1"
                >
                    {/* style={{ display: "flex", justifyContent: "space-between" }} */}
                    <div className=''>
                        <div
                            name="basic"
                            className='d-flex flex-col'
                            labelCol={{
                                span: 6,
                            }}
                            wrapperCol={{
                                span: 24,
                            }}
                            style={{
                                maxWidth: 600,
                                width: 600,
                                marginRight: 10
                            }}
                            initialValues={{
                                remember: false,
                            }}


                            autoComplete="off"
                        >


                            <div>
                                <h2>Email : </h2>
                                <Form.Item
                                    name="email"
                                    label=""
                                    rules={[
                                        {
                                            type: 'email',
                                            message: 'E-mail is invalid!',
                                        },
                                        {
                                            required: true,
                                            message: 'E-mail is required!',
                                            transform: (value) => value.trim(),
                                        },
                                    ]}
                                >
                                    <Input name='email' onChange={formik.handleChange} className="d-flex block text-sm py-2.5 px-4 mt-2 rounded-lg w-full border outline-none" placeholder="Email" />
                                </Form.Item>
                            </div>


                            <div>
                                <h2>Password : </h2>
                                <Form.Item
                                    label=""
                                    name="password"
                                    rules={[
                                        {
                                            required: true,
                                            message: 'Password is required!',
                                            transform: (value) => value.trim(),
                                        },
                                    ]}
                                >
                                    <Input.Password className="d-flex block text-sm py-2.5 px-4 mt-2 rounded-lg w-full border outline-none" placeholder="Password" />
                                </Form.Item>
                            </div>

                            <div>
                                <h2>Password Confirm : </h2>
                                <Form.Item
                                    name="password_confirmation"
                                    label=""
                                    dependencies={['password']}
                                    hasFeedback
                                    rules={[
                                        {
                                            required: true,
                                            message: 'Please re-enter your password!',
                                            transform: (value) => value.trim(),
                                        },
                                        ({ getFieldValue }) => ({
                                            validator(_, value) {
                                                if (!value || getFieldValue('password') === value) {
                                                    return Promise.resolve();
                                                }
                                                return Promise.reject(new Error('Password do not match, please try again!'));
                                            },
                                        }),
                                    ]}
                                >
                                    <Input.Password name="password" onChange={formik.handleChange} className="d-flex block text-sm py-2.5 px-4 mt-2 rounded-lg w-full border outline-none" placeholder="Password confirm" />
                                </Form.Item>
                            </div>

                            <div>
                                <h2>Name Company : </h2>
                                <Form.Item
                                    name="name_company"
                                    rules={[
                                        {
                                            required: true,
                                            message: 'Name Company is required!',
                                            transform: (value) => value.trim(),
                                        },
                                    ]}
                                >
                                    <Input name="name_company" onChange={formik.handleChange} className="d-flex block text-sm py-2.5 px-4 mt-2 rounded-lg w-full border outline-none" placeholder="Name Company" />

                                </Form.Item>
                            </div>

                            <div className=''>
                                <h2>Introduction : </h2>
                                <Form.Item
                                >
                                    <CKEditor
                                        className="rounded-lg overflow-hidden"
                                        data={formik.values.introduction}
                                        name="introduction"
                                        editor={ClassicEditor}
                                        onChange={(event, editor) => {
                                            handleChangeInput(event, editor, 'introduction')
                                        }}
                                        onReady={(editor) => {
                                            editor.editing.view.change((writer) => {
                                                writer.setStyle(
                                                    "height",
                                                    "200px",
                                                    editor.editing.view.document.getRoot()
                                                );
                                            });
                                        }}
                                    ></CKEditor>
                                </Form.Item>
                            </div>

                            <div className=''>
                                <h2>Benefit : </h2>
                                <Form.Item
                                >
                                    <CKEditor
                                        className="rounded-lg  overflow-hidden"
                                        name="benefit"
                                        data={formik.values.benefit}
                                        editor={ClassicEditor}
                                        onChange={(event, editor) => {
                                            handleChangeInput(event, editor, 'benefit')
                                        }}
                                        onReady={(editor) => {
                                            editor.editing.view.change((writer) => {
                                                writer.setStyle(
                                                    "height",
                                                    "200px",
                                                    editor.editing.view.document.getRoot()
                                                );
                                            });
                                        }}
                                    ></CKEditor>
                                    {/* <Input name="benefit" onChange={formik.handleChange} value={formik.values.benefit} /> */}
                                </Form.Item>
                            </div>

                            <div>
                                <h2>Profession : </h2>
                                <Form.Item
                                    name="Profession"
                                    rules={[
                                        {
                                            required: true,
                                            message: 'profession is required!',
                                            transform: (value) => value.trim(),
                                        },
                                    ]}
                                >
                                    <Input name="profession" onChange={formik.handleChange} className="d-flex block text-sm py-2.5 px-4 mt-2 rounded-lg w-full border outline-none" placeholder="Profession" />
                                </Form.Item>
                            </div>
                            <div>
                                <h2>Size : </h2>
                                <Form.Item
                                    style={{ minWidth: '100%' }}
                                    rules={[
                                        {
                                            required: true,
                                            message: 'Size  is required!',
                                            transform: (value) => value.trim(),
                                        },
                                    ]}
                                >
                                    <Select name="size" onChange={handleChangeSize} placeholder="Choose Szie" value={formik.values.size}>
                                        <Option value={"1 - 100"}>1 - 100</Option>
                                        <Option value={'100 - 500'}>100 - 500</Option>
                                        <Option value={'500 - 1000'}>500 - 1000</Option>
                                        <Option value={'1000 - 5000'}>1000 - 5000</Option>
                                        <Option value={'5000 - 9000'}>5000 - 9000</Option>

                                    </Select>
                                </Form.Item>
                            </div>

                            <div>
                                <h2>Link Website : </h2>
                                <Form.Item
                                    name="Link website"
                                    rules={[
                                        {
                                            required: true,
                                            message: 'link_website is required!',
                                            transform: (value) => value.trim(),
                                        },
                                    ]}
                                >
                                    <Input name="link_website" onChange={formik.handleChange} className="d-flex block text-sm py-2.5 px-4 mt-2 rounded-lg w-full border outline-none" placeholder="Link website" />
                                </Form.Item>
                            </div>

                            <div>
                                <h2>National : </h2>
                                <Form.Item
                                    name="nationnality"
                                    rules={[
                                        {
                                            required: true,
                                            message: 'National is required!',
                                            transform: (value) => value.trim(),
                                        },
                                    ]}
                                >
                                    <Input name="nationnality" onChange={formik.handleChange} className="d-flex block text-sm py-2.5 px-4 mt-2 rounded-lg w-full border outline-none" placeholder="National" />
                                </Form.Item>
                            </div>

                            <div>
                                <h2>City Province : </h2>
                                <Form.Item
                                    label=""
                                    name="city_province_id"
                                    style={{ minWidth: "100%" }}
                                    rules={[
                                        {
                                            required: true,
                                            message: "City Province is required!",
                                            transform: (value) => value.trim(),
                                        },
                                    ]}
                                >
                                    <Select
                                        rules={[{ required: true }]}
                                        options={
                                            arrCityProvince
                                                ? arrCityProvince?.data?.map((item, index) => ({
                                                    key: index,
                                                    label: item.name,
                                                    value: item.id,
                                                }))
                                                : ""
                                        }
                                        onChange={handleChangeCityProvince}
                                    />
                                </Form.Item>
                            </div>

                            <div>
                                <h2>Location : </h2>
                                <Form.Item
                                    name="location"
                                    rules={[
                                        {
                                            required: true,
                                            message: 'Location is required!',
                                            transform: (value) => value.trim(),
                                        },
                                    ]}
                                >
                                    <Input name="location" onChange={formik.handleChange} className="d-flex block text-sm py-2.5 px-4 mt-2 rounded-lg w-full border outline-none" placeholder="Location" />
                                </Form.Item>
                            </div>

                            <div>
                                <div>
                                    <h2>Logo Company : </h2>
                                    <Form.Item>
                                        <input
                                            name="UploadFileLogo"
                                            type="file"
                                            onChange={handleChangeFileLogo}
                                            accept="image/png, image/jpeg,image/gif,image/png"
                                        />
                                        <br />
                                        {logoSrc ? (
                                            <img style={{ width: 300, height: 200, objectFit: "cover", borderRadius: "10%", }} src={logoSrc} alt="..." />
                                        ) : (
                                            <img style={{ width: 300, height: 200, border: "0.1px solid #ccc", borderRadius: "10%", }} src="/img/placeholder-image.jpg" alt="..." />
                                        )}
                                    </Form.Item>
                                </div>

                                <div>
                                    <h2>Background Company : </h2>
                                    <Form.Item>
                                        <input
                                            name="UploadFileBackground"
                                            type="file"
                                            onChange={handleChangeFileBackground}
                                            accept="image/png, image/jpeg,image/gif,image/png"
                                        />
                                        <br />
                                        {backgroundSrc ? (
                                            <img style={{ width: 300, height: 200, objectFit: "cover", borderRadius: "10%", }} src={backgroundSrc} alt="..." />
                                        ) : (
                                            <img style={{ width: 300, height: 200, border: "0.1px solid #ccc", borderRadius: "10%", }} src="/img/placeholder-image.jpg" alt="..." />
                                        )}
                                    </Form.Item>
                                </div>
                            </div>



                            <Checkbox checked={checked} onChange={onChange}>
                                Please ensure your information is accurate!!
                            </Checkbox>

                            <div className="text-center">
                                {checked ? <Button htmlType='submit' type='primary' >Sign Up</Button> : <Button htmlType='submit' disabled type='primary' >Sign Up</Button>}
                                <div className="mt-2 text-sm">Already registered? <a href='/' className="underline  cursor-pointer"> Sign In</a></div>
                            </div>
                        </div>

                    </div>
                </Form>
            </div>


        </div>
    )
}




