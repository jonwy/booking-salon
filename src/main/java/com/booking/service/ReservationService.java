package com.booking.service;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReservationService {

    private static final Scanner input = MenuService.input;

    public static void createReservation(List<Reservation> reservations, List<Person> persons, List<Service>serviceList){
        PrintService.showAllCustomer(persons);
        Customer customer = selectCustomer(persons);
        
        PrintService.showAllEmployee(persons);
        Employee employee = selectEmployee(persons);

        PrintService.printServiceList(serviceList);
        List<Service> customerServiceChoices = new ArrayList<>();
        char addOtherServiceAgain = 'Y';
        do {
            Service service = selectService(serviceList);
            if (isCustomerServiceChoiceFull(serviceList, customerServiceChoices)) {
                System.out.println("Anda telah memilih semua service yang tersedia.");
                break;
            }

            if (!customerServiceChoices.contains(service)) {
                customerServiceChoices.add(service);
            }

            boolean isServiceAdded = !customerServiceChoices.isEmpty();
            
            if (isServiceAdded) {
                System.out.println("Ingin pilih service yang lain? Y/T");
                addOtherServiceAgain = input.nextLine().toUpperCase().charAt(0);
                if (addOtherServiceAgain == 'T') {
                    System.out.println("Selesai memilih service");
                    break;
                }
                else if (addOtherServiceAgain != 'Y'){
                    System.out.println("Input tidak sesuai.\nUser dianggap telah selesai memilih service.");
                    break;
                }
            }
            else {
                System.out.println("Service tidak ditemukan. Silahkan pilih service yang sesuai");
                continue;
            }
            
        } while (addOtherServiceAgain == 'Y' || customerServiceChoices.isEmpty());
        
        double reservationPrice = calculateReservationPrice(customer.getMember().getMembershipName(), customerServiceChoices);
        Reservation reservation = Reservation.builder()
        .reservationId(Reservation.generateId())
        .customer(customer)
        .employee(employee)
        .services(customerServiceChoices)
        .workstage("In Process")
        .reservationPrice(reservationPrice)
        .build();
        reservations.add(reservation);
    }

    public static Customer getCustomerByCustomerId(String id, List<Person> persons){
        return (Customer)persons.stream()
            .filter(person -> person.getId().equalsIgnoreCase(id))
            .findFirst().orElse(null);
    }

    public static Employee getEmployeeByEmployeeId(String id, List<Person> persons) {
        return (Employee)persons.stream()
            .filter(person -> person.getId().equalsIgnoreCase(id))
            .findFirst().orElse(null);
    }

    public static Service getServiceByServiceId(String id, List<Service> serviceList) {
        return serviceList.stream()
            .filter(service -> service.getServiceId().equalsIgnoreCase(id))
            .findFirst().orElse(null);
    }

    public static Reservation getReservationByReservationId(String id, List<Reservation> reservationList) {
        return reservationList.stream()
            .filter(reservation -> reservation.getReservationId().equalsIgnoreCase(id))
            .findFirst().orElse(null);
    }

    private static Customer selectCustomer(List<Person> persons) {
        Customer customer = null;
        do {
            System.out.print("Masukkan Customer Id: ");
            String customerId = input.nextLine();
            customer = getCustomerByCustomerId(customerId, persons);
            if (customer == null) {
                ValidationService.printErrorDataIsNull("Customer");
            }
        } while (customer == null);
        return customer;
    }

    private static Employee selectEmployee(List<Person> persons) {
        Employee employee = null;
        do {
            System.out.print("Pilih employee.\nMasukkan employee id: ");
            String employeeId = input.nextLine();
            employee = getEmployeeByEmployeeId(employeeId, persons);
            if (employee == null) {
                ValidationService.printErrorDataIsNull("Employee");
            }
        } while (employee == null);
        return employee;
    }

    private static Service selectService(List<Service> serviceList) {
        Service service = null;
        do {
            System.out.println("Pilih service");
            System.out.print("Masukkan service id: ");
            String serviceId = input.nextLine();
            service = getServiceByServiceId(serviceId, serviceList);
            if (service == null) {
                System.out.println("Service tidak ditemukan");
                continue;
            }
        } while (service == null);
        return service;
    }

    /*
     * status == 0, cancel
     * status == 1, finish
     */
    public static void editReservationWorkstage(List<Reservation> reservationList) {
        System.out.print("Update Reservasi Status\n");
        List<Reservation> inProcessReservations = 
            reservationList.stream()
                .filter(reservation -> reservation.getWorkstage().equalsIgnoreCase("in process"))
                .collect(Collectors.toList());
        if (inProcessReservations.isEmpty()) {
            System.out.println("Reservasi dengan status \"in process\" kosong");
            return;
        } 

        PrintService.showRecentReservation(reservationList);
        Reservation reservation = null;
        do {
            System.out.print("Masukkan reservasi ID: ");
            String reservationId = input.nextLine();
            reservation = getReservationByReservationId(reservationId, inProcessReservations);
            if (reservation == null) {
                ValidationService.printErrorDataIsNull("Reservasi");
            }
        } while (reservation == null);
        
        System.out.println("Pilih Status");
        System.out.println("1. Completed");
        System.out.println("2. Cancel");
        int status = 0;

        boolean exit = false;
        do {
            System.out.print("Choice: ");
            status = ValidationService.isInputNumber(input.nextLine());
            exit = status != -1 && ValidationService.workstageStatusChangeValidate(status);
        } while (!exit);

        if (status == 1) {
            double reservationPrice = reservation.getReservationPrice();
            double customerCurrWallet = reservation.getCustomer().getWallet();
            if (reservationPrice > customerCurrWallet) {
                System.out.println("Status gagal diperbaharui karena wallet tidak cukup");
                return;
            }
            reservation.setWorkstage("Finish");
            reservation.getCustomer().setWallet(customerCurrWallet - reservationPrice);
        }
        else if (status == 2) {
            reservation.setWorkstage("Cancel");
        }
        System.out.println("Data berhasil diperbaharui");
    }

    private static boolean isCustomerServiceChoiceFull(List<Service> serviceList, List<Service> customerServiceChoice) {
        return serviceList.size() == customerServiceChoice.size();
    }

    private static double calculateReservationPrice (String membershipType, List<Service> customerServiceChoices) {
        double total = 0;
        float discoutPercentage;
        switch (membershipType.toUpperCase()) {
            case "NONE":
                discoutPercentage = 0;
                break;
            case "SILVER":
                discoutPercentage = 0.05f;
                break;
            case "GOLD":
                discoutPercentage = 0.1f;
                break;
            default:
                discoutPercentage = 0;
                break;
        }
        for (Service service: customerServiceChoices) {
            total += service.getPrice();
        }
        return total - (total * discoutPercentage);
    }
}
