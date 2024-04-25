package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;

public class MenuService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    private static List<Reservation> reservationList = new ArrayList<>();
    final static Scanner input = new Scanner(System.in);

    public static void mainMenu() {
        String[] mainMenuArr = {"Show Data", "Create Reservation", "Complete/cancel reservation", "Exit"};
        String[] subMenuArr = {"Recent Reservation", "Show Customer", "Show Available Employee", "Show History and Profit","Back to main menu"};
    
        int optionMainMenu;
        int optionSubMenu;

		boolean backToMainMenu = false;
        boolean backToSubMenu = false;
        do {
            PrintService.printMenu("Main Menu", mainMenuArr);
            System.out.print("Choice: ");
            optionMainMenu = ValidationService.isInputNumber(input.nextLine());
            if (optionMainMenu == -1) continue;
            switch (optionMainMenu) {
                case 1:
                    do {
                        PrintService.printMenu("Show Data", subMenuArr);
                        System.out.print("Choice: ");
                        optionSubMenu = ValidationService.isInputNumber(input.nextLine());
                        if (optionSubMenu == -1) continue;
                        // Sub menu - menu 1
                        switch (optionSubMenu) {
                            case 1:
                                PrintService.showRecentReservation(reservationList);
                                break;
                            case 2:
                                PrintService.showAllCustomer(personList);
                                break;
                            case 3:
                                PrintService.showAllEmployee(personList);
                                break;
                            case 4:
                                PrintService.showHistoryReservation(reservationList);
                                break;
                            case 0:
                                backToSubMenu = true;
                            default:
                                System.out.println("Pilih hanya antara 0 - 4");
                                break;
                        }
                    } while (!backToSubMenu);
                    break;
                case 2:
                    ReservationService.createReservation(reservationList, personList, serviceList);
                    break;
                case 3:
                    ReservationService.editReservationWorkstage(reservationList);
                    break;
                case 0:
                    backToMainMenu = true;
                    break;
                default:
                    System.out.println("Pilih hanya antara 0 - 3");
                    break;
            }
        } while (!backToMainMenu);
	}
}
