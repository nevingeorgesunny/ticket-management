package com.nevin.sunny.dao;

import com.nevin.sunny.pojo.entities.postgress.UserEntity;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 5:30 pm
 */
public interface IUserDao {

    UserEntity save(UserEntity userEntity);
}
