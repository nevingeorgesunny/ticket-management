package com.nevin.sunny.dao.impl;

import com.nevin.sunny.dao.IUserDao;
import com.nevin.sunny.pojo.entities.postgress.UserEntity;
import com.nevin.sunny.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 5:30 pm
 */

@Service
public class UserDao implements IUserDao {

    private final UserRepository userRepository;

    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
