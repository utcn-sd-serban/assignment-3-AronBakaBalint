import React, { Component } from "react";
import Login from  "../view/Login";
import loginPresenter from "../presenter/LoginPresenter";

export default class SmartLogin extends Component {
    
    render() {
        return (
            <Login 
                onLogin={loginPresenter.onLogin}
                />
        );
    }
}