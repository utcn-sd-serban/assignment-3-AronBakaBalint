package ro.utcn.aron.persistence.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import ro.utcn.aron.model.User;
import ro.utcn.aron.persistence.api.UserRepository;

@Component
@ConditionalOnProperty(name="aron.repository-type", havingValue = "MEMORY")
public class InMemoryUserRepository implements UserRepository {

	private static final Map<String, String> users = new ConcurrentHashMap<>();
	
	@Override
	public boolean matches(String username, String password) {
		return users.get(username).equals(password);
	}

	@Override
	public void save(String name, String password) {
		users.put(name, password);
		
	}

	@Override
	public List<User> findAll() {
		List<User> userList = new ArrayList<>();
		users.forEach((user, password)->{
			userList.add(new User(user, password));
		});
		
		return userList;
	}

	@Override
	public Optional<User> findByName(String name) {
		return Optional.ofNullable(new User(name, users.get(name)));
	}

}
