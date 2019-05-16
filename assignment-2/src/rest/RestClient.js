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

    createQuestion(title, text, tags){
        return fetch(BASE_URL+"/question-list", {
            method: "POST",
            body: JSON.stringify({
                title: title,
                body: text,
                tags: tags,
                author: "John"
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type":  "application/json"
            }
        }).then(response => response.json());
    }
}