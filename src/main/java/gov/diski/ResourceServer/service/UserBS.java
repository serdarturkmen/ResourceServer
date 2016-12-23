package gov.diski.ResourceServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import gov.diski.ResourceServer.model.Role;
import gov.diski.ResourceServer.model.User;
import gov.diski.ResourceServer.repository.RoleRepository;
import gov.diski.ResourceServer.repository.UserRepository;

@Service
public class UserBS {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public void authenticateUser(String username) {
		User user = (User) loadUserByUsername(username);
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getRoles()));
	}

	public void clearAuthentication() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username);
	}

	public User createUser(String email, String accessToken) {
		Role role = roleRepository.findOne(2L);
		User user = new User();
		user.getRoles().add(role);
		user.setAccessToken(accessToken);
		user.setEmail(email);
		return userRepository.save(user);
	}

}
