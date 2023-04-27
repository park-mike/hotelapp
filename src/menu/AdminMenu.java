package menu;

import api.AdminResources;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.roomType;
import service.ReservationService;

import java.util.*;

public class AdminMenu {
    ArrayList<String> roomNumList = new ArrayList<>();
    public static final ArrayList<String> roomCheck = new ArrayList<>();
    protected static final String adminText = """
            ADMIN MENU
            1. Get all rooms
            2. Add a room
            3. Display all customers
            4. Display all reservations
            5. Return to main menu
            """;

    public static void displayAdminMenu(Scanner scanner) {
        boolean keepRunning = true;
        Scanner adminScanner = new Scanner(System.in);
        while (keepRunning) {
            System.out.println(adminText);
            while (!adminScanner.hasNextInt()) {
                System.out.println("enter 1-5");
                adminScanner.next();
            }
            while (adminScanner.hasNext()) {
                int scanInt = adminScanner.nextInt();

                if (scanInt > 0 && scanInt < 6) {
                    adminScanner.nextLine();
                    makeMenuSelection(adminScanner, scanInt);
                    break;
                } else {
                    System.out.println("please enter a number between 1 and 5");
                }

            }
        }
    }

    private static void makeMenuSelection(Scanner scanner, int menuInt) {
        switch (menuInt) {
            case 1 -> getAllRooms();
            case 2 -> addARoomAndPrice(scanner);
            case 3 -> displayAllCustomers();
            case 4 -> AdminResources.displayAllReservations();
            case 5 -> MainMenu.displayMainMenu();
            default -> throw new IllegalStateException("Number must match option on menu");
        }
    }
    private static void getAllRooms() {
        Collection<IRoom> allRooms = AdminResources.getAllRooms();
        if (allRooms.isEmpty()) {
            System.out.println("No room has been reserved");
        } else {
            for (IRoom room : allRooms) {
                System.out.println("Summary of rooms: ");
                System.out.println(room.toString());
            }
        }
    }
    public static void addARoomAndPrice(Scanner scanner) {
        String roomNumber = null;
        Double price = 0.0;
        ArrayList<IRoom> rooms = new ArrayList<>();
        roomType roomType = model.roomType.SINGLE;
        String adminInput = "n";
        ArrayList<String> roomCheck = new ArrayList<>();

        while (adminInput.equals("n")) {
            Boolean roomloop = true;
            Boolean roomCompareMatch = true;
            System.out.println("Enter room number. Number list will be validated on exit.");
            Boolean dupCheckBool = false;
            while (!scanner.hasNextInt()) {
                System.out.println("Enter room number.");
                scanner.nextLine();
            }
            while (roomloop) {
                roomNumber = scanner.next();
                LinkedList roomLL = new LinkedList();
                AdminResources.getAllRooms().addAll(roomLL);

                if ((!roomCheck.contains(roomNumber) || dupCheckBool == true || roomCheck.isEmpty())) {
                    roomCheck.add(roomNumber);
                    //System.out.println("acceptable room");
                    roomloop = false;
                } else {
                    if (roomCheck.contains(roomNumber) || roomLL.contains(roomNumber)) {
                        System.out.println("room taken, try again");
                        scanner.hasNext();
                    }
                }
            }
            System.out.println(roomCheck);
            System.out.println("Enter price");
            while (!scanner.hasNextDouble()) {
                System.out.println("enter double input");
                scanner.next();
            }
            price = scanner.nextDouble();
            System.out.println("Press 1 for single room, 2 for double room");
            while (!scanner.hasNextInt()) {
                System.out.println("enter a number");
                scanner.next();
            }
            while (scanner.hasNextInt()) {
                int beds = scanner.nextInt();
                if (beds == 1 || beds == 2) {
                    roomType = beds == 1 ? model.roomType.SINGLE : model.roomType.DOUBLE;
                    break;
                } else {
                    System.out.println("please enter the number 1 or 2");
                }
            }
            Room room = new Room(roomNumber, price, roomType);
            //System.out.println("Room added");
            //System.out.println(roomCheck.size());
            System.out.println(room);
            rooms.add(room);
            System.out.println("Exit to menu? press y or no");
            adminInput = scanner.next();
        }
        AdminResources.addRoom(rooms);
    }

    private static void displayAllCustomers() {
        Collection<Customer> customers = AdminResources.displayAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("no customers");
        } else {
            for (Customer customer : customers) System.out.println(customer);

        }
    }

    public static void displayAllReservations() {
        ReservationService reservationService = ReservationService.getSINGLETON();
        reservationService.printAllReservations();
    }
}