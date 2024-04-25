package com.booking.service;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Reservation;
import com.booking.models.Service;

import java.util.List;
import java.util.Set;

public class ReservationService {
    public static void createReservation(List<Reservation> reservations, Customer customer, Employee employee, List<Service> customerServiceChoices){
        Reservation reservation = Reservation.builder()
        .customer(customer)
        .employee(employee)
        .services(customerServiceChoices)
        .build();
        reservations.add(reservation);
    }

    public static void getCustomerByCustomerId(){
        
    }

    public static void editReservationWorkstage(){
        
    }

}
