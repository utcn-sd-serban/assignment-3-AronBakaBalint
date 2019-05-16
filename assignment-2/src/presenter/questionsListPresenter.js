import model from "../model/model";

class QuestionsListPresenter {
    onInit(){
        model.loadQuestions();
    }

    onCreateQuestion() {
        window.location.assign("#/create-question");
    }

    onFilterByTitle() {
        model.filterByTitle();
        window.location.assign("#/title-filter-result");
    }

    onFilterByTag() {
        model.filterByTag();
        window.location.assign("#/tag-filter-result");
    }

    onEditAnswer(){
        window.location.assign("#/edit-answer");
    }

    onViewDetails(index) {
        window.location.assign("#/question-details/" + index);
    }
}

const questionsListPresenter = new QuestionsListPresenter();

export default questionsListPresenter;