import React, { Component } from "react";
import model from "../model/model";

import CreateQuestion from "./CreateQuestion";
import createQuestionPresenter from "../presenter/createQuestionPresenter";

const mapModelStateToComponentState = modelState => ({
    title: modelState.newQuestion.title,
    body: modelState.newQuestion.body,
    tags: modelState.newQuestion.tags
});

export default class SmartCreateQuestion extends Component {
    constructor() {
        super();
        this.state = mapModelStateToComponentState(model.state);
        this.listener = modelState => this.setState(mapModelStateToComponentState(modelState));
        model.addListener("change", this.listener);
    }

    componentWillUnmount() {
        model.removeListener("change", this.listener);
    }

    render() {
        return (
            <CreateQuestion 
                onCreate={createQuestionPresenter.onCreateQuestion}
                onChange={createQuestionPresenter.onQuestionChange}
                title={this.state.title}
                body={this.state.body}
                tags={this.state.tags} />
        );
    }
}