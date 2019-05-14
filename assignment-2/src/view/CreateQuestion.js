import React from "react";

const CreateQuestion = ({ title, body, tags, onCreate, onChange }) => (
    <div className="container">
        <h2>Add Question</h2>
        <form>
            <div className="form-group">
                <label>Title: </label>
                <input value={title} 
                    onChange={ e => onChange("title", e.target.value) } />
            </div>
            <div className="form-group">
                <label>Text: </label>
                <input value={body} 
                    onChange={ e => onChange("body", e.target.value) } />
            </div>
            <div className="form-group">
                <label>Tags: </label>
                <input value={tags} 
                    onChange={ e => onChange("tags", e.target.value) } />    
            </div>
            <button className="btn btn-secondary" onClick={onCreate}>Add</button>
        </form>
    </div>
);

export default CreateQuestion;