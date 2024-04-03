package com.nevin.sunny.pojo.communicators.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nevin.sunny.pojo.context.SeatContext;
import lombok.*;

import java.util.List;

/**
 * @author nevinsunny
 * date 03/04/24
 * time 9:52 am
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FetchAllSectionResponse {
    List<SeatContext> seats;
}
