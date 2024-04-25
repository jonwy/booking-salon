package com.booking.models;

import java.util.List;

import com.booking.util.IDGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private static int index = 1;

    private String reservationId;
    private Customer customer;
    private Employee employee;
    private List<Service> services;
    private double reservationPrice;
    private String workstage;
    //   workStage (In Process, Finish, Canceled)

    public Reservation() {
        reservationId = generateId();
    }

    public Reservation(Customer customer, Employee employee, List<Service> services,
            String workstage) {
        this.reservationId = IDGenerator.generateID("Rsv", index++);
        this.customer = customer;
        this.employee = employee;
        this.services = services;
        this.reservationPrice = calculateReservationPrice();
        this.workstage = workstage;
    };

    private double calculateReservationPrice(){
        return 0;
    }

    public static String generateId() {
        return IDGenerator.generateID("Rsv", index++);
    }
}
