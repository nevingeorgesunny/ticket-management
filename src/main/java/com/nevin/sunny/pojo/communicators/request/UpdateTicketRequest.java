package com.nevin.sunny.pojo.communicators.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nevin.sunny.pojo.communicators.response.FetchAllSectionResponse;
import lombok.*;

import java.util.UUID;

/**
 * @author nevinsunny
 * date 03/04/24
 * time 3:00 pm
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTicketRequest {
    private UUID userId;
    private UUID ticketId;
    private Boolean isEnabled;
    private String newSection;
    private Integer newSeatNumber;
}
