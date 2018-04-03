import React from 'react'

const User = (props) => {
    return ( <center>
        <div id={`user-${props.user.id}`} data-user-display>
            <br></br>
            <div id={`user-${props.user.id}-user-name`}>
                EMAIL:  &nbsp;&nbsp;      {props.user.userName}
            </div>
            <br></br>
            <div id={`user-${props.user.id}-first-name`}>
                FIRST NAME: &nbsp;&nbsp;  {props.user.firstName}
            </div>
             <br></br>
            <div id={`user-${props.user.id}-last-name`}>
                LAST NAME: &nbsp;&nbsp;  {props.user.lastName}
            </div>
            <br></br>
            <button
                id={`delete-user-${props.user.id}`}
                onClick={() => {props.deleteUser(props.user.id, props.index)}}>

                Remove User
            </button>
        </div>
        </center>
    )
}

export default User