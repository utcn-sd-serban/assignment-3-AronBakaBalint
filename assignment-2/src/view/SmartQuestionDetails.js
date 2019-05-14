import React, { Component } from "react";
import model from "../model/model";

import QuestionDetails from "./QuestionDetails";
import createAnswerPresenter from "../presenter/createAnswerPresenter";
import questionsListPresenter from "../presenter/questionsListPresenter";

const mapModelStateToComponentState = (modelState, props) => (
    modelState.questions[props.match.params.index]
)

export default class SmartQuestionDetails extends Component {
    constructor(props) {
        super(props);
        this.state = mapModelStateToComponentState(model.state, props);
        this.listener = modelState => this.setState(mapModelStateToComponentState(modelState, this.props));
        model.addListener("change", this.listener);
    }

    componentDidUpdate(prev) {
        if (prev.match.params.index !== this.props.match.params.index) {
            this.setState(mapModelStateToComponentState(model.state, this.props));
        }
    }

    componentWillUnmount() {
        model.removeListener("change", this.listener);
    }

    render() {
        return (
            <QuestionDetails
                id={this.state.id}
                title={this.state.title}
                body={this.state.body}
                tags={this.state.tags}
                author={this.state.author}
                postDate={this.state.postDate}
                answers={model.getAnswersByQuestionId(this.state.id)}
                onCreateAnswer={createAnswerPresenter.onCreateAnswer}
                onAnswerChange={createAnswerPresenter.onAnswerChange}
                onEditAnswer={questionsListPresenter.onEditAnswer}
            />
        );
    }
}