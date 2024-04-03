package com.nevin.sunny.pojo.entities.postgress;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 9:10 pm
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticket")
public class TicketEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "from_location", nullable = false)
    private String from;

    @Column(name = "to_location", nullable = false)
    private String to;

    @Column(name = "price_paid", nullable = false)
    private Integer pricePaid;

    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;

}