package com.nevin.sunny.impl;


import com.nevin.sunny.dao.ISeatDao;
import com.nevin.sunny.dao.ITicketDao;
import com.nevin.sunny.dao.IUserDao;
import com.nevin.sunny.exception.TicketException;
import com.nevin.sunny.pojo.communicators.request.BookTicketRequest;
import com.nevin.sunny.pojo.communicators.request.UpdateTicketRequest;
import com.nevin.sunny.pojo.communicators.response.BookTicketResponse;
import com.nevin.sunny.pojo.communicators.response.FetchAllSectionResponse;
import com.nevin.sunny.pojo.communicators.response.FetchTicketReceiptResponse;
import com.nevin.sunny.pojo.context.UserContext;
import com.nevin.sunny.pojo.entities.postgress.SeatEntity;
import com.nevin.sunny.pojo.entities.postgress.TicketEntity;
import com.nevin.sunny.pojo.entities.postgress.UserEntity;
import com.nevin.sunny.pojo.projection.SeatUserProjection;
import com.nevin.sunny.service.impl.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketServiceTest {

    @Mock
    private IUserDao userDao;

    @Mock
    private ISeatDao seatDao;

    @Mock
    private ITicketDao ticketDao;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBookTicket_Success() throws TicketException {
        // Setup mock responses
        SeatEntity mockSeat = new SeatEntity(); // Assume necessary setters are called
        UserEntity mockUser = new UserEntity(); // Assume necessary setters are called
        TicketEntity mockTicket = new TicketEntity(); // Assume necessary setters are called
        BookTicketRequest request = new BookTicketRequest(); // Setup your request

        when(seatDao.fetchSeatIfAvailable()).thenReturn(mockSeat);
        when(userDao.findById(any(UUID.class))).thenReturn(mockUser);
        when(ticketDao.save(any(TicketEntity.class))).thenReturn(mockTicket);
        request.setUserId(UUID.randomUUID());

        // Execute the method
        BookTicketResponse response = ticketService.bookTicket(request);

        // Validate the results
        assertNotNull(response);
        verify(seatDao, times(1)).fetchSeatIfAvailable();
        verify(userDao, times(1)).findById(any(UUID.class));
        verify(ticketDao, times(1)).save(any(TicketEntity.class));
        verify(seatDao, times(1)).save(any(SeatEntity.class));
    }

    @Test
    public void testBookTicket_Success_NewUser() throws TicketException {
        // Setup mock responses
        SeatEntity mockSeat = new SeatEntity(); // Assume necessary setters are called
        UserEntity mockUser = new UserEntity(); // Assume necessary setters are called
        TicketEntity mockTicket = new TicketEntity(); // Assume necessary setters are called
        mockTicket.setSeatId(1L);
        BookTicketRequest request = new BookTicketRequest(); // Setup your request

        when(seatDao.fetchSeatIfAvailable()).thenReturn(mockSeat);
        when(userDao.findByEmail(any(String.class))).thenReturn(Optional.ofNullable(null));
        when(userDao.save(any(UserEntity.class))).thenReturn(UserEntity.builder()
                        .id(UUID.randomUUID())
                .build());
        when(ticketDao.save(any(TicketEntity.class))).thenReturn(mockTicket);

        request.setUserInfo(UserContext.builder()
                .email("nev2in.sunny@skillovilla.com")
                .build());

        // Execute the method
        BookTicketResponse response = ticketService.bookTicket(request);

        // Validate the results
        assertNotNull(response);
        verify(seatDao, times(1)).fetchSeatIfAvailable();
        verify(userDao, times(1)).findByEmail(any(String.class));
        verify(userDao, times(1)).save(any(UserEntity.class));
        verify(ticketDao, times(1)).save(any(TicketEntity.class));
        verify(seatDao, times(1)).save(any(SeatEntity.class));
    }

    @Test
    public void testBookTicket_Success_EmailPreset() throws TicketException {
        // Setup mock responses
        SeatEntity mockSeat = new SeatEntity(); // Assume necessary setters are called
        UserEntity mockUser = new UserEntity(); // Assume necessary setters are called
        TicketEntity mockTicket = new TicketEntity(); // Assume necessary setters are called
        mockTicket.setSeatId(1L);
        BookTicketRequest request = new BookTicketRequest(); // Setup your request

        when(seatDao.fetchSeatIfAvailable()).thenReturn(mockSeat);
        when(userDao.findByEmail(any(String.class))).thenReturn(Optional.of(new UserEntity()));

        request.setUserInfo(UserContext.builder()
                .email("nev2in.sunny@skillovilla.com")
                .build());


        assertThrows(TicketException.class, () -> ticketService.bookTicket(request));
    }

    @Test
    public void testBookTicket_NoSeatsAvailable() {
        when(seatDao.fetchSeatIfAvailable()).thenReturn(null);

        BookTicketRequest request = new BookTicketRequest(); // Setup your request

        assertThrows(TicketException.class, () -> ticketService.bookTicket(request));
    }


    @Test
    public void testViewUserTicketReceipt_Success() throws TicketException {
        UUID userId = UUID.randomUUID();
        List<TicketEntity> ticketList = Collections.singletonList(new TicketEntity()); // Setup tickets
        List<SeatEntity> seatList = Collections.singletonList(new SeatEntity()); // Setup seats

        when(userDao.findById(userId)).thenReturn(new UserEntity());
        when(ticketDao.findAllTicketsForUser(userId)).thenReturn(ticketList);
        when(seatDao.findAllById(anyList())).thenReturn(seatList);

        FetchTicketReceiptResponse response = ticketService.viewUserTicketReceipt(userId);

        assertNotNull(response);
        assertFalse(response.getTicketList().isEmpty());
    }

    @Test
    public void testUpdateTicket_DisableTicket() throws TicketException {
        UpdateTicketRequest request = new UpdateTicketRequest(); // Assume setup
        request.setTicketId(UUID.randomUUID());
        request.setIsEnabled(false);
        TicketEntity ticketEntity = new TicketEntity(); // Assume setup
        ticketEntity.setSeatId(1l);
        SeatEntity seatEntity = new SeatEntity(); // Assume setup

        when(ticketDao.findById(any(UUID.class))).thenReturn(ticketEntity);
        when(seatDao.findById(anyLong())).thenReturn(seatEntity);

        Boolean result = ticketService.updateTicket(request);

        assertTrue(result);
        verify(seatDao, times(1)).saveAll(anyList());
        verify(ticketDao, times(1)).save(any(TicketEntity.class));
    }

    @Test
    public void testUpdateTicket_UpdateTicket() throws TicketException {
        UpdateTicketRequest request = new UpdateTicketRequest(); // Assume setup
        request.setTicketId(UUID.randomUUID());
        request.setNewSection("A");
        request.setNewSeatNumber(1);
        TicketEntity ticketEntity = new TicketEntity(); // Assume setup
        ticketEntity.setSeatId(1l);
        SeatEntity seatEntity = new SeatEntity(); // Assume setup
        SeatEntity newSeatEntity = new SeatEntity();

        when(ticketDao.findById(any(UUID.class))).thenReturn(ticketEntity);
        when(seatDao.findById(anyLong())).thenReturn(seatEntity);
        when(seatDao.findBySeatNumberAndSection(anyInt(),anyString())).thenReturn(newSeatEntity);

        Boolean result = ticketService.updateTicket(request);

        assertTrue(result);
        verify(seatDao, times(1)).findBySeatNumberAndSection(anyInt(),anyString());
        verify(seatDao, times(1)).saveAll(anyList());
        verify(ticketDao, times(1)).save(any(TicketEntity.class));
    }

    @Test
    public void testRemoveUserFromTrain_Success() {
        UUID userId = UUID.randomUUID();
        List<TicketEntity> tickets = Arrays.asList(new TicketEntity(), new TicketEntity()); // Assume these have been setup
        List<SeatEntity> seats = Arrays.asList(new SeatEntity(), new SeatEntity()); // Assume these have been setup

        when(ticketDao.findAllTicketsForUser(userId)).thenReturn(tickets);
        when(seatDao.findAllById(anyList())).thenReturn(seats);

        Boolean result = ticketService.removeUserFromTrain(userId);

        assertTrue(result);
        verify(ticketDao, times(1)).findAllTicketsForUser(userId);
        verify(seatDao, times(1)).findAllById(anyList());
        verify(seatDao, times(1)).saveAll(anyList());
        verify(ticketDao, times(1)).saveAll(anyList());
    }

    @Test
    public void testViewAllTicketForAllSection_Success() {
        String section = "A";
        List<SeatUserProjection> projections = Arrays.asList(mock(SeatUserProjection.class), mock(SeatUserProjection.class)); // Setup projections

        when(seatDao.findAllBySection(section)).thenReturn(projections);

        FetchAllSectionResponse response = ticketService.viewAllTicketForAllSection(section);

        assertNotNull(response);
        assertEquals(projections.size(), response.getSeats().size());
        verify(seatDao, times(1)).findAllBySection(section);
    }

    @Test
    public void testViewAllTicketForAllSection_WithUsers() {
        String section = "A";
        SeatUserProjection seatUserProjection = new SeatUserProjection() {
            @Override
            public Long getSeatId() {
                return 1L;
            }

            @Override
            public String getSection() {
                return "A";
            }

            @Override
            public Integer getSeatNumber() {
                return 1;
            }

            @Override
            public Boolean getIsTaken() {
                return true;
            }

            @Override
            public UUID getTicketId() {
                return UUID.randomUUID();
            }

            @Override
            public UUID getUserId() {
                return UUID.randomUUID();
            }

            @Override
            public String getFromLocation() {
                return "London";
            }

            @Override
            public String getToLocation() {
                return "France";
            }

            @Override
            public Integer getPricePaid() {
                return 123;
            }

            @Override
            public String getFirstName() {
                return "A";
            }

            @Override
            public String getLastName() {
                return "b";
            }

            @Override
            public String getEmail() {
                return "a.b@gmail.com";
            }
        };
        List<SeatUserProjection> projections = List.of(seatUserProjection); // Setup projections


        when(seatDao.findAllBySection(section)).thenReturn(projections);

        FetchAllSectionResponse response = ticketService.viewAllTicketForAllSection(section);

        assertNotNull(response);
        assertEquals(projections.size(), response.getSeats().size());
        verify(seatDao, times(1)).findAllBySection(section);
    }



}