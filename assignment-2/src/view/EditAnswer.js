import React from "react";
import model from "../model/model";

const EditAnswer = ( { onEdit } ) => (
    <div className="container">
        <h2>Edit Answer</h2>
            <div className="form-group">
                <label>New Text: </label>
                <input onChange={ e => {model.setNewAnswerText(e.target.value)} } />
            </div>
            <button className="btn btn-primary" onClick={() => {onEdit(); model.editAnswer()}} >Confirm</button>
    </div>
);

export default EditAnswer;