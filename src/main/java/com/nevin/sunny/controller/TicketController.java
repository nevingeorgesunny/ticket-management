package com.nevin.sunny.controller;

import com.nevin.sunny.exception.TicketException;
import com.nevin.sunny.pojo.communicators.request.BookTicketRequest;
import com.nevin.sunny.pojo.communicators.request.UpdateTicketRequest;
import com.nevin.sunny.pojo.communicators.response.BaseResponse;
import com.nevin.sunny.service.ITicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 10:58 pm
 */

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class TicketController {

    private final ITicketService ticketService;

    public TicketController(ITicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/book-ticket")
    public ResponseEntity<BaseResponse> createTicket(@RequestBody BookTicketRequest request)
            throws TicketException {
        return ResponseEntity.ok(BaseResponse.builder()
                .data(ticketService.bookTicket(request))
                .code(HttpStatus.OK)
                .build());
    }

    @GetMapping("/ticket/{userId}")
    public ResponseEntity<BaseResponse> getAllUserTickets(@PathVariable(value = "userId") UUID userId)
            throws TicketException {
        return ResponseEntity.ok(BaseResponse.builder()
                .data(ticketService.viewUserTicketReceipt(userId))
                .code(HttpStatus.OK)
                .build());
    }

    @GetMapping("/fetch/{section}")
    public ResponseEntity<BaseResponse> getAllUserTickets(@PathVariable(value = "section") String section) {
        return ResponseEntity.ok(BaseResponse.builder()
                .data(ticketService.viewAllTicketForAllSection(section))
                .code(HttpStatus.OK)
                .build());
    }


    @DeleteMapping("/ticket/{userId}")
    public ResponseEntity<BaseResponse> deleteAllUserTicket(@PathVariable(value = "userId") UUID userId) {
        return ResponseEntity.ok(BaseResponse.builder()
                .data(ticketService.removeUserFromTrain(userId))
                .code(HttpStatus.OK)
                .build());
    }

    @PutMapping("/ticket/{ticketId}")
    public ResponseEntity<BaseResponse> updateTicket(@RequestBody UpdateTicketRequest request)
            throws TicketException {
        return ResponseEntity.ok(BaseResponse.builder()
                .data(ticketService.updateTicket(request))
                .code(HttpStatus.OK)
                .build());
    }
}
