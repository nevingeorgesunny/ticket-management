package com.nevin.sunny.service.impl;

import com.nevin.sunny.dao.ISeatDao;
import com.nevin.sunny.dao.ITicketDao;
import com.nevin.sunny.dao.IUserDao;
import com.nevin.sunny.exception.TicketException;
import com.nevin.sunny.pojo.communicators.request.BookTicketRequest;
import com.nevin.sunny.pojo.communicators.request.UpdateTicketRequest;
import com.nevin.sunny.pojo.communicators.response.BookTicketResponse;
import com.nevin.sunny.pojo.communicators.response.FetchAllSectionResponse;
import com.nevin.sunny.pojo.communicators.response.FetchTicketReceiptResponse;
import com.nevin.sunny.pojo.context.SeatContext;
import com.nevin.sunny.pojo.context.TicketContext;
import com.nevin.sunny.pojo.context.UserContext;
import com.nevin.sunny.pojo.entities.postgress.SeatEntity;
import com.nevin.sunny.pojo.entities.postgress.TicketEntity;
import com.nevin.sunny.pojo.entities.postgress.UserEntity;
import com.nevin.sunny.pojo.projection.SeatUserProjection;
import com.nevin.sunny.service.ITicketService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 10:20 pm
 */
@Slf4j
@Service
public class TicketService implements ITicketService {

    private final IUserDao userDao;
    private final ISeatDao seatDao;

    private final ITicketDao ticketDao;

    public TicketService(IUserDao userDao, ISeatDao seatDao, ITicketDao ticketDao) {
        this.userDao = userDao;
        this.seatDao = seatDao;
        this.ticketDao = ticketDao;
    }


    @Override
    @Transactional
    public BookTicketResponse bookTicket(BookTicketRequest request) throws TicketException {

        SeatEntity seatEntity = seatDao.fetchSeatIfAvailable();
        if (seatEntity == null) {
            throw new TicketException(
                    String.format(
                            "No seats available"
                    )
            );
        }

        UserEntity userEntity = fetchUserEntityForBookingTicket(request);

        TicketEntity ticketEntity = TicketEntity.builder()
                .seatId(seatEntity.getSeatId())
                .id(UUID.randomUUID())
                .userId(userEntity.getId())
                .pricePaid(request.getPricePaid())
                .from(request.getFrom())
                .to(request.getTo())
                .isEnabled(true)
                .build();

        //Save the ticket
        ticketDao.save(ticketEntity);

        seatEntity.setIsTaken(true);
        //Update the ticket as taken
        seatDao.save(seatEntity);

        return BookTicketResponse.builder()
                .userId(userEntity.getId())
                .seatNumber(seatEntity.getSeatNumber())
                .section(seatEntity.getSection())
                .ticketId(ticketEntity.getId())
                .from(ticketEntity.getFrom())
                .to(ticketEntity.getTo())
                .pricePaid(ticketEntity.getPricePaid())
                .build();
    }

    @Override
    public FetchTicketReceiptResponse viewUserTicketReceipt(UUID userId) throws TicketException {
        //check if user exists
        userDao.findById(userId);

        List<TicketEntity> tickets = ticketDao.findAllTicketsForUser(userId);
        Map<Long, SeatEntity> seatIdMap = seatDao.findAllById(tickets
                        .stream()
                        .map(TicketEntity::getSeatId).collect(Collectors.toList())).stream()
                .collect(Collectors.toMap(SeatEntity::getSeatId, Function.identity()));


        return FetchTicketReceiptResponse.builder()
                .userId(userId)
                .ticketList(tickets.stream().map(ticket -> {
                    SeatEntity seat = seatIdMap.get(ticket.getSeatId());
                    return TicketContext.builder()
                            .ticketId(ticket.getId())
                            .section(seat.getSection())
                            .seatNumber(seat.getSeatNumber())
                            .amountPaid(ticket.getPricePaid())
                            .build();
                }).collect(Collectors.toList()))
                .build();

    }

    private UserEntity fetchUserEntityForBookingTicket(BookTicketRequest request) throws TicketException {
        UserEntity userEntity;
        if (request.getUserId() != null) {
            log.info("Fetching user with Id : {}", request.getUserId());
            userEntity = userDao.findById(request.getUserId());
        } else {

            if (userDao.findByEmail(request.getUserInfo().getEmail()).isPresent()) {
                throw new TicketException(
                        String.format(
                                "User with email %s already exist", request.getUserInfo().getEmail()
                        )
                );
            }

            userEntity = userDao.save(UserEntity.builder()
                    .id(UUID.randomUUID())
                    .lastName(request.getUserInfo().getLastName())
                    .firstName(request.getUserInfo().getFirstName())
                    .email(request.getUserInfo().getEmail())
                    .build());

            log.info("Created new user with id : {}", userEntity.getId());
        }
        return userEntity;
    }

    @Override
    @Transactional
    public Boolean updateTicket(UpdateTicketRequest request) throws TicketException {
        TicketEntity ticketEntity = ticketDao.findById(request.getTicketId());
        SeatEntity bookedSeat = seatDao.findById(ticketEntity.getSeatId());

        if (Boolean.FALSE.equals(request.getIsEnabled())) {
            log.info("Deleting ticket with Id : {}", request.getTicketId());

            bookedSeat.setIsTaken(false);

            seatDao.saveAll(List.of(bookedSeat));
            ticketDao.deleteAll(List.of(ticketEntity));
        } else {
            log.info("Updating ticket with : {}", request);
            SeatEntity newSeat = seatDao
                    .findBySeatNumberAndSection(request.getNewSeatNumber(), request.getNewSection());

            bookedSeat.setIsTaken(false);
            newSeat.setIsTaken(true);

            ticketEntity.setSeatId(newSeat.getSeatId());

            seatDao.saveAll(List.of(newSeat, bookedSeat));
            ticketDao.save(ticketEntity);
        }

        return true;
    }


    @Override
    @Transactional
    public Boolean removeUserFromTrain(UUID userId) {
        List<TicketEntity> tickets = ticketDao.findAllTicketsForUser(userId);

        List<Long> seatId = tickets.stream().map(t -> t.getSeatId()).collect(Collectors.toList());

        List<SeatEntity> seats = seatDao.findAllById(seatId);

        seats.forEach(s -> s.setIsTaken(false));

        seatDao.saveAll(seats);

        ticketDao.deleteAll(tickets);

        return true;
    }

    @Override
    public FetchAllSectionResponse viewAllTicketForAllSection(String section) {
        List<SeatUserProjection> allBySection = seatDao.findAllBySection(section);

        return FetchAllSectionResponse.builder()
                .seats(allBySection.stream()
                        .map(s -> SeatContext.builder()
                                .isTaken(s.getIsTaken())
                                .seatId(s.getSeatId())
                                .seatNumber(s.getSeatNumber())
                                .userInfo(s.getIsTaken() ? UserContext.builder()
                                        .userId(s.getUserId())
                                        .firstName(s.getFirstName())
                                        .lastName(s.getLastName())
                                        .email(s.getEmail())
                                        .build() : null)
                                .ticketId(s.getIsTaken() ? s.getTicketId() : null)
                                .section(s.getSection())
                                .build()).collect(Collectors.toList()))
                .build();

    }
}
