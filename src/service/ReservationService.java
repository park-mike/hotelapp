package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.*;

public class ReservationService {
    private static final ReservationService SINGLETON = new ReservationService();

    //private static final Collection<IRoom> iRooms = new ArrayList<IRoom>();
    private static final Collection<IRoom> iRooms = new HashSet<>();
    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Map<String, Collection<Reservation>> reservations = new HashMap<>();

    public static ReservationService getSINGLETON() {
        return SINGLETON;
    }

    public void addRoom(IRoom room) {
        IRoom duplicateCheck = getARoom(room.getRoomNum());
        if (duplicateCheck == null) {
            IRoom addRoom = new Room(room.getRoomNum(),  room.getRoomPrice(), room.getRoomType());
            rooms.put(room.getRoomNum(), addRoom);
        } else {
            System.out.println("Error: room number " + room.getRoomNum() + " already entered into system");
        }
    }


    public IRoom getARoom(String roomNum) {
        return rooms.get(roomNum);
    }

    public Collection<IRoom> getAllRooms() {
        iRooms.addAll(rooms.values());
        return rooms.values();
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        Collection<Reservation> customerReservations = reservations.get(customer.getEmail());
        if (customerReservations == null) {
            customerReservations = new ArrayList<>();
        }
        customerReservations.add(reservation);
        reservations.put(customer.getEmail(), customerReservations);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkoutDate) {
        Collection<IRoom> availableRooms = new ArrayList<>();
        Collection<IRoom> reservedRooms = findReservedRooms(checkInDate, checkoutDate);
        for (IRoom room : rooms.values()) {
            if (!reservedRooms.contains(room)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Collection<IRoom> findReservedRooms(Date checkInDate, Date checkoutDate) {
        Collection<Reservation> allReservations = getAllReservations();
        Collection<IRoom> reservedRooms = new ArrayList<>();
        for (Reservation reservation : allReservations) {
            if ((checkInDate.after(reservation.getCheckInDate()) || checkInDate.equals(reservation.getCheckInDate()) &&
                    checkoutDate.before(reservation.getCheckOutDate()) ||
                    checkoutDate.equals(reservation.getCheckOutDate()))) {
                reservedRooms.add(reservation.getRoom());
            }
        }
        return reservedRooms;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    private Collection<Reservation> getAllReservations() {
        Collection<Reservation> allReservations = new ArrayList<>();
        for (Collection<Reservation> reservations : reservations.values()) {
            allReservations.addAll(reservations);
        }
        return allReservations;
    }

    public void printAllReservations() {
        Collection<Reservation> allReservations = getAllReservations();
        if (allReservations.isEmpty()) {
            System.out.println("No reservations in system");
        } else {
            for (Reservation room : allReservations) {
                System.out.println(room);
            }
        }
    }
}
