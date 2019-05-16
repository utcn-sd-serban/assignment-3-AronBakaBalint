import { EventEmitter } from "events";
import RestClient from "../rest/RestClient";

const client = new RestClient("John", "1234");

class Model extends EventEmitter {

    constructor() {
        super();
        this.state = {
            user: "",
            questionid: 2,
            answerid: 3,
            searchWord: "",
            editedAnswerId: -1,
            newAnswerText: "",
            filterResult: [],
            questions: [],
            answers: [{
                answerid: 1,
                questionid: 1,
                text: "I don't know",
                author: "Caitlin",
                postDate: "5/1/2019 10:37 AM"
            },{
                answerid: 2,
                questionid: 1,
                text: "Probably I can help you",
                author: "James",
                postDate: "5/1/2019 11:05 AM"
            },{
                answerid: 3,
                questionid: 1,
                text: "Ask somebody else",
                author: "Tina",
                postDate: "5/1/2019 11:05 AM"
            }],
            newAnswer: {
                anwserid: "",
                text: ""
            },
            newQuestion: {
                title: "",
                body: "",
                tags:""
            }
        };
    }

    loadQuestions(){
        return client.loadAllQuestions().then(questions => {
            this.state = {
                ...this.state,
                questions: questions
            };
            this.emit("change", this.state);
        })
    }

    filterByTitle(){
        return client.filterByTitle(this.state.searchWord).then(filterRes => {
            this.state = {
                ...this.state,
                filterResult: filterRes
            };
            this.emit("change", this.state);
        })
    }

    filterByTag(){
        return client.filterByTag(this.state.searchWord).then(filterRes => {
            this.state = {
                ...this.state,
                filterResult: filterRes
            };
            this.emit("change", this.state);
        })
    }

    getCurrentDate(){
        // get current date
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth()+1; //for January
        var yyyy = today.getFullYear();
        if(dd<10) {
            dd = '0'+dd
        }
        if(mm<10) {
            mm = '0'+mm
        } 
        today = mm + '/' + dd + '/' + yyyy;
        
        return today;
    }

    getAnswersByQuestionId(questionId){
        var result = new Array(0);
        var k=0;
        for(var i=0;i < this.state.answers.length;i++){
            if(this.state.answers[i].questionid === questionId){
                result[k++]=(this.state.answers[i]);
            }
        }

        return result;
    }

    addQuestion(title, body, tags) {
        return client.createQuestion(title, body, tags)
        .then( question => {
                this.state = {
                    ...this.state,
                    questionid: this.state.questionid + 1,
                    questions: this.state.questions.concat([question])
                };
                this.emit("change", this.state);
            }
        );
    }

    addAnswer(questionid, text){
        this.state = {
            ...this.state,
            answerid: this.state.answerid+1,
            answers: this.state.answers.concat([{
                questionid: questionid,
                text: text,
                author: this.state.user,
                postDate: this.getCurrentDate()
            }])
        };
        this.emit("change", this.state);
    }

    //splice did not work for me so I wrote my own delete function
    deleteById(answerList, id){
        var resultElements = new Array(0);
        var k=0;
        for(var i=0;i < answerList.length; i++){
            if(answerList[i].answerid !== id || (answerList[i].author !== this.state.user && answerList[i].answerid === id)){
                resultElements[k++] = answerList[i];
            }
        }

        return resultElements;
    }

    editAnswerText(answerList, answerid, newText){
        var editedList = new Array(0);
        var k=0;
        for(var i=0;i < answerList.length; i++){
            editedList[k] = answerList[i];
            if(editedList[k].answerid === answerid && editedList[k].author === this.state.user){
                editedList[k].text = newText;
            }
            k++;
        }

        return editedList;
    }

    editAnswer(){
        this.state = {
            ...this.state,
            answers: this.editAnswerText(this.state.answers, model.state.editedAnswerId, model.state.newAnswerText)
        }
        this.emit("change", this.state);
    }

    deleteAnswer(id){
        this.state = {
            ...this.state,
            answers: this.deleteById(this.state.answers, id)
        }
        this.emit("change", this.state);
    }

    changeNewAnswerProperty(property, value) {
        this.state = {
            ...this.state,
            newAnswer: {
                ...this.state.newAnswer,
                [property]: value
            }
        };
        this.emit("change", this.state);
    }

    changeNewQuestionProperty(property, value) {
        this.state = {
            ...this.state,
            newQuestion: {
                ...this.state.newQuestion,
                [property]: value
            }
        };
        this.emit("change", this.state);
    }

    setEditedAnswerId(value){
        this.state.editedAnswerId = value;
    }

    getEditedAnswerId(){
        return this.state.editedAnswerId;
    }

    setNewAnswerText(value){
        this.state.newAnswerText = value;
    }

    getNewAnswerText(){
        return this.state.newAnswerText;
    }

    setSearchWord(value){
        this.state.searchWord = value;
    }

    getSearchWord(){
        return this.state.searchWord;
    }

    setUser(user){
        this.state.user = user;
    }

}

const model = new Model();

export default model;