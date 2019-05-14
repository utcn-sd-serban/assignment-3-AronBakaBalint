import model from "../model/model";

class CreateAnswerPresenter {

    onCreateAnswer() {
        model.addAnswer(model.state.newAnswer.answerid, model.state.newAnswer.text);
        model.changeNewQuestionProperty("title", "");
        model.changeNewQuestionProperty("body", "");
    }

    onAnswerEditFinished(){
        window.location.assign("#/question-list");
    }

    onAnswerChange(property, value) {
        model.changeNewAnswerProperty(property, value);
    }

}

const createAnswerPresenter = new CreateAnswerPresenter();

export default createAnswerPresenter;