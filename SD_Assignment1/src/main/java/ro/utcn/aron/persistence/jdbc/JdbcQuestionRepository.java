package ro.utcn.aron.persistence.jdbc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import ro.utcn.aron.model.Answer;
import ro.utcn.aron.model.Question;
import ro.utcn.aron.model.Vote;
import ro.utcn.aron.persistence.api.QuestionRepository;

public class JdbcQuestionRepository implements QuestionRepository {

	private final JdbcTemplate template;

	public JdbcQuestionRepository(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public Question save(Question question) {
		if (question.getId() != 0) {
			update(question);
		} else {
			int id = insert(question);
			question.setId(id);
		}

		return question;
	}

	@Override
	public Optional<Question> findById(int id) {
		List<Question> questions = template.query("SELECT * FROM question WHERE id = ?", new Object[] { id },
				(resultSet, i) -> new Question(resultSet.getInt("id"), resultSet.getString("title"),
						resultSet.getString("body"), list2String(getTagsFromJdbc(resultSet.getInt("id"))),
						resultSet.getString("author"), resultSet.getString("creationdate")));

		return questions.isEmpty() ? Optional.empty() : Optional.ofNullable(questions.get(0));
	}

	@Override
	public void remove(Question question) {
		template.update("DELETE FROM question WHERE id = ?", question.getId());
	}

	@Override
	public List<Question> findAll() {
		return template.query("SELECT * FROM question",
				(resultSet, i) -> new Question(resultSet.getInt("id"), resultSet.getString("title"),
						resultSet.getString("body"), list2String(getTagsFromJdbc(resultSet.getInt("id"))),
						resultSet.getString("author"), resultSet.getString("creationdate"),
						getAnswersFromJdbc(resultSet.getInt("id")), getQuestionScore(resultSet.getInt("id"))));
	}

	private int insert(Question question) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
		insert.setTableName("question");
		insert.setGeneratedKeyName("id");

		Map<String, Object> data = new HashMap<>();
		data.put("title", question.getTitle());
		data.put("body", question.getBody());
		data.put("author", question.getAuthor());

		data.put("creationdate", new Date().toString());
		int questionId = insert.executeAndReturnKey(data).intValue();

		SimpleJdbcInsert insert2 = new SimpleJdbcInsert(template);
		insert2.setTableName("tags");
		insert2.setGeneratedKeyName("tagid");

		Map<String, Object> data2 = new HashMap<>();
		String[] tags = question.getTags().split(",");
		for (int i = 0; i < tags.length; i++) {
			data2.put("questionid", questionId);
			data2.put("tags", tags[i]);
			insert2.execute(data2);
		}

		return questionId;
	}

	private void update(Question question) {
		template.update("UPDATE question SET title = ?, body = ?, author = ?, creationdate = ? WHERE id = ? ",
				question.getTitle(), question.getBody(), question.getAuthor(), question.getCreationDate(),
				question.getId());
	}

	@Override
	public List<Question> filterByTitle(String title) {
		return template
				.query("SELECT * FROM question",
						(resultSet, i) -> new Question(resultSet.getInt("id"), resultSet.getString("title"),
								resultSet.getString("body"), list2String(getTagsFromJdbc(resultSet.getInt("id"))),
								resultSet.getString("author"), resultSet.getString("creationdate"),
								getAnswersFromJdbc(resultSet.getInt("id")), getQuestionScore(resultSet.getInt("id"))))
				.stream().filter(q -> q.getTitle().contains(title)).collect(Collectors.toList());
	}

	@Override
	public List<Question> filterByTag(String tag) {
		return template.query(
				"SELECT * FROM question JOIN tags ON question.id = tags.questionid WHERE tags.tags LIKE '%" + tag
						+ "%'",
				(resultSet, i) -> new Question(resultSet.getInt("id"), resultSet.getString("title"),
						resultSet.getString("body"), list2String(getTagsFromJdbc(resultSet.getInt("id"))),
						resultSet.getString("author"), resultSet.getString("author"),
						getAnswersFromJdbc(resultSet.getInt("id")), getQuestionScore(resultSet.getInt("id"))));
	}

	private List<String> getTagsFromJdbc(int questionId) {
		return template.query("SELECT * FROM tags WHERE questionid = ?",
				(resultSet, i) -> new String(resultSet.getString("tags")), questionId);
	}

	private String list2String(List<String> tagList) {
		String text = "";
		for (int i = 0; i < tagList.size() - 1; i++) {
			text += tagList.get(i) + ",";
		}
		text += tagList.get(tagList.size() - 1);

		return text;
	}

	@Override
	public void answerQuestion(int questionid, String user, String answer) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
		insert.setTableName("answers");
		insert.setGeneratedKeyName("answerid");

