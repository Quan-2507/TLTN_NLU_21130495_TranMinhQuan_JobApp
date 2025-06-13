import React, { Component, Fragment, useEffect } from 'react'
import { Route } from "react-router-dom";

export default function UserTemplate(props) {


  const { Component, ...restProps } = props;

  useEffect(() => {
    window.scrollTo(0, 0);
  })

  return <Route {...restProps} render={(propsRoute) => { //props.location, props.history, props.match
    return <Fragment>
      <div className="min-h-screen bg-purple-400 flex justify-center items-center" style={{
        backgroundImage: 'url(./img/colorful-abstract-textured-background-design.jpg)',
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'center',
         filter: 'blur(0px)',
         position:"absolute",
         height:"100vh",
         width:"100%"
      }}>
      </div>
      {/* <div className="min-h-screen bg-black flex justify-center items-center" style={{
        // backgroundImage: 'url(./img/backgroundImage.jpg)',
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'center',
         filter: 'blur(0px)',
         position:"absolute",
         opacity:0,
         height:"100vh",
         width:"100%"
      }}>
      </div> */}
      <div className="min-h-screen flex justify-center items-center">
      <Component {...propsRoute} />
      </div>
    </Fragment>
  }} />

}
