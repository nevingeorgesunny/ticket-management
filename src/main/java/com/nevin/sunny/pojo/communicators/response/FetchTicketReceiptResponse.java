package com.nevin.sunny.pojo.communicators.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nevin.sunny.pojo.context.TicketContext;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * @author nevinsunny
 * date 03/04/24
 * time 6:37 am
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FetchTicketReceiptResponse {
    private UUID userId;
    private String from;
    private String  to;

    private List<TicketContext> ticketList;

}
