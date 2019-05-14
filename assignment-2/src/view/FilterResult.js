import React from "react";

//common 'view' for both title and tag filter
const FilterResult = ({ questions, onViewDetails }) => (
    <div>
        <h2>{ "Filter Result" }</h2>
        <div className="col-md-12">
        <table class="table table" border="1" align="center">
            <thead class="thead-dark">
                <tr>
                    <th>Question ID</th>
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
                            <td><button onClick={() => onViewDetails(index)}>View Details</button></td>
                        </tr>
                    ))
                }
            </tbody>
        </table>
        </div>
    </div>
);

export default FilterResult;