
const BASE_URL = "http://localhost:8080";

export default class RestClient{
    constructor(username, password){
        this.authorization = "Basic " + btoa(username+":"+password);
    }

    loadAllQuestions(){
        return fetch(BASE_URL+"/question-list", {
            method: "GET",
            headers: {
                "Authorization": this.authorization
            }
        }).then(response => response.json());
    }

    filterByTitle(searchPart){
        return fetch(BASE_URL+"/question-list/searchTitle?title="+searchPart, {
            method: "GET",
            headers: {
                "Authorization": this.authorization
            }
        }).then(response => response.json());
    }

    filterByTag(searchPart){
        return fetch(BASE_URL+"/question-list/searchTag?tag="+searchPart, {
            method: "GET",
            headers: {
                "Authorization": this.authorization
            }
        }).then(response => response.json());
    }

    addAnswer(questionid, text, user){
        return fetch(BASE_URL+"/question-details/addAnswer", {
            method: "POST",
            body: JSON.stringify({
                text: text,
                questionID: questionid,
                author: user
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type":  "application/json"
            }
        }).then(response => response.json());
    }

    deleteAnswer(questionid, answerid, user){
        return fetch(BASE_URL+"/question-details/deleteAnswer", {
            method: "POST",
            body: JSON.stringify({
                id: answerid,
                questionID: questionid,
                author: user
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type":  "application/json",
                "Access-Control-Allow-Origin": "http://localhost:3000"
            }
        }).then(response => response.json());
    }

    editAnswer(questionid, answerid, user, text){
        return fetch(BASE_URL+"/question-details/editAnswer", {
            method: "POST",
            body: JSON.stringify({
                id: answerid,
                questionID: questionid,
                author: user,
                text: text
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type":  "application/json"
            }
        }).then(response => response.json());
    }

    createQuestion(title, text, tags, username){
        return fetch(BASE_URL+"/question-list", {
            method: "POST",
            body: JSON.stringify({
                title: title,
                body: text,
                tags: tags,
                author: username
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type":  "application/json"
            }
        }).then(response => response.json());
    }

    getAnswerByQuestionId(id){
        return fetch(BASE_URL+"/question-details/getAnswers?id="+id, {
            method: "GET",
            headers: {
                "Authorization": this.authorization
            }
        }).then(response => response.json());
    }
}