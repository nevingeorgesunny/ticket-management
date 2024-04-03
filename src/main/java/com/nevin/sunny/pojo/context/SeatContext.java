package com.nevin.sunny.pojo.context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

/**
 * @author nevinsunny
 * date 03/04/24
 * time 9:51 am
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatContext {
    private Long seatId;
    private String section;
    private Integer seatNumber;
    private UUID ticketId;
    private String from;
    private String to;
    private UserContext userInfo;
    private Boolean isTaken = false;
}
