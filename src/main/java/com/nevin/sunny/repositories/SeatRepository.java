package com.nevin.sunny.repositories;

import com.nevin.sunny.pojo.entities.postgress.SeatEntity;
import com.nevin.sunny.pojo.entities.postgress.TicketEntity;
import com.nevin.sunny.pojo.projection.SeatUserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 5:24 pm
 */
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    SeatEntity findTopByIsTakenFalse();

    List<SeatEntity> findAllBySection(String section);

    @Query(value = "SELECT s.seatId as seatId , s.seatNumber as seatNumber, s.section as section, s.isTaken as isTaken " +
            " ,t.id as ticketId , u.email as email , u.firstName as firstName , u.lastName as lastName , u.id as userId  FROM SeatEntity s " +
            " LEFT JOIN TicketEntity t ON t.seatId = s.seatId" +
            " LEFT JOIN UserEntity u on u.id = t.userId" +
            " WHERE s.section = ?1 AND (t.isEnabled IS NULL OR t.isEnabled = true)")
    List<SeatUserProjection> findAllTicketAndUserBySection(String sectionName);

    Optional<SeatEntity> findBySeatNumberAndSection(Integer seatNumber,String section);
}
