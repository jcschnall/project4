import React, { Component } from 'react'

class Idea extends Component {

    render() {
        return (
            <div>
                <div>
                    <h2>Cat Breed</h2>
                    <input
                        name="Cat Breed"
                        onChange={(event) => this.props.handleIdeaChange(event, this.props.index)}
                        onBlur={() => this.props.updateIdea(this.props.index)}
                        value={this.props.title} />
                </div>
                <p><br></br></p>
                <p><br></br></p>
                <div>
                    {/* <textarea
                        name="Image URL"
                        onChange={(event) => this.props.handleIdeaChange(event, this.props.index)}
                        onBlur={() => this.props.updateIdea(this.props.index)}
                        value={this.props.image} />
                    */}

                </div>
                <p><br></br></p>
                <p><br></br></p>
                <div>
                    <a href={`cats/${this.props.id}`}><img src={this.props.image} alt="cat" height="420" width="420" /></a>
                </div>
                 <p><br></br></p>
                 <p><br></br></p>
                <div>
                    <button
                        onClick={() => this.props.deleteIdea(this.props.id, this.props.index)}>
                        Delete
                    </button>
                </div>
                <hr />
            </div>
        )
    }

}

export default Idea
