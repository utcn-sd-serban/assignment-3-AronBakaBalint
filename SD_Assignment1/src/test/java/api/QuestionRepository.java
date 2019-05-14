package api;

import java.util.List;
import java.util.Optional;

import model.Question;

public interface QuestionRepository {

	Question save(Question question);
	
	Optional<Question> findByID(int id);
	
	void remove(Question question);
	
	List<Question> findAll();
}
