package ro.utcn.aron.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ro.utcn.aron.dto.AnswerDTO;
import ro.utcn.aron.dto.QuestionDTO;
import ro.utcn.aron.model.Question;
import ro.utcn.aron.persistence.api.QuestionRepository;
import ro.utcn.aron.persistence.api.RepositoryFactory;

@Component
public class QuestionManagementService {

	private final RepositoryFactory questionRepositoryFactory;

	public QuestionManagementService(RepositoryFactory questionRepositoryFactory) {

		this.questionRepositoryFactory = questionRepositoryFactory;
	}

	@Transactional
	public List<QuestionDTO> listQuestions() {
		return questionRepositoryFactory.createQuestionRepository().findAll().stream().map(QuestionDTO::ofEntity)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public List<AnswerDTO> listAnswerByID(int id) {
		return questionRepositoryFactory.createQuestionRepository().getAnswerByQuestionID(id).stream().map(AnswerDTO::ofEntity)
				.collect(Collectors.toList());
	}

	@Transactional
	public QuestionDTO addQuestion(QuestionDTO dto) {
		Question question = new Question(dto.getId(), dto.getTitle(), dto.getBody(), dto.getTags(), dto.getAuthor(), dto.getCreationDate());
		return dto.ofEntity(questionRepositoryFactory.createQuestionRepository()
				.save(question));
	}

	@Transactional
	public void removeQuestion(int id) {
		QuestionRepository repository = questionRepositoryFactory.createQuestionRepository();
		Question question = repository.findById(id).orElseThrow(RuntimeException::new);
		repository.remove(question);
	}

	@Transactional
	public List<QuestionDTO> filterByTitle(String title) {
		return questionRepositoryFactory.createQuestionRepository().filterByTitle(title).stream().map(QuestionDTO::ofEntity).collect(Collectors.toList());
	}

	@Transactional
	public List<QuestionDTO> filterByTag(String tag) {
		return questionRepositoryFactory.createQuestionRepository().filterByTitle(tag).stream().map(QuestionDTO::ofEntity).collect(Collectors.toList());
	}

	@Transactional
	public void answerQuestion(String user, int questionid, String answer) {
		QuestionRepository repository = questionRepositoryFactory.createQuestionRepository();
		repository.answerQuestion(questionid, user, answer);
	}

	@Transactional
	public void editQuestion(String user, int questionid, String answer) {
		QuestionRepository repository = questionRepositoryFactory.createQuestionRepository();
		repository.editQuestion(questionid, user, answer);
	}

	@Transactional
	public void editAnswer(String user, int answerid, String answer) {
		QuestionRepository repository = questionRepositoryFactory.createQuestionRepository();
		repository.editAnswer(answerid, user, answer);
	}

	@Transactional
	public void deleteAnswer(String user, int answerid) {
		QuestionRepository repository = questionRepositoryFactory.createQuestionRepository();
		repository.removeAnswer(answerid, user);
	}

	@Transactional
	public void upVoteAnswer(String user, int answerid) {
		QuestionRepository repository = questionRepositoryFactory.createQuestionRepository();
		repository.upVoteAnswer(user, answerid);
	}

	@Transactional
	public void downVoteAnswer(String user, int answerid) {
		QuestionRepository repository = questionRepositoryFactory.createQuestionRepository();
		repository.downVoteAnswer(user, answerid);
	}

	@Transactional
	public void upVoteQuestion(String user, int questionid) {
		QuestionRepository repository = questionRepositoryFactory.createQuestionRepository();
		repository.upVoteQuestion(user, questionid);
	}

	@Transactional
	public void downVoteQuestion(String user, int questionid) {
		QuestionRepository repository = questionRepositoryFactory.createQuestionRepository();
		repository.downVoteQuestion(user, questionid);
	}
}
