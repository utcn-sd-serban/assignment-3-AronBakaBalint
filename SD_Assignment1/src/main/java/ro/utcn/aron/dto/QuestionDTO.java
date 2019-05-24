package ro.utcn.aron.dto;

import ro.utcn.aron.model.Question;

public class QuestionDTO {

	private int id;
	private String title;
	private String body;
	private String tags;
	private String author;
	private String creationDate; 
	
	public static QuestionDTO ofEntity(Question question) {
		QuestionDTO questionDTO = new QuestionDTO();
		questionDTO.setTitle(question.getTitle());
		questionDTO.setId(question.getId());
		questionDTO.setAuthor(question.getAuthor());
		questionDTO.setBody(question.getBody());
		questionDTO.setTags(question.getTags());
		questionDTO.setCreationDate(question.getCreationDate());
		
		return questionDTO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
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
