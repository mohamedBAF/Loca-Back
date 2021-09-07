package com.onegateafrica.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onegateafrica.entity.Users;
import com.onegateafrica.repository.UsersRepository;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UsersRepository usersRepository;

  @Override

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user = usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("email not found"));
      return UserFactory.build(user);

  }

}