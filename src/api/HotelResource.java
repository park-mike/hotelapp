    package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    //provide a static reference
    public static Customer getCustomer(String email) {
        CustomerService customerService = CustomerService.getSINGLETON();
        return customerService.getCustomer(email);
    }
    public static Collection<Reservation> getCustomerReservations(String customerEmail) {
        ReservationService reservationService = ReservationService.getSINGLETON();
        return reservationService.getCustomerReservation(getCustomer(customerEmail));
    }
    public static void createACustomer(String email, String firstName, String lastName) {
        CustomerService customerService = CustomerService.getSINGLETON();
        customerService.addCustomer(email, firstName, lastName);
    }


    public static IRoom getRoom(String roomNumber) {
        ReservationService reservationService = ReservationService.getSINGLETON();
        return reservationService.getARoom(roomNumber);
    }

    public static Reservation reserveHotelRoom (String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        ReservationService reservationService = ReservationService.getSINGLETON();
        return reservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }



    public static Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        ReservationService reservationService = ReservationService.getSINGLETON();
        return reservationService.findRooms(checkIn, checkOut);
    }
}