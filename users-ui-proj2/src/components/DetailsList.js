import React, { Component } from 'react'
import axios from 'axios'


class DetailsList extends Component {

    state = {
        ideas: []
    }

    async componentWillMount() {
        try {
            var CatId = window.location.pathname[(window.location.pathname).length-1]
            const response = await axios.get(`/cats/${CatId}`)
            this.setState( {ideas: response.data} )
        } catch (error) {
            console.log('Error retrieving ideas!')
            console.log(error)
        }
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
        this.createIdea(this.state.newIdea)
    }

    createIdea = async (idea, index) => {
        try {
            const newIdeaResponse = await axios.post(`/cats`, idea)

            const updatedIdeasList = [...this.state.ideas]
            updatedIdeasList.push(newIdeaResponse.data)
            this.setState({ideas: updatedIdeasList})

        } catch(error) {
            console.log('Error creating new User!')
            console.log(error)
        }
    }



    render() {
        return (
            <div>
                <center>
                <h1>Cat</h1>
                    <input
                        name="Cat Breed"
                        value={this.state.ideas.title} />
                          <div>
                          <p><br></br></p>
                          </div>
                              <img src={this.state.ideas.image} alt="cat" height="420" width="420"/>
                          <div>
                            </div>
                                <h3> {this.state.ideas.description} </h3>
                            <div>
                           <p><br></br></p>

                          <form onSubmit={this.handleSubmit}>
                              <div>
                                  <label htmlFor="description">description</label>
                                  <input
                                      name="description"
                                      type="text"
                                      onChange={this.handleChange} />
                              </div>
                              <div>
                                  <input type="submit" value="Add"/>
                              </div>
                          </form>
                          </div>


                </center>
            </div>
        )
    }
}

export default DetailsList