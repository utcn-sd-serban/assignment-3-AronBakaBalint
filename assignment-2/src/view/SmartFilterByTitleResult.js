import React, { Component } from "react";
import model from "../model/model";
import FilterResult from "./FilterResult";
import questionsListPresenter from "../presenter/questionsListPresenter";

const mapModelStateToComponentState = modelState => ({
    questions: modelState.questions
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

    filterByTitle(searchArray, text){
        var filterResult = new Array(0);
        var k =0;
        for(var i=0;i < searchArray.length;i++){
            if(searchArray[i].title === text){
                filterResult[k++]=(searchArray[i]);
            }
        }

        return filterResult;
    }

    render() {
        return (
            <FilterResult 
                onViewDetails={questionsListPresenter.onViewDetails}
                questions={this.filterByTitle(this.state.questions, model.getSearchWord())} />
        );
    }

    
}