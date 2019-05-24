package ro.utcn.aron.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ro.utcn.aron.command.Command;
import ro.utcn.aron.command.QuestionAdder;
import ro.utcn.aron.command.TagFilter;
import ro.utcn.aron.command.TitleFilter;
import ro.utcn.aron.dto.AnswerDTO;
import ro.utcn.aron.dto.QuestionDTO;
import ro.utcn.aron.service.QuestionManagementService;

@RestController
public class QuestionController {

	private final QuestionManagementService questionManagementService;
	
	private Command command;
	
	public QuestionController(QuestionManagementService questionService) {
		this.questionManagementService = questionService;
	}
	
	
	@GetMapping("/question-list")
	public List<QuestionDTO> findAll(){
		return questionManagementService.listQuestions();
	}
	
	@GetMapping("/question-list/searchTitle")
	public List<QuestionDTO> filterByTitle(@RequestParam String title){
		command = new TitleFilter(questionManagementService, title);
		return (List<QuestionDTO>)command.execute();
	}
	
	@GetMapping("/question-list/searchTag")
	public List<QuestionDTO> filterByTag(@RequestParam String tag){
		command = new TagFilter(questionManagementService, tag);
		return (List<QuestionDTO>)command.execute();
	}
	
	@PostMapping("/question-list")
	public QuestionDTO create(@RequestBody QuestionDTO dto) {
		command = new QuestionAdder(questionManagementService, dto);
		return (QuestionDTO)command.execute();
	}

}
