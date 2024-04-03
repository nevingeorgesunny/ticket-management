package com.nevin.sunny.service;

import com.nevin.sunny.dao.ISeatDao;
import com.nevin.sunny.dao.IUserDao;
import com.nevin.sunny.pojo.entities.postgress.SeatEntity;
import com.nevin.sunny.pojo.entities.postgress.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 9:53 pm
 */
@Component
@Slf4j
public class DataInitlizer implements ApplicationRunner {

    private final IUserDao userDao;

    private final ISeatDao seatDao;

    private static final String SECTION_A = "A";
    private static final String SECTION_B = "B";

    public DataInitlizer(IUserDao userDao, ISeatDao seatDao) {
        this.userDao = userDao;
        this.seatDao = seatDao;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {


        userDao.save(UserEntity.builder()
                .id(UUID.randomUUID())
                .email("nevin.sunny@gmail.com")
                .firstName("Nevin")
                .lastName("Sunny")
                .build());

        userDao.save(UserEntity.builder()
                .id(UUID.randomUUID())
                .email("user.one@gmail.com")
                .firstName("User")
                .lastName("One")
                .build());

        log.info("User data created");

        List<SeatEntity> seats = List.of(

                SeatEntity.builder()
                        .section(SECTION_A)
                        .seatNumber(1)
                        .isTaken(false)
                        .build(),

                SeatEntity.builder()
                        .section(SECTION_A)
                        .seatNumber(2)
                        .isTaken(false)
                        .build(),

                SeatEntity.builder()
                        .section(SECTION_A)
                        .seatNumber(3)
                        .isTaken(false)
                        .build(),

                SeatEntity.builder()
                        .section(SECTION_A)
                        .seatNumber(4)
                        .isTaken(false)
                        .build(),
                SeatEntity.builder()
                        .section(SECTION_B)
                        .seatNumber(1)
                        .isTaken(false)
                        .build(),
                SeatEntity.builder()
                        .section(SECTION_B)
                        .seatNumber(2)
                        .isTaken(false)
                        .build(),

                SeatEntity.builder()
                        .section(SECTION_B)
                        .seatNumber(3)
                        .isTaken(false)
                        .build(),

                SeatEntity.builder()
                        .section(SECTION_B)
                        .seatNumber(4)
                        .isTaken(false)
                        .build()
        );

        seatDao.saveAll(seats);

        log.info("Seat data created");

    }
}
