import React from "react";
import model from "../model/model";

const QuestionDetails = ({ id, title, body, tags, author, postDate, answers, onCreateAnswer, onAnswerChange, onEditAnswer }) => (
    <div>
        <span><font size="8"><b>{ title }</b></font></span>
        <br />
        <span><font size="4">{ body }</font></span>
        <br />
        <br />
        <span><font size="3" color="blue">{ author }</font></span>
        <br />
        <span class="badge badge-secondary">{ postDate }</span>
        <br />
        <br/>
        <span class="badge badge-secondary"><font size="2">{ tags }</font></span>
        <br />
        <br/>
        <input onChange={ e => {onAnswerChange("text", e.target.value); onAnswerChange("answerid",id)}} id="inputField"></input>
        <button onClick={() => {onCreateAnswer(); document.getElementById('inputField').value = ''}}>Answer</button>
        <br/>
        <br/>
        <div className="col-md-12">
        <table className="table" border="1">
            <thead class="thead-dark">
                <tr>
                    <th>Text</th>
                    <th>Author</th>
                    <th>Post Date</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                {
                    answers.map((answer, index) => (
                        <tr key={index}>
                            <td>{answer.text}</td>
                            <td>{answer.author}</td>
                            <td>{answer.postDate}</td>
                            <td><button className="btn btn-secondary" onClick={() => {onEditAnswer(); model.setEditedAnswerId(answer.answerid)}}>Edit</button></td>
                            <td><button className="btn btn-secondary" onClick={() => model.deleteAnswer(answer.answerid)}>Delete</button></td>
                        </tr>
                    ))
                }
            </tbody>
        </table>
        </div>
    </div>
);

export default QuestionDetails;