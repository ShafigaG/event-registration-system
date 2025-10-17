package com.shafigag.event_reg.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "events")
@Data
public class Event extends BaseEntity{
    @Column(name = "event_name", nullable = false)
    private String name;

    @Column(nullable = false, length = 150)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Instant startingAt;

    @Column(nullable = false)
    private Instant endingAt;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Integer attendance = 0;

    @Column(nullable = false)
    private Double ticketPrice;

}
