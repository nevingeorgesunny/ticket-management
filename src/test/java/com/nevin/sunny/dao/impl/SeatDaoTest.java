package com.nevin.sunny.dao.impl;

import com.nevin.sunny.exception.TicketException;
import com.nevin.sunny.pojo.entities.postgress.SeatEntity;
import com.nevin.sunny.repositories.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 10:01 pm
 */

public class SeatDaoTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatDao seatDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAll() {
        List<SeatEntity> seatEntities = Arrays.asList(new SeatEntity(), new SeatEntity());
        when(seatRepository.saveAll(anyList())).thenReturn(seatEntities);

        List<SeatEntity> returnedSeats = seatDao.saveAll(seatEntities);

        assertEquals(seatEntities.size(), returnedSeats.size());
        verify(seatRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testFetchSeatIfAvailable() {
        SeatEntity seatEntity = new SeatEntity();
        when(seatRepository.findTopByIsTakenFalse()).thenReturn(seatEntity);

        SeatEntity returnedSeat = seatDao.fetchSeatIfAvailable();

        assertEquals(seatEntity, returnedSeat);
        verify(seatRepository, times(1)).findTopByIsTakenFalse();
    }

    @Test
    public void testFindAllById() {
        List<SeatEntity> seatEntities = Arrays.asList(new SeatEntity(), new SeatEntity());
        List<Long> ids = Arrays.asList(1L, 2L);
        when(seatRepository.findAllById(anyList())).thenReturn(seatEntities);

        List<SeatEntity> returnedSeats = seatDao.findAllById(ids);

        assertEquals(seatEntities.size(), returnedSeats.size());
        verify(seatRepository, times(1)).findAllById(anyList());
    }

    @Test
    public void testFindById_Success() throws TicketException {
        Long id = 1L;
        SeatEntity seatEntity = new SeatEntity();
        when(seatRepository.findById(id)).thenReturn(Optional.of(seatEntity));

        SeatEntity returnedSeat = seatDao.findById(id);

        assertEquals(seatEntity, returnedSeat);
    }
}
