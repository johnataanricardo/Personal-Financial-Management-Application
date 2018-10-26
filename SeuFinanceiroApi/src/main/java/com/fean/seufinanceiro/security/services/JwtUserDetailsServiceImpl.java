package com.fean.seufinanceiro.security.services;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	/*
	@Autowired
	private UserPersonService userPersonService;
	*/

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Optional<UserPerson> userPerson = userPersonService.findUserPersonByUsernameEmail(username);

		/*
		if (userPerson.isPresent()) {
			return JwtUserFactory.create(userPerson.get());
		}
		*/

		throw new UsernameNotFoundException("Email não encontrado.");
	}

}
