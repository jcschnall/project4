import React, { Component } from 'react'
import User from './User'
import {Link} from "react-router-dom";
import axios from 'axios';

const UsersList = (props) => {

    return (
        <div id="users-wrapper">
            <h1>List Of Users</h1>

            <Link to="/logIn" id="new-user-link">Log In</Link>
            {
                props.users.map((user, index) => {
                    return (
                        <User
                            deleteUser={props.deleteUser}
                            user={user}
                            key={index}
                            index={index}
                            />
                    )
                })
            }
        </div>
    )
}

export default UsersList