package com.nevin.sunny.dao.impl;

import com.nevin.sunny.dao.ISeatDao;
import com.nevin.sunny.exception.TicketException;
import com.nevin.sunny.pojo.entities.postgress.SeatEntity;
import com.nevin.sunny.pojo.projection.SeatUserProjection;
import com.nevin.sunny.repositories.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 10:01 pm
 */
@Service
public class SeatDao implements ISeatDao {
    private final SeatRepository seatRepository;

    public SeatDao(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<SeatEntity> saveAll(List<SeatEntity> seatEntities) {
        return seatRepository.saveAll(seatEntities);
    }

    @Override
    public SeatEntity save(SeatEntity seatEntity) {
        return seatRepository.save(seatEntity);
    }

    @Override
    public SeatEntity fetchSeatIfAvailable() {
        return seatRepository.findTopByIsTakenFalse();
    }

    @Override
    public List<SeatEntity> findAllById(List<Long> seatId) {
        return seatRepository.findAllById(seatId);
    }

    @Override
    public SeatEntity findById(Long seatId) throws TicketException {
        return seatRepository.findById(seatId).orElseThrow(() -> new TicketException(
                String.format("Seat with Id : % not found", seatId)
        ));
    }

    @Override
    public List<SeatUserProjection> findAllBySection(String section) {
        return seatRepository.findAllTicketAndUserBySection(section);
    }

    @Override
    public SeatEntity findBySeatNumberAndSection(Integer seatNumber, String section) throws TicketException {
        return seatRepository.findBySeatNumberAndSection(seatNumber,section).orElseThrow(() -> new TicketException(
                String.format("No ticket with seat number : %i and section : %s ", seatNumber,seatNumber)
        ));
    }
}
