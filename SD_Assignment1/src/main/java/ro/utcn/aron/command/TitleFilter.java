package ro.utcn.aron.command;

import ro.utcn.aron.service.QuestionManagementService;

public class TitleFilter implements Command {

	private final QuestionManagementService questionManagementService;
	
	private final String title;
	
	public TitleFilter(QuestionManagementService questionManagementService, String title) {
		this.questionManagementService = questionManagementService;
		this.title = title;
	}
	
	@Override
	public Object execute() {
		return questionManagementService.filterByTag(title);
	}

}
