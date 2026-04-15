package com.domain.booking_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    @ManyToOne
    private Show show;
    @ManyToMany
    private List<Seat> seats;
    private String status;
    private Double totalPrice;
    /**
     * All-args constructor for Booking entity.
     * @param id Booking ID
     * @param userName User name
     * @param show Show entity
     * @param seats List of Seat entities
     * @param status Booking status
     * @param totalPrice Total price for the booking
     */
    public Booking(Long id, String userName, Show show, List<Seat> seats, String status, Double totalPrice) {
        this.id = id; this.userName = userName; this.show = show; this.seats = seats; this.status = status; this.totalPrice = totalPrice;
    }

    /**
     * No-args constructor for Booking entity.
     */
    public Booking() {}

}
