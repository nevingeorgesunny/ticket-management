package com.nevin.sunny.service;

import com.nevin.sunny.exception.TicketException;
import com.nevin.sunny.pojo.communicators.request.BookTicketRequest;
import com.nevin.sunny.pojo.communicators.request.UpdateTicketRequest;
import com.nevin.sunny.pojo.communicators.response.BookTicketResponse;
import com.nevin.sunny.pojo.communicators.response.FetchAllSectionResponse;
import com.nevin.sunny.pojo.communicators.response.FetchTicketReceiptResponse;

import java.util.UUID;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 10:13 pm
 */
public interface ITicketService {

    BookTicketResponse bookTicket(BookTicketRequest request) throws TicketException;

    Boolean updateTicket(UpdateTicketRequest request) throws TicketException;

    FetchTicketReceiptResponse viewUserTicketReceipt(UUID userId) throws TicketException;

    Boolean removeUserFromTrain(UUID userId);

    FetchAllSectionResponse viewAllTicketForAllSection(String section);

}
