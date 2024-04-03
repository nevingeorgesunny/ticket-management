package com.nevin.sunny.pojo.projection;

import java.util.UUID;

/**
 * @author nevinsunny
 * date 03/04/24
 * time 12:36 pm
 */
public interface SeatUserProjection {
    Long getSeatId();
    String getSection();
    Boolean getIsEnabled();
    Integer getSeatNumber();
    Boolean getIsTaken();

    // From TicketEntity
    UUID getTicketId();
    UUID getUserId(); // To correlate with UserEntity
    String getFromLocation();
    String getToLocation();
    Integer getPricePaid();

    // From UserEntity
    String getFirstName();
    String getLastName();
    String getEmail();
}
