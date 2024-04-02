package com.nevin.sunny.repositories;

import com.nevin.sunny.pojo.entities.postgress.SeatEntity;
import com.nevin.sunny.pojo.entities.postgress.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 5:24 pm
 */
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
}
