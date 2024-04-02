package com.nevin.sunny.dao.impl;

import com.nevin.sunny.dao.ISeatDao;
import com.nevin.sunny.pojo.entities.postgress.SeatEntity;
import com.nevin.sunny.repositories.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<SeatEntity> save(List<SeatEntity> seatEntities) {
        return seatRepository.saveAll(seatEntities);
    }
}
