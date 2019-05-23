package ro.utcn.aron.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ro.utcn.aron.dto.AnswerDTO;
import ro.utcn.aron.service.QuestionManagementService;

@RestController
public class AnswerController {

	private final QuestionManagementService questionManagementService;
	
	
	public AnswerController(QuestionManagementService questionService) {
		this.questionManagementService = questionService;
	}
	
	@GetMapping("/question-details/getAnswers")
	public List<AnswerDTO> getAnswerById(@RequestParam String id){
		return questionManagementService.listAnswerByID(Integer.parseInt(id)+1);
	}
	
	@PostMapping("/question-details/addAnswer")
	public List<AnswerDTO> create(@RequestBody AnswerDTO dto) {
		questionManagementService.answerQuestion(dto.getAuthor(), dto.getQuestionID(), dto.getText());
		return questionManagementService.listAnswerByID(dto.getQuestionID());
	}
	
	//delete and put mapping are throwing some CORS exceptions so I preferred to use POST instead though it is not correct 
	
	@PostMapping("/question-details/deleteAnswer")
	public List<AnswerDTO> delete(@RequestBody AnswerDTO dto) {
		questionManagementService.deleteAnswer(dto.getAuthor(), dto.getId());
		return questionManagementService.listAnswerByID(dto.getQuestionID());
	}
	
	@PostMapping("/question-details/editAnswer")
	public List<AnswerDTO> edit(@RequestBody AnswerDTO dto) {
		questionManagementService.editAnswer(dto.getAuthor(), dto.getId(), dto.getText());
		return questionManagementService.listAnswerByID(dto.getQuestionID());
	}
	
}
