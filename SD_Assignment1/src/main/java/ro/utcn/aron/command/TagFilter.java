package ro.utcn.aron.command;

import ro.utcn.aron.service.QuestionManagementService;

public class TagFilter implements Command {

	private final QuestionManagementService questionManagementService;
	
	private final String tag;
	
	public TagFilter(QuestionManagementService questionManagementService, String tag) {
		this.questionManagementService = questionManagementService;
		this.tag = tag;
	}
	
	@Override
	public Object execute() {
		return questionManagementService.filterByTag(tag);
	}

}
