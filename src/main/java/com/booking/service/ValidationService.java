package com.booking.service;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Service;

import java.util.List;

public class ValidationService {
    // Buatlah function sesuai dengan kebutuhan
    public static void validateInput(String type, boolean isNull){

    }

    public static int isInputNumber(String input) {
        if (input.matches("[0-9]+")) {
            return Integer.parseInt(input);
        }
        System.out.println("Input harus berupa angka");
        return -1;
    }

    public static boolean workstageStatusChangeValidate(int input) {
        if (input >= 1 && input <= 2) {
            return true;
        }
        System.out.println("Input hanya boleh 1 / 2");
        return false;
    }

    public static void printErrorDataIsNull(String type) {
        System.out.println(type + " tidak ditemukan");
    }

}
