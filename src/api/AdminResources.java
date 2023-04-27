package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResources {
    //static

    public static Customer getCustomer(String email) {
        CustomerService customerService = CustomerService.getSINGLETON();
        return customerService.getCustomer(email);
    }

    public static void addRoom(List<IRoom> rooms) {
        ReservationService reservationService = ReservationService.getSINGLETON();
        for (IRoom iRoom : rooms) {
            reservationService.addRoom(iRoom);
        }
    }

    public static Collection<IRoom> getAllRooms() {
        ReservationService reservationService = ReservationService.getSINGLETON();
        return reservationService.getAllRooms();
    }

    public static Collection<Customer> displayAllCustomers() {
        CustomerService customerService = CustomerService.getSINGLETON();
        return customerService.getAllCustomers();
    }

    public static void displayAllReservations() {
        ReservationService reservationService = ReservationService.getSINGLETON();
        reservationService.printAllReservations();
    }
}