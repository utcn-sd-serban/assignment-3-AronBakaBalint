package ro.utcn.aron.command;

import ro.utcn.aron.dto.QuestionDTO;
import ro.utcn.aron.service.QuestionManagementService;

public class QuestionAdder implements Command {

	private final QuestionManagementService questionManagementService;
	
	private final QuestionDTO questionDto;
		
	public QuestionAdder(QuestionManagementService questionManagementService, QuestionDTO questionDto) {
		this.questionDto = questionDto;
		this.questionManagementService = questionManagementService;
	}
	
	@Override
	public Object execute() {
		return questionManagementService.addQuestion(questionDto);
	}
	
}
