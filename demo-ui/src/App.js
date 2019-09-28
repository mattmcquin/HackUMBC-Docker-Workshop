import React from 'react';
import './App.css';
import ReactDOM from "react-dom";
import ReactWordcloud from "react-wordcloud";
import _ from 'lodash';

import words from "./words";

const wordcloudOptions = {
  fontSizes: [20, 70],
  fontFamily: "impact",
  fontStyle: "normal",
  fontWeight: "normal",
  padding: 1,
  rotations: 3,
  rotationAngles: [0, 90],
  scale: "sqrt",
  spiral: "archimedean"
}

class App extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      words: []
    }

    this.fetchWords = () => fetch('http://172.17.0.2:8080/' + document.getElementById('generate_wordcloud_box').value)
        .then(res => res.json())
        .then((data) => _.map(data, (value, key) => ({"text": key, "value": value})))
        .then((data) => {
          console.log(data);
          return data;
        })
        .then((data) => {
          this.setState({ words: data })
        })
  }

  render() {
    return (
    <div className="App">
      <div style={{ height: 800, width: 800 }}>

        {/** Use ONLY IF Twitter API access exists: */}

        <input id="generate_wordcloud_box"/>
        <button className="Button" type="button" onClick={this.fetchWords}>Generate Wordcloud</button>
        <ReactWordcloud words={this.state.words} options={wordcloudOptions} />


        {/* Use if NO Twitter API access exists:
        <ReactWordcloud words={words} options={wordcloudOptions} />
        */}

      </div>
    </div>
    );
  }
}

export default App;
