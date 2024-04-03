package com.nevin.sunny.pojo.communicators.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nevin.sunny.pojo.context.UserContext;
import lombok.*;

import java.util.UUID;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 10:20 pm
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookTicketRequest {
    private UUID userId;
    private String from;
    private String to;
    private Integer pricePaid;
    private UserContext userInfo;
}
