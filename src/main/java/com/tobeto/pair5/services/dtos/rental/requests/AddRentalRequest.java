package com.tobeto.pair5.services.dtos.rental.requests;

import java.time.LocalDate;

public class AddRentalRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate returnDate;
    private int startKilometer;
    private int endKilometer;
    private double totalPrice;

}
