package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;

public class MenuService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    private static List<Reservation> reservationList = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);

    public static void mainMenu() {
        String[] mainMenuArr = {"Show Data", "Create Reservation", "Complete/cancel reservation", "Exit"};
        String[] subMenuArr = {"Recent Reservation", "Show Customer", "Show Available Employee", "Back to main menu"};
    
        int optionMainMenu;
        int optionSubMenu;

		boolean backToMainMenu = false;
        boolean backToSubMenu = false;
        do {
            PrintService.printMenu("Main Menu", mainMenuArr);
            optionMainMenu = Integer.valueOf(input.nextLine());
            switch (optionMainMenu) {
                case 1:
                    do {
                        PrintService.printMenu("Show Data", subMenuArr);
                        optionSubMenu = Integer.valueOf(input.nextLine());
                        // Sub menu - menu 1
                        switch (optionSubMenu) {
                            case 1:
                                showAllRecentReservation();
                                break;
                            case 2:
                                showAllCustomer();
                                break;
                            case 3:
                                showAllEmployee();
                                break;
                            case 4:
                                // panggil fitur tampilkan history reservation + total keuntungan
                                break;
                            case 0:
                                backToSubMenu = true;
                        }
                    } while (!backToSubMenu);
                    break;
                case 2:
                    addReservation();
                    break;
                case 3:
                    // panggil fitur mengubah workstage menjadi finish/cancel
                    break;
                case 0:
                    backToMainMenu = true;
                    break;
            }
        } while (!backToMainMenu);
	}

    private static void showAllRecentReservation() {
        PrintService.showRecentReservation(reservationList);
    }

    private static void showAllCustomer() {
        PrintService.showAllCustomer(personList);
    }

    private static void showAllEmployee() {
        PrintService.showAllEmployee(personList);
    }

    private static void addReservation() {
        showAllCustomer();
        Customer customer = selectCustomer();
        
        showAllEmployee();
        Employee employee = selectEmployee();

        PrintService.printServiceList(serviceList);
        List<Service> customerServiceChoices = new ArrayList<>();
        char addOtherServiceAgain = 'Y';
        do {
            Service service = selectService();
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
        ReservationService.createReservation(reservationList, customer, employee, customerServiceChoices);
    }

    private static Customer selectCustomer() {
        Customer customer = null;
        do {
            System.out.print("Masukkan Customer Id: ");
            String customerId = input.nextLine();
            customer = ValidationService.validateCustomerId(customerId, personList);
            if (customer == null) {
                System.out.println("Customer tidak ditemukan");
                continue;
            }
        } while (customer == null);
        return customer;
    }

    private static Employee selectEmployee() {
        Employee employee = null;
        do {
            System.out.print("Pilih employee.\nMasukkan employee id: ");
            String employeeId = input.nextLine();
            employee = ValidationService.validateEmployeeId(employeeId, personList);
            if (employee == null) {
                System.out.println("Customer tidak ditemukan");
                continue;
            }
        } while (employee == null);
        return employee;
    }

    private static Service selectService() {
        Service service = null;
        do {
            System.out.println("Pilih service");
            System.out.print("Masukkan service id: ");
            String serviceId = input.nextLine();
            service = ValidationService.validateServiceId(serviceId, serviceList);
            if (service == null) {
                System.out.println("Service tidak ditemukan");
                continue;
            }
        } while (service == null);
        return service;
    }
}
