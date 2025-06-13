import React, { useEffect } from 'react';
import { Avatar, Button, Typography, } from 'antd';
import { useDispatch, useSelector } from 'react-redux';
import { DOMAIN, IMAGE, TOKEN, USER_LOGIN } from '../../util/settings/config';


const Profile = () => {
  const dispatch = useDispatch();
  let { userLogin } = useSelector(state => state.UserReducer);

  return (
    <div >
      {userLogin?.role === 'EMPLOYER' ? <h3 className='mb-5'>Employer Information:</h3> : <h3 className='mb-5'>User Information:</h3>}
      <div className='row mx-10'>
        <div className='col-4'>
          {userLogin?.image == null || userLogin?.image == ""
            ? <Avatar size={200} style={{ fontSize: '80px', display: 'flex', justifyContent: 'center', alignItems: 'center' }} icon={userLogin?.email?.substr(0, 1)} />
            : <div style={{ minWidth: '40px', minHeight: 40, width: 200, height: 200, backgroundSize: 'cover', borderRadius: '50%', backgroundImage: `url(${IMAGE + "/image/upload/" + userLogin.image})` }} />
          }
        </div>
        <div className='col-12'>
          <div className='col-6'>
            <Typography>
              <pre>Account: {userLogin?.email}</pre>
            </Typography>
          </div>
          <div className='col-6'>
            <Typography>
              <pre>Full Name: {userLogin?.name}</pre>
            </Typography>
            <Typography>
              <pre>Role: {userLogin?.role}</pre>
            </Typography>
          </div>
          <div>

          </div>
          <div className='col-6'>
            <Button href={`/admin/empmng/edit/${userLogin?.id}`} className='btn-primary bg-primary mt-3 px-5' type='primary' onClick={() => {

            }}>Update Information</Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;