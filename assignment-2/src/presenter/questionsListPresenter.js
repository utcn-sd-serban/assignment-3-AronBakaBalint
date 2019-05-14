
class QuestionsListPresenter {
    onCreateQuestion() {
        window.location.assign("#/create-question");
    }

    onFilterByTitle() {
        window.location.assign("#/title-filter-result");
    }

    onFilterByTag() {
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