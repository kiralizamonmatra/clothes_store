package com.kiraliza.spring.store.clothes.service.impl;

import com.kiraliza.spring.store.clothes.exception.UserNotFoundException;
import com.kiraliza.spring.store.clothes.model.User;
import com.kiraliza.spring.store.clothes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MongoUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException
    {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty())
        {
            throw new UserNotFoundException("", username);
        }

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.get().getRole().name()));

        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), authorities);
    }
}
