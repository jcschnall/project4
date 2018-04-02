import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios'
import IdeaList from './components/IdeaList'
import DetailsList from './components/DetailsList'

class App extends Component {


  render() {
    if(window.location.pathname == '/' ){
    return (
      <div>
        <IdeaList />
      </div>
    )}
    else{
    return(
      <div>
        <DetailsList />
      </div>
    )}
  }
}

export default App

