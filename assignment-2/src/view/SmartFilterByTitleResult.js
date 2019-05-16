import React, { Component } from "react";
import model from "../model/model";
import FilterResult from "./FilterResult";
import questionsListPresenter from "../presenter/questionsListPresenter";


const mapModelStateToComponentState = modelState => ({
    questions: modelState.questions,
    filterResult: modelState.filterResult
});

export default class SmartFilterByTitleResult extends Component {
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
            <FilterResult 
                onViewDetails={questionsListPresenter.onViewDetails}
                questions={this.state.filterResult} />
        );
    }

    
} 