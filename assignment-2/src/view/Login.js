import React from "react";
import model from "../model/model";

 const  Login = ({ onLogin }) => (
    <div className="container">
        <h2>Welcome to Stack Overflow</h2>
        <br/>
        <form>
            <div className="form-group" onChange={ e => model.setUser(e.target.value)}>
                <label>Username: </label>
                <input/>
            </div>
            <div className="form-group">
                <label>Password: </label>
                <input/>
            </div>
            <button className="btn btn-primary" onClick={onLogin}>Login</button>
        </form>
    </div>
);

export default Login;