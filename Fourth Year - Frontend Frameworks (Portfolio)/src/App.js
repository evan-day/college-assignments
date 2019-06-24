import React, { Component } from 'react';
import MainSection from './components/Main'
import ReactGA from 'react-ga';
import './App.css';

function initializeReactGA() {
    ReactGA.initialize('UA-74965331-12');
    ReactGA.pageview('/homepage');
}
class App extends Component {
  render() {
    return (
        <div>
            <MainSection/>
        </div>
    );
  }
}

export default App;
