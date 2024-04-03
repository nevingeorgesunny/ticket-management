package com.nevin.sunny.dao.impl;

import com.nevin.sunny.dao.IUserDao;
import com.nevin.sunny.exception.TicketException;
import com.nevin.sunny.pojo.entities.postgress.UserEntity;
import com.nevin.sunny.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public UserEntity findById(UUID userId) throws TicketException {
        return userRepository.findById(userId).orElseThrow(() -> new TicketException(
                String.format("No user with Id : %s found ", userId)
        ));
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) throws TicketException {
        return userRepository.findByEmail(email);
    }
}
