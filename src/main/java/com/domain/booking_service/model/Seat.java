package com.domain.booking_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seatNumber;
    private Long showId;
    private String status;

    public Seat() {}

    public Seat(Long id, String seatNumber, Long showId, String status) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.showId = showId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public Long getShowId() {
        return showId;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) { this.id = id; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public void setShowId(Long showId) { this.showId = showId; }
    public void setStatus(String status) {
        this.status = status;
    }
}
