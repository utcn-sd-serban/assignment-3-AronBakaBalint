package ro.utcn.aron.persistence.api;

import java.util.List;
import java.util.Optional;

import ro.utcn.aron.model.User;

public interface UserRepository {

	boolean matches(String username, String password);
	
	void save(String username, String password);
	
	Optional<User> findByName(String name);
	
	List<User> findAll();
}
