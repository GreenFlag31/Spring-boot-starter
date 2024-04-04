package com.example.demo.mysql;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    com.example.demo.mysql.User user = userRepository.findByUsername(username);
    System.out.println("----------------------");
    System.out.println("USER: " + user.getUsername() + user.getPassword() +
        getAuthorities(user.getRoles()));
    System.out.println("----------------------");

    return new User(user.getUsername(), user.getPassword(),
        getAuthorities(user.getRoles()));
  }

  private Set<GrantedAuthority> getAuthorities(Set<Role> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
        .collect(Collectors.toSet());
  }

}