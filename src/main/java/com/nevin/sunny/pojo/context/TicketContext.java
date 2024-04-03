package com.nevin.sunny.pojo.context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

/**
 * @author nevinsunny
 * date 03/04/24
 * time 6:39 am
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketContext {
    private UUID ticketId;
    private String section;
    private Integer seatNumber;
}
