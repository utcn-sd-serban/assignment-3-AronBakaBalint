package ro.utcn.aron.dto;

import ro.utcn.aron.model.Answer;

public class AnswerDTO {

	private int id;
	private int questionID;
	private String text;
	private String author;
	private String creationDate; 
	
	public static AnswerDTO ofEntity(Answer answer) {
		AnswerDTO answerDTO = new AnswerDTO();
		answerDTO.setText(answer.getText());
		answerDTO.setId(answer.getId());
		answerDTO.setAuthor(answer.getAuthor());
		answerDTO.setCreationDate(answer.getCreationDate());
		answerDTO.setQuestionID(answer.getQuestionID());
		return answerDTO;
	}
	

	public int getQuestionID() {
		return questionID;
	}


	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	
}
