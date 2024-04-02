package com.nevin.sunny.repositories;

import com.nevin.sunny.pojo.entities.postgress.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 5:24 pm
 */
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
