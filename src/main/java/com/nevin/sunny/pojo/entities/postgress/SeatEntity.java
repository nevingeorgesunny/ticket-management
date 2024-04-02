package com.nevin.sunny.pojo.entities.postgress;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 9:58 pm
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "seat")
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_seq_generator")
    @SequenceGenerator(name = "entity_seq_generator", sequenceName = "entity_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long seatId;

    @Column(name = "section", nullable = false)
    private String section;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    @Column(name = "is_taken", nullable = false)
    private Boolean isTaken = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private TicketEntity ticket;
}
