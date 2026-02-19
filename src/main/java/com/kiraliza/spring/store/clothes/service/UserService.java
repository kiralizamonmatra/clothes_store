package com.kiraliza.spring.store.clothes.service;

import com.kiraliza.spring.store.clothes.exception.UserNotFoundException;
import com.kiraliza.spring.store.clothes.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService
{
    User findByUsername(String username) throws UserNotFoundException;
    boolean isExistsByUsername(String username);
    User save(User user);
}
