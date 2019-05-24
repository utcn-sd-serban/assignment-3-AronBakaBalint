class LoginPresenter {

    onLogin() {
        window.location.assign("#/question-list");
    }

}

const loginPresenter = new LoginPresenter();

export default loginPresenter;