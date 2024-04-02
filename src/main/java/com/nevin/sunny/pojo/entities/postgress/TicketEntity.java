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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticket")
public class TicketEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "from_location", nullable = false)
    private String from;

    @Column(name = "to_location", nullable = false)
    private String to;

    @Column(name = "price_paid", nullable = false)
    private Double pricePaid;

    @Column(name = "seat_section", nullable = false)
    private String seatSection;

}