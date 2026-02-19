package com.kiraliza.spring.store.clothes.service.impl;

import com.kiraliza.spring.store.clothes.exception.UserNotFoundException;
import com.kiraliza.spring.store.clothes.model.User;
import com.kiraliza.spring.store.clothes.repository.UserRepository;
import com.kiraliza.spring.store.clothes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) throws UserNotFoundException
    {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(null, username));
    }

    public boolean isExistsByUsername(String username)
    {
        return userRepository.findByUsername(username).isPresent();
    }

    public User save(User user)
    {
        return userRepository.save(user);
    }
}
