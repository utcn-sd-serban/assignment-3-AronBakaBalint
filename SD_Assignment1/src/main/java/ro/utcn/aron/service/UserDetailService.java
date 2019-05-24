package ro.utcn.aron.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ro.utcn.aron.persistence.api.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailService(UserRepository userRepo) {
		this.userRepository = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ro.utcn.aron.model.User user = userRepository.findByName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
	}
}
