package com.nevin.sunny.advice;

import com.nevin.sunny.exception.TicketException;
import com.nevin.sunny.pojo.communicators.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nevinsunny
 * date 24/03/24
 * time 3:42 pm
 */

@Slf4j
@ControllerAdvice
@RestController
@Order(0)
public class TicketControllerAdvice {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(TicketException.class)
    public BaseResponse lotteryTicketException(TicketException exception) {
        log.error("TicketException thrown", exception);
        return BaseResponse.builder()
                .code(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();
    }
}
