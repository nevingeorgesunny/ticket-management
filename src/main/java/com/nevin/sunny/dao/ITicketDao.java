package com.nevin.sunny.dao;

import com.nevin.sunny.exception.TicketException;
import com.nevin.sunny.pojo.entities.postgress.TicketEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 9:12 pm
 */
public interface ITicketDao {
    TicketEntity save(TicketEntity ticketEntity);

    List<TicketEntity> saveAll(List<TicketEntity> ticketEntities);

    void deleteAll(List<TicketEntity> ticketEntities);

    List<TicketEntity> findAllTicketsForUser(UUID userId);

    TicketEntity findById(UUID ticketId) throws TicketException;
}
