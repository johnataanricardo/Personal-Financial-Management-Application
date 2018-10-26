package com.fean.seufinanceiro.security;

import com.billplan.billbox.models.UserPerson;
import com.billplan.billbox.security.enums.ProfileEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class JwtUserFactory {

	private JwtUserFactory() {
	}

	/**
	 * Converte e gera um JwtUser com base nos dados de um funcionário.
	 * 
	 * @param userPerson
	 * @return JwtUser
	 */
	public static JwtUser create(UserPerson userPerson) {
		return new JwtUser(userPerson.getId(), userPerson.getUsernameEmail(), userPerson.getPassword(),
				mapToGrantedAuthorities(userPerson.getProfile()));
	}

	/**
	 * Converte o perfil do usuário para o formato utilizado pelo Spring Security.
	 * 
	 * @param profileEnum
	 * @return List<GrantedAuthority>
	 */
	private static List<GrantedAuthority> mapToGrantedAuthorities(ProfileEnum profileEnum) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(profileEnum.toString()));
		return authorities;
	}

}
