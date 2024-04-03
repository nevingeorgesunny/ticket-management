package com.nevin.sunny.dao.impl;

import com.nevin.sunny.dao.ITicketDao;
import com.nevin.sunny.exception.TicketException;
import com.nevin.sunny.pojo.entities.postgress.TicketEntity;
import com.nevin.sunny.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 10:56 pm
 */
@Service
public class TicketDao implements ITicketDao {
    private final TicketRepository ticketRepository;

    public TicketDao(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketEntity save(TicketEntity ticketEntity) {
        return ticketRepository.save(ticketEntity);
    }

    @Override
    public List<TicketEntity> saveAll(List<TicketEntity> ticketEntities) {
        return ticketRepository.saveAll(ticketEntities);
    }

    @Override
    public void deleteAll(List<TicketEntity> ticketEntities) {
        ticketRepository.deleteAll(ticketEntities);
    }

    @Override
    public List<TicketEntity> findAllTicketsForUser(UUID userId) {
        return ticketRepository.findAllByUserIdAndIsEnabledTrue(userId);
    }

    @Override
    public TicketEntity findById(UUID ticketId) throws TicketException {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new TicketException(
                String.format("Ticket with id : %s", ticketId)
        ));
    }
}
