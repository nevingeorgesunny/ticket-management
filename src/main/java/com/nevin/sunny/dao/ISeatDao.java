package com.nevin.sunny.dao;

import com.nevin.sunny.exception.TicketException;
import com.nevin.sunny.pojo.entities.postgress.SeatEntity;
import com.nevin.sunny.pojo.projection.SeatUserProjection;

import java.util.List;
import java.util.Optional;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 10:00 pm
 */
public interface ISeatDao {
    List<SeatEntity> saveAll(List<SeatEntity> seatEntities);

    SeatEntity save(SeatEntity seatEntity);

    SeatEntity fetchSeatIfAvailable();

    List<SeatEntity> findAllById(List<Long> seatId);

    SeatEntity findById(Long seatId) throws TicketException;

    List<SeatUserProjection> findAllBySection(String section);

    SeatEntity findBySeatNumberAndSection(Integer seatNumber, String section) throws TicketException;
}
