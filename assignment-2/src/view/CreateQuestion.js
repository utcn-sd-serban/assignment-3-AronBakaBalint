import React from "react";

const CreateQuestion = ({ title, body, tags, onCreate, onChange }) => (
    <div className="container">
        <h2>Add Question</h2>
            <div className="form-group">
                <label>Title: </label>
                <input value={title} 
                    onChange={ e => onChange("title", e.target.value) } data-cy="title" />
            </div>
            <div className="form-group">
                <label>Text: </label>
                <input value={body} 
                    onChange={ e => onChange("body", e.target.value) } data-cy="text" />
            </div>
            <div className="form-group">
                <label>Tags: </label>
                <input value={tags} 
                    onChange={ e => onChange("tags", e.target.value) } data-cy="tags" />    
            </div>
            <button className="btn btn-secondary" onClick={onCreate} data-cy="create">Add</button>
    </div>
);

export default CreateQuestion;