import React, { Component } from 'react'

   class IdeaNewForm extends Component {

       state = {
           newIdea: {}
       }

       handleChange = (event) => {
           const attributeToChange = event.target.name
           const newValue = event.target.value

           const updatedNewIdea = { ...this.state.newIdea }
           updatedNewIdea[attributeToChange] = newValue
           this.setState({ newIdea: updatedNewIdea })
       }

       handleSubmit = (event) => {
           event.preventDefault()

           this.props.createIdea(this.state.newIdea)
       }

       render() {
           return (
               <div>
                   <h2>Add Cat Photo</h2>

                   <form onSubmit={this.handleSubmit}>
                       <div>
                           <label htmlFor="title">Breed</label>
                           <input
                               name="title"
                               type="text"
                               onChange={this.handleChange} />
                       </div>
                       <p><br></br></p>

                       <div>
                           <label htmlFor="description">URL</label>
                           <input
                               name="image"
                               type="text"
                               onChange={this.handleChange} />
                       </div>
                       <p><br></br></p>

                       <div>
                           <input type="submit" value="Add"/>
                       </div>
                   </form>

                   <hr />
                   <hr />
               </div>
           )
       }

   }

   export default IdeaNewForm