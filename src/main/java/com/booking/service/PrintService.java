package com.booking.service;

import java.util.List;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;

public class PrintService {
    public static void printMenu(String title, String[] menuArr){
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {   
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);   
            num++;
        }
    }

    public static String printServices(List<Service> serviceList){
        String result = "";
        // Bisa disesuaikan kembali
        for (Service service : serviceList) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }

    public static void printServiceList(List<Service> services) {
        int num = 1;
        System.out.printf("| %-4s | %-10s | %-20s | %-20s |\n",
                "No.", "ID", "Nama", "Harga");
        for (Service service: services) {
            System.out.printf("| %-4s | %-10s | %-20s | %-20s |\n",
                num++, service.getServiceId(), service.getServiceName(), service.getPrice());
        }
        
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public static void showRecentReservation(List<Reservation> reservationList){
        int num = 1;
        System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+========================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Waiting") || reservation.getWorkstage().equalsIgnoreCase("In process")) {
                System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), reservation.getReservationPrice(), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
            }
        }
    }

    public static void showAllCustomer(List<Person> persons){
        int num = 1;
        System.out.println("Data Customer");
        System.out.printf("| %-4s | %-10s | %-20s | %-20s | %-15s | %-15s |\n",
                "No.", "ID", "Nama", "Alamat", "Membership", "Uang");
        System.out.println("+========================================================================================+");
        for (Person person: persons) {
            if (person instanceof Customer) {
                Customer customer = (Customer)person;
                System.out.printf("| %-4s | %-10s | %-20s | %-20s | %-15s | %-15s |\n", 
                num++, customer.getId(), customer.getName(), customer.getAddress(), 
                customer.getMember().getMembershipName(), customer.getWallet());
            }
        }
    }

    public static void showAllEmployee(List<Person> persons){
        int num = 1;
        System.out.println("Data Customer");
        System.out.printf("| %-4s | %-10s | %-20s | %-12s | %-11s |\n",
                "No.", "ID", "Nama", "Alamat", "Pengalaman");
        System.out.println("+========================================================================================+");
        for (Person person: persons) {
            if (person instanceof Employee) {
                Employee employee = (Employee)person;
                System.out.printf("| %-4s | %-10s | %-20s | %-12s | %-11s |\n", 
                num++, employee.getId(), employee.getName(), employee.getAddress(), employee.getExperience());
            }
        }
    }

    public void showHistoryReservation(){
        
    }
}
