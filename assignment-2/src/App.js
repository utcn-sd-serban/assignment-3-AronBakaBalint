import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import { HashRouter, Switch, Route } from "react-router-dom";
import SmartEditAnswer from './view/SmartEditAnswer';
import SmartQuestionsList from './view/SmartQuestionsList';
import SmartCreateQuestion from './view/SmartCreateQuestion';
import SmartQuestionDetails from './view/SmartQuestionDetails';
import SmartFilterByTagResult from './view/SmartFilterByTagResult';
import SmartFilterByTitleResult from './view/SmartFilterByTitleResult';
import SmartLogin from './view/SmartLogin';

const App = () => (
  <div className="App">
    <HashRouter>
      <Switch>
        <Route exact={true} component={SmartLogin} path="/" />
        <Route exact={true} component={SmartQuestionsList} path="/question-list" />
        <Route exact={true} component={SmartCreateQuestion} path="/create-question" />
        <Route exact={true} component={SmartEditAnswer} path="/edit-answer" />
        <Route exact={true} component={SmartQuestionDetails} path="/question-details/:index" />
        <Route exact={true} component={SmartFilterByTagResult} path="/tag-filter-result" />
        <Route exact={true} component={SmartFilterByTitleResult} path="/title-filter-result" />
      </Switch>
    </HashRouter>
  </div>
);


export default App;