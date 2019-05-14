import React from "react";
import model from "../model/model";

const QuestionsList = ({ questions, onCreateQuestion, onViewDetails, onFilterByTitle, onFilterByTag }) => (
    <div>
        <h2>{ "Stack Overflow" }</h2>
        <br/>
        <div className="col-md-12">
        <table className="table" border="1">
            <thead class="thead-dark">
                <tr>
                    <th>{}</th>
                    <th>Title</th>
                    <th>Text</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                {
                    questions.map((question, index) => (
                        <tr key={index}>
                            <td>{question.id}</td>
                            <td>{question.title}</td>
                            <td>{question.body}</td>
                            <td><button className="btn btn-secondary" onClick={() => onViewDetails(index)} >View Details</button></td>
                        </tr>
                    ))
                }
            </tbody>
        </table>
        </div>
        <button class="btn btn-primary"  onClick={onCreateQuestion}>Add new Question</button>
        <br/>
        <hr></hr>
        <input onChange={ e => model.setSearchWord(e.target.value) }/>
        <button onClick={onFilterByTitle}>Filter By Title</button>
        <button onClick={onFilterByTag}>Filter By Tag</button>
    </div>
);

export default QuestionsList;