		Map<String, Object> data = new HashMap<>();
		data.put("questionid", questionid);
		data.put("author", user);
		data.put("answer", answer);

		data.put("creationdate", new Date().toString());
		insert.execute(data);
	}

	private List<Answer> getAnswersFromJdbc(int questionid) {
		return template.query("SELECT * FROM answers WHERE questionid = ?",
				(resultSet, i) -> new Answer(resultSet.getInt("answerid"), resultSet.getString("answer"),
						resultSet.getString("author"), resultSet.getString("creationdate"),
						getAnswerScore(resultSet.getInt("answerid"))),
				questionid);
	}

	private int getAnswerScore(int answerid) {
		return template
				.query("SELECT * FROM avotes WHERE answerid = ?",
						(resultSet, i) -> new Vote(resultSet.getString("author"), resultSet.getInt("voteid"),
								resultSet.getInt("votetype")),
						answerid)
				.stream().mapToInt(vote -> vote.getVoteYype()).sum();
	}

	private int getQuestionScore(int questionid) {
		return template
				.query("SELECT * FROM qvotes WHERE questionid = ?",
						(resultSet, i) -> new Vote(resultSet.getString("author"), resultSet.getInt("voteid"),
								resultSet.getInt("votetype")),
						questionid)
				.stream().mapToInt(vote -> vote.getVoteYype()).sum();
	}

	@Override
	public void editQuestion(int questionid, String user, String text) {
		List<Question> result = template.query("SELECT * FROM question WHERE id = ? and author = ?",
				(resultSet, i) -> new Question(resultSet.getInt("id"), resultSet.getString("title"),
						resultSet.getString("body"), list2String(getTagsFromJdbc(resultSet.getInt("id"))),
						resultSet.getString("author"), resultSet.getString("creationdate"),
						getAnswersFromJdbc(resultSet.getInt("id"))),
				questionid, user);

		if (result.isEmpty()) {
			System.out.println("You cannot edit other question!");
			return;
		}

		template.update("UPDATE question SET body = ? WHERE id = ? ", text, questionid);
	}

	@Override
	public void removeAnswer(int answerid, String user) {
		List<Answer> result = template.query("SELECT * FROM answers WHERE answerid = ? and author = ?",
				(resultSet, i) -> new Answer(resultSet.getInt("answerid"), resultSet.getString("author"),
						resultSet.getString("answer"), resultSet.getString("creationdate")),
				answerid, user);

		if (result.isEmpty()) {
			System.out.println("You can delete your answer only!");
			return;
		}

		template.update("DELETE FROM answers WHERE answerid = ? ", answerid);
	}

	@Override
	public void editAnswer(int answerid, String user, String newText) {
		List<Answer> result = template.query("SELECT * FROM answers WHERE answerid = ? and author = ?",
				(resultSet, i) -> new Answer(resultSet.getInt("answerid"), resultSet.getString("author"),
						resultSet.getString("answer"), resultSet.getString("creationdate")),
				answerid, user);

		if (result.isEmpty()) {
			System.out.println("You can edit your answer only!");
			return;
		}

		template.update("UPDATE answers SET answer = ? WHERE answerid = ? ", newText, answerid);

	}

	@Override
	public void upVoteAnswer(String username, int answerid) {

		if (ownAnswer(username, answerid)) {
			System.out.println("You cannot vote your own answer!");
			return;
		}

		if (alreadyUpvotedAnswer(username, answerid)) {
			System.out.println("You already upvoted this answer!");
			return;
		}

		if (alreadyDownvotedAnswer(username, answerid)) {
			template.update("UPDATE avotes SET votetype = 1 WHERE answerid = ? ", answerid);
			return;
		}

		// if it was not already voted we will insert the new vote
		SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
		insert.setTableName("avotes");
		insert.setGeneratedKeyName("voteid");

		Map<String, Object> data = new HashMap<>();
		data.put("answerid", answerid);
		data.put("author", username);
		data.put("votetype", 1);

		insert.execute(data);

	}

	@Override
	public void downVoteAnswer(String username, int answerid) {

		if (ownAnswer(username, answerid)) {
			System.out.println("You cannot vote your own answer!");
			return;
		}

		if (alreadyDownvotedAnswer(username, answerid)) {
			System.out.println("You already downvoted this answer!");
			return;
		}

		if (alreadyUpvotedAnswer(username, answerid)) {
			template.update("UPDATE avotes SET votetype = -1 WHERE answerid = ? ", answerid);
			return;
		}

		// if it was not already voted we will insert the new vote
		SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
		insert.setTableName("avotes");
		insert.setGeneratedKeyName("voteid");

		Map<String, Object> data = new HashMap<>();
		data.put("answerid", answerid);
		data.put("author", username);
		data.put("votetype", -1);

		insert.execute(data);
	}

	@Override
	public void upVoteQuestion(String username, int questionid) {
		if (ownQuestion(username, questionid)) {
			System.out.println("You cannot vote your own question!");
			return;
		}

		if (alreadyUpvotedQuestion(username, questionid)) {
			System.out.println("You already upvoted this question!");
			return;
		}

		if (alreadyDownvotedQuestion(username, questionid)) {
			template.update("UPDATE qvotes SET votetype = 1 WHERE questionid = ? ", questionid);
			return;
		}

		// if it was not already voted we will insert the new vote
		SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
		insert.setTableName("qvotes");
		insert.setGeneratedKeyName("voteid");

		Map<String, Object> data = new HashMap<>();
		data.put("questionid", questionid);
		data.put("author", username);
		data.put("votetype", 1);

		insert.execute(data);
	}

	@Override
	public void downVoteQuestion(String username, int questionid) {
		if (ownQuestion(username, questionid)) {
			System.out.println("You cannot vote your own question!");
			return;
		}

		if (alreadyDownvotedQuestion(username, questionid)) {
			System.out.println("You already downvoted this question!");
			return;
		}

		if (alreadyUpvotedQuestion(username, questionid)) {
			template.update("UPDATE qvotes SET votetype = -1 WHERE questionid = ? ", questionid);
			return;
		}

		// if it was not already voted we will insert the new vote
		SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
		insert.setTableName("qvotes");
		insert.setGeneratedKeyName("voteid");

		Map<String, Object> data = new HashMap<>();
		data.put("questionid", questionid);
		data.put("author", username);
		data.put("votetype", -1);

		insert.execute(data);

	}

	private boolean alreadyUpvotedAnswer(String username, int answerid) {
		List<Vote> voteList = template.query("SELECT * FROM avotes WHERE answerid = ? and author = ?",
				(resultSet, i) -> new Vote(resultSet.getString("author"), resultSet.getInt("answerid"),
						resultSet.getInt("votetype")),
				answerid, username);

		if (voteList.isEmpty())
			return false;

		if (voteList.get(0).getVoteYype() > 0)
			return true;

		return false;

	}

	private boolean alreadyDownvotedAnswer(String username, int answerid) {
		List<Vote> voteList = template.query("SELECT * FROM avotes WHERE answerid = ? and author = ?",
				(resultSet, i) -> new Vote(resultSet.getString("author"), resultSet.getInt("answerid"),
						resultSet.getInt("votetype")),
				answerid, username);

		if (voteList.isEmpty())
			return false;

		if (voteList.get(0).getVoteYype() < 0)
			return true;

		return false;
	}

	private boolean ownAnswer(String username, int answerid) {
		List<Answer> result = template
				.query("SELECT * FROM answers WHERE answerid = ? and author = ?",
						(resultSet, i) -> new Answer(resultSet.getInt("answerid"), resultSet.getString("author"),
								resultSet.getString("answer"), resultSet.getString("creationdate")),
						answerid, username);

		return !result.isEmpty();
	}

	private boolean alreadyUpvotedQuestion(String username, int questionid) {
		List<Vote> voteList = template.query("SELECT * FROM qvotes WHERE questionid = ? and author = ?",
				(resultSet, i) -> new Vote(resultSet.getString("author"), resultSet.getInt("questionid"),
						resultSet.getInt("votetype")),
				questionid, username);

		if (voteList.isEmpty())
			return false;

		if (voteList.get(0).getVoteYype() > 0)
			return true;

		return false;

	}

	private boolean alreadyDownvotedQuestion(String username, int questionid) {
		List<Vote> voteList = template.query("SELECT * FROM qvotes WHERE questionid = ? and author = ?",
				(resultSet, i) -> new Vote(resultSet.getString("author"), resultSet.getInt("questionid"),
						resultSet.getInt("votetype")),
				questionid, username);

		if (voteList.isEmpty())
			return false;

		if (voteList.get(0).getVoteYype() < 0)
			return true;

		return false;
	}

	private boolean ownQuestion(String username, int questionid) {
		List<Question> result = template.query("SELECT * FROM question WHERE id = ? and author = ?",
				(resultSet, i) -> new Question(resultSet.getInt("id"), resultSet.getString("title"),
						resultSet.getString("body"), list2String(getTagsFromJdbc(resultSet.getInt("id"))),
						resultSet.getString("author"), resultSet.getString("creationdate"),
						getAnswersFromJdbc(resultSet.getInt("id"))),
				questionid, username);

		return !result.isEmpty();
	}

	@Override
	public List<Answer> getAnswerByQuestionID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
