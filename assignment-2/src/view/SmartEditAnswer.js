import React, { Component } from "react";
import EditAnswer from "../view/EditAnswer";
import createAnswerPresenter from "../presenter/createAnswerPresenter";

export default class SmartCreateQuestion extends Component {

    render() {
        return (
            <EditAnswer
                onEdit={createAnswerPresenter.onAnswerEditFinished}
            />
        );
    }
}