import React, { Component } from 'react'
import axios from 'axios'

import Idea from './Idea'
import IdeaNewForm from './IdeaNewForm'

class IdeaList extends Component {

    state = {
        ideas: []
    }

    async componentWillMount() {
        try {
            const response = await axios.get('')
            this.setState({ ideas: response.data })
        } catch (error) {
            console.log('Error retrieving ideas!')
            console.log(error)
        }
    }

    deleteIdea = async (ideaId, index) => {
        try {
            await axios.delete(`/cats/${ideaId}`)
            
            const updatedIdeasList = [...this.state.ideas]
            updatedIdeasList.splice(index, 1)
            this.setState({ideas: updatedIdeasList})

        } catch (error) {
            console.log(`Error deleting Idea with ID of ${ideaId}`)
            console.log(error)
        }
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

    handleIdeaChange = (event, index) => {
        const attributeToChange = event.target.name
        const newValue = event.target.value

        const updatedIdeasList = [...this.state.ideas]
        const ideaToUpdate = updatedIdeasList[index]
        ideaToUpdate[attributeToChange] = newValue
        
        this.setState({ideas: updatedIdeasList})
    }

    updateIdea = async (index) => {
        try {
            const ideaToUpdate = this.state.ideas[index]
            await axios.patch(`/ideas/${ideaToUpdate.id}`, ideaToUpdate)
        } catch(error) {
            console.log('Error updating idea!')
            console.log(error)
        }
    }

    render() {
        return (
            <div>
            {console.log('debug')}
            {console.log(this.state.ideas)}
                <center>
                <h1>Cats Submitted</h1>
                <IdeaNewForm createIdea={this.createIdea}/>

                {
                    this.state.ideas.map((idea, index) => {
                        return (
                            <Idea
                                {...idea}
                                key={index}
                                index={index}
                                deleteIdea={this.deleteIdea} 
                                handleIdeaChange={this.handleIdeaChange} 
                                updateIdea={this.updateIdea} />
                        )
                    })
                }
                </center>
            </div>
        )
    }
}

export default IdeaList
