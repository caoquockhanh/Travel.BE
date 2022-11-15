package com.MyTravel.mytravel.security.services;

import com.MyTravel.mytravel.exception.ApiException;
import com.MyTravel.mytravel.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MyTravel.mytravel.model.User;
import com.MyTravel.mytravel.repository.UserRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

		return UserDetailsImpl.build(user);
	}

}
