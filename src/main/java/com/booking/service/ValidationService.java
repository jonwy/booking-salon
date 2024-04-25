package com.booking.service;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Service;

import java.util.List;

public class ValidationService {
    // Buatlah function sesuai dengan kebutuhan
    public static void validateInput(){

    }

    public static Customer validateCustomerId(String id, List<Person> persons){
        for (Person person: persons) {
            if (person instanceof Customer) {
                Customer customer = (Customer) person;
                if (customer.getId().equalsIgnoreCase(id)) {
                    return customer;
                }
            }
        }
        return null;
    }

    public static Employee validateEmployeeId(String id, List<Person> persons) {
        for (Person person: persons) {
            if (person instanceof Employee) {
                Employee employee = (Employee) person;
                if (employee.getId().equalsIgnoreCase(id)) {
                    return employee;
                }
            }
        }
        return null;
    }

    public static Service validateServiceId(String id, List<Service> serviceList){
        for (Service service: serviceList) {
            if (service.getServiceId().equalsIgnoreCase(id)) {
                return service;
            }
        }
        return null;
    }
}
