import React, { Component } from "react";
import model from "../model/model";
import FilterResult from "./FilterResult";
import questionsListPresenter from "../presenter/questionsListPresenter";

const mapModelStateToComponentState = modelState => ({
    questions: modelState.questions
});

export default class SmartFilterByTagResult extends Component {
    constructor() {
        super();
        this.state = mapModelStateToComponentState(model.state);
        this.listener = modelState => this.setState(mapModelStateToComponentState(modelState));
        model.addListener("change", this.listener);
    }

    componentWillUnmount() {
        model.removeListener("change", this.listener);
    }

    filterByTag(questions, text){
        var filterResult = new Array(0);
        var k=0;
        for(var i=0;i < questions.length;i++){
            if(questions[i].tags.includes(text)){
                filterResult[k++]=(questions[i]);
            }
        }

        return filterResult;
    }

    render() {
        
        return (
            
            <FilterResult 
                onViewDetails={questionsListPresenter.onViewDetails}
                questions={this.filterByTag(this.state.questions, model.getSearchWord())} />
        );
    }

}