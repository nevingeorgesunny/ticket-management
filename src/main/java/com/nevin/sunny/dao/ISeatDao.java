package com.nevin.sunny.dao;

import com.nevin.sunny.pojo.entities.postgress.SeatEntity;

import java.util.List;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 10:00 pm
 */
public interface ISeatDao {
    List<SeatEntity> save(List<SeatEntity> seatEntities);
}
