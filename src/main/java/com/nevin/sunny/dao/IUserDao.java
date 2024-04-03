package com.nevin.sunny.dao;

import com.nevin.sunny.exception.TicketException;
import com.nevin.sunny.pojo.entities.postgress.UserEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 5:30 pm
 */
public interface IUserDao {

    UserEntity save(UserEntity userEntity);

    UserEntity findById(UUID userId) throws TicketException;

    Optional<UserEntity> findByEmail(String email) throws TicketException;
}
