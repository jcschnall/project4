import React, { Component } from 'react'
import {Redirect} from "react-router-dom";

class NewUserForm extends Component {

    state = {
        user: {},
        redirectToUsersPage: false
    }

    handleSubmit = (event) => {
        event.preventDefault();

        this.props.createUser(this.state.user)

        this.setState({redirectToUsersPage: true})
    }

    handleChange = (event) => {
        const attributeToChange = event.target.name
        const newValue = event.target.value

        const updatedUser = { ...this.state.user }
        updatedUser[attributeToChange] = newValue
        this.setState({ user: updatedUser })
    }

    render() {

        if(this.state.redirectToUsersPage) {
            return <Redirect to="/list" />
        }


        return (
            <center>
            <div>
                <h2>Login Info</h2>
                <form onSubmit={this.handleSubmit} id="new-user-form">
                    <div>
                        <label htmlFor="userName">Email </label>
                        <input
                            id="new-user-user-name"
                            type="text"
                            name="userName"
                            onChange={this.handleChange} />
                                           <br>
                                           </br>
                    </div>
                     <br>
                                                               </br>

                    <div>
                        <label htmlFor="firstName">First Name </label>
                        <input
                            id="new-user-first-name"
                            type="text"
                            name="firstName"
                            onChange={this.handleChange} />
                    </div>
                     <br>
                                                               </br>

                    <div>
                        <label htmlFor="lastName">Last Name </label>
                        <input
                            id="new-user-last-name"
                            type="text"
                            name="lastName"
                            onChange={this.handleChange} />
                    </div>
                     <br>
                                                               </br>

                    <div>
                        <input
                            id="new-user-submit"
                            type="submit"
                            value="LogIn" />
                    </div>
                </form>
            </div>
            </center>
        )
    }

}

export default NewUserForm