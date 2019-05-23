import model from "../model/model";

class CreateAnswerPresenter {

    onCreateAnswer(questionid) {
        model.addAnswer(questionid, model.state.newAnswer.text);
        model.changeNewAnswerProperty("answerid", "");
        model.changeNewAnswerProperty("text", "");
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