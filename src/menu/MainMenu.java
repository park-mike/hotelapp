package menu;

import api.AdminResources;
import api.HotelResource;
import model.IRoom;
import model.Reservation;
import model.Room;
import model.roomType;
import service.ReservationService;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {
    public static final ArrayList<String> roomCheckMain = new ArrayList<>();
    protected static final String startMenu = """
            MAIN MENU
            Please select a number 1-5 from the choices below:
            1. Reserve Room
            2. Check reservation
            3. Create account
            4. Admin menu
            5. Exit
            """;
    protected static boolean keepRunning = true;

    public static void displayMainMenu() {
        //String line = "";
        try (Scanner scanner = new Scanner(System.in)) {
            while (keepRunning) {
                System.out.println(startMenu);
                while (!scanner.hasNextInt()) {
                    System.out.println("enter 1-5");
                    scanner.next();
                }
                while (scanner.hasNextInt()) {
                    int userInput = scanner.nextInt();
                    if (userInput < 6 && userInput > 0) {
                        scanner.nextLine();
                        handleUserInput(scanner, userInput);
                        break;
                    } else {
                        System.out.println("please enter number 1-5");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected static void handleUserInput(Scanner scanner, int switchInput) {
        switch (switchInput) {
            case 1:
                ReserveRoom(scanner);
                break;
            case 2:
                CheckReservations(scanner);
                break;
            case 3:
                CreateAccount(scanner);
                break;
            case 4:
                AdminMenu.displayAdminMenu(scanner);
            case 5:
                System.out.println("Exiting hotel program");
                keepRunning = false;
                System.exit(0);

        }
    }
    private static void CheckReservations(Scanner scanner) {
        System.out.println("enter email");
        String email = scanner.nextLine();
        if(!AdminResources.getAllRooms().isEmpty()) {
        if (HotelResource.getCustomer(email) != null) {
            for (Reservation reservation : HotelResource.getCustomerReservations(email)) {
                System.out.println(reservation);
            }
        } } else {
            System.out.println("no resvervation for " + email);
        }
    }

    private static void roomResv(final Collection<IRoom> availableRooms, String email, Date checkInDate, Date checkOutDate, Scanner scanner) {
        System.out.println("what room do you want?");
        String roomNumber = scanner.next();
        IRoom roomSel = null;
    for(IRoom iRooms :availableRooms)
    {
        if (iRooms.getRoomNum().equalsIgnoreCase(roomNumber)) {
            System.out.println("processing");
            roomSel = iRooms;
            break;
        }
        }
    if (roomSel != null){
        Reservation resChoice = HotelResource.reserveHotelRoom(email, roomSel, checkInDate, checkOutDate);
        System.out.println("Room reserved:");
        System.out.println("Information: "+resChoice);
    } else { System.out.println("incorrect entry");
    }
    }
    private static void ReserveRoom(Scanner scanner) {
        Boolean dateloop = false;
        Date checkInDate = null;
        Date checkOutDate = null;
        //Date altCheckInDate = null;
        //Date altCheckOutDate = null;

        //String roomNumber = null;
        SimpleDateFormat enterDate = new SimpleDateFormat("MM/dd/yyyy");
        Pattern datePattern = Pattern.compile(String.valueOf(enterDate));
        while (!dateloop) {
            try {
                System.out.println("enter checkin date mm/dd/yyyy");
                checkInDate = enterDate.parse(scanner.nextLine());
                datePattern.matcher(String.valueOf(checkInDate)).matches();
                System.out.println("enter checkout date mm/dd/yyyy");
                checkOutDate = enterDate.parse(scanner.nextLine());
                datePattern.matcher(String.valueOf(checkOutDate)).matches();
                dateloop = true;
            } catch (Exception e) {
                System.out.println("date format error, try again");
            }
        }
        Collection<IRoom> availableRooms = HotelResource.findARoom(checkInDate, checkOutDate);

        while (availableRooms.isEmpty()) {
            System.out.println("There are no rooms available for your selected dates. " +
                    "\n" + "The system is now searching for any room available with 7 days.");
            try {
                Calendar calendar = new GregorianCalendar();
                assert checkInDate != null;
                calendar.setTime(checkInDate);
                calendar.add(Calendar.DATE, -7);
                Date altCheckInDate = calendar.getTime();
                assert checkOutDate != null;
                calendar.setTime(checkOutDate);
                calendar.add(Calendar.DATE, 7);
                Date altCheckOutDate = calendar.getTime();
                System.out.println("Searching the following dates: " + altCheckInDate + " - " + altCheckOutDate);
                Collection<IRoom> altRoomList = HotelResource.findARoom(altCheckInDate, altCheckOutDate);

                if (altRoomList != null && !altRoomList.isEmpty()) {
                    for (IRoom room : altRoomList) {
                        //System.out.println("rooms: A " + room);
                        System.out.println("available rooms " + availableRooms);
                        //System.out.println(altRoomList);
                        break; }
                } else {
                    System.out.println("No rooms available");
                    displayMainMenu();
                    break;
                }
            } catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());
            }
                //for (IRoom room : altRoomList) {System.out.println("alt room: " + room);
                for (IRoom room : availableRooms) {
                    System.out.println("room: " + room);
                    break;
                }
            System.out.println("No rooms available, returning to main menu");
            displayMainMenu();
            break;
            }

            String roomNumber = null;
            Boolean roomMatch = true;
            String email = "";
            String newAcct = "";
            try {
                System.out.println("Do you need to create an account? Enter y or n");
                newAcct = scanner.nextLine();
                if (newAcct.equalsIgnoreCase("y")) {
                    email = CreateAccount(scanner);
                } else {
                    System.out.println("enter email");
                    email = scanner.nextLine();
                    //email = "";
                    if (HotelResource.getCustomer(email) != null) {
                        System.out.println("email checks out");
                    } else {
                        System.out.println("incorrect email, no account");
                        email = CreateAccount(scanner);
                    }
                }
                {
                    if (!availableRooms.isEmpty()) {    //!AdminResources.getAllRooms().isEmpty()) {
                        System.out.println("What room number do you want? Select from the following rooms: " //+ "\n" + AdminResources.getAllRooms()
                                + "\n" + availableRooms);

                    } else {
                        System.out.println("no rooms in system");
                        displayMainMenu();
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("IllegalArgumentException");
            }
            roomResv(availableRooms, email, checkInDate, checkOutDate, scanner);
            //roomResv(altRoomList, email, altCheckInDate, altCheckOutDate, scanner);
        }
    private static void AddRoom(Scanner scanner) {
        List<IRoom> rooms = new ArrayList<>();
        roomType roomType = model.roomType.SINGLE;
        System.out.println("Enter room number");
        while (!scanner.hasNextInt()) {
            System.out.println("invalid entry, enter room number");
            scanner.next();
        }
        String roomNumber = scanner.next();
        System.out.println("Press 1 for single room, 2 for double room");
        while (scanner.hasNext()) {
            int beds = scanner.nextInt();
            if (beds == 1 || beds == 2) {
                roomType = beds == 1 ? model.roomType.SINGLE : model.roomType.DOUBLE;
                break;
            } else {
                System.out.println("please enter the number 1 or 2");
            }
        }
        System.out.println("Enter price");
        while (!scanner.hasNextDouble()) {
            System.out.println("enter number for price");
            scanner.next();
        }
        double price = scanner.nextDouble();

        Room room = new Room(roomNumber, price, roomType);
        System.out.println("Room information: " + "\n" + "Room number is " + roomNumber + "\n" + "Price is " + price + "\n" + "Room type is " + roomType);
        rooms.add(room);
        AdminResources.addRoom(rooms);
        displayMainMenu();
    }


    public static String CreateAccount(Scanner scanner) {
        boolean eloop = false;
        boolean b = false;
        String email = "";
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern emailValidation = Pattern.compile(emailRegex);
        while (!eloop) {
            try {
                System.out.println("Create account:" + "\n" + "Enter email");
                email = scanner.nextLine();
                if (emailValidation.matcher(email).matches()) {

                    eloop = true;
                } else {
                    System.out.println("Incorrect email format, try again");
                }
            } catch (Exception exception) {
                System.out.println("email format error, try again" + exception.getLocalizedMessage());

            }
        }
        System.out.println("First name: ");
        String firstName = scanner.nextLine();
        System.out.println("Last name: ");
        String lastName = scanner.nextLine();
        try {
            HotelResource.createACustomer(email, firstName, lastName);
            System.out.println("acct created");
        } catch (IllegalArgumentException e) {

            System.out.println(e.getLocalizedMessage());
            return null;
        }
        System.out.println("Account created: \n" + "Name: " + firstName + " " + lastName + System.lineSeparator() + email);
        return email;
    }
}

