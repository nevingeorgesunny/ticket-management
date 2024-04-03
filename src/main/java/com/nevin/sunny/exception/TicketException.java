package com.nevin.sunny.exception;

/**
 * @author nevinsunny
 * date 24/03/24
 * time 3:28 pm
 */
public class TicketException extends Exception{
    public TicketException() {
        super("Ticket Exception");
    }

    public TicketException(String message) {
        super(message);
    }
}
