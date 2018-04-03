import React, {Component} from 'react'
import axios from 'axios'
import UsersList from './components/UsersList'
import NewUserForm from './components/NewUserForm'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';


class App extends Component {

    state = {
        users: []
    }


    async componentWillMount() {
        const response = await axios.get("/users")
        this.setState({
            users: response.data,
            response
        })

    }

    createUser = async (newUser) => {
        try {
            const response = await axios.post('/users', newUser)

            const newUserDB = response.data
            const updatedUsersList = [...this.state.users]
            updatedUsersList.push(newUserDB)

            this.setState({users: updatedUsersList})

        } catch (error) {
            console.log("Error creating new User")
        }
    }


    deleteUser = async (userId, index) => {
        try {
            await axios.delete(`/users/${userId}`)

            const updatedUsersList = [...this.state.users]
            updatedUsersList.splice(index, 1)

            this.setState({users: updatedUsersList})

        } catch (error) {
            console.log(`Error deleting User with ID: ${userId}`)
        }
    }

    render() {

        const NewUserFormComponent = () => (
            <NewUserForm createUser={this.createUser}/>
        )

        const UsersListComponent = () => (
            <UsersList
                users={this.state.users}
                deleteUser={this.deleteUser}/>
        )


        /*
        const UserByEmail = () => (

            function onClickReturnUserID(){
                return this.props.user.id
            }

            id = user.props.user.id
            <UsersList
                users={this.state.users[id]}
                deleteUser={this.deleteUser}/>
        )
         <Route exact path="/byEmail" render={UserByEmail}/>

         Link to="/byEmail"
        */




        return (
            <Router>
                <Switch>
                    <Route exact path="/list" render={UsersListComponent}/>
                    <Route exact path="/logIn" render={NewUserFormComponent}/>
                </Switch>
            </Router>
        )
    }
}

export default App;
