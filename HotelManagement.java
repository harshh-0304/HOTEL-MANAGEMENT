import java.util.ArrayList;
import java.util.Scanner;

class Hotel {
    String name;
    int totalRooms;
    int availableRooms;
    double roomRate;
    
    public Hotel(String name, int totalRooms, double roomRate) {
        this.name = name;
        this.totalRooms = totalRooms;
        this.availableRooms = totalRooms;
        this.roomRate = roomRate;
    }
}

class Guest {
    String name;
    String contactNumber;
    String checkInDate;
    int numberOfDays;
    int numberOfRooms;
    Hotel hotel;
    double totalBill;
    
    public Guest(String name, String contactNumber, String checkInDate, 
                int numberOfDays, int numberOfRooms, Hotel hotel) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.checkInDate = checkInDate;
        this.numberOfDays = numberOfDays;
        this.numberOfRooms = numberOfRooms;
        this.hotel = hotel;
        this.totalBill = numberOfDays * numberOfRooms * hotel.roomRate;
    }
}

public class HotelManagement {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Hotel> hotels = new ArrayList<>();
    private static ArrayList<Guest> guests = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to Hotel Management System");
        System.out.println("Please select an option:");
        options();
    }

    public static void options() {
        int choice = 0;
        do {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Manage Hotel");
            System.out.println("2. Manage Guest");
            System.out.println("3. Report");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    manageHotel();
                    break;
                case 2:
                    manageGuest();
                    break;
                case 3:
                    report();
                    break;
                case 0:
                    System.out.println("Thank you for using Hotel Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);
    }

    public static void manageHotel() {
        System.out.println("\n=== Hotel Management ===");
        System.out.println("1. Add Hotel");
        System.out.println("2. Remove Hotel");
        System.out.println("3. View All Hotels");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        
        switch (choice) {
            case 1:
                addHotel();
                break;
            case 2:
                removeHotel();
                break;
            case 3:
                viewHotels();
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    public static void addHotel() {
        System.out.println("\nEnter the details of the hotel:");
        input.nextLine(); // Clear buffer
        System.out.print("Name: ");
        String name = input.nextLine();
        System.out.print("Total Rooms: ");
        int totalRooms = input.nextInt();
        System.out.print("Room rate per night: ");
        double roomRate = input.nextDouble();
        
        hotels.add(new Hotel(name, totalRooms, roomRate));
        System.out.println("Hotel added successfully!");
    }

    public static void removeHotel() {
        if (hotels.isEmpty()) {
            System.out.println("No hotels to remove!");
            return;
        }
        
        viewHotels();
        input.nextLine(); // Clear buffer
        System.out.print("Enter the name of the hotel to remove: ");
        String name = input.nextLine();
        
        hotels.removeIf(hotel -> hotel.name.equalsIgnoreCase(name));
        System.out.println("Hotel removed successfully");
    }

    public static void viewHotels() {
        if (hotels.isEmpty()) {
            System.out.println("No hotels available!");
            return;
        }
        
        System.out.println("\n=== Available Hotels ===");
        for (Hotel hotel : hotels) {
            System.out.println("Name: " + hotel.name);
            System.out.println("Total Rooms: " + hotel.totalRooms);
            System.out.println("Available Rooms: " + hotel.availableRooms);
            System.out.println("Room Rate: $" + hotel.roomRate);
            System.out.println("------------------------");
        }
    }

    public static void manageGuest() {
        System.out.println("\n=== Guest Management ===");
        System.out.println("1. Check In Guest");
        System.out.println("2. Check Out Guest");
        System.out.println("3. View All Guests");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        
        switch (choice) {
            case 1:
                checkInGuest();
                break;
            case 2:
                checkOutGuest();
                break;
            case 3:
                viewGuests();
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    public static void checkInGuest() {
        if (hotels.isEmpty()) {
            System.out.println("No hotels available for check-in!");
            return;
        }

        viewHotels();
        input.nextLine(); // Clear buffer
        
        System.out.println("\nEnter the details of the guest:");
        System.out.print("Name: ");
        String name = input.nextLine();
        System.out.print("Contact Number: ");
        String contactNumber = input.nextLine();
        System.out.print("Check-in Date (DD/MM/YYYY): ");
        String checkInDate = input.nextLine();
        System.out.print("Number of days: ");
        int numberOfDays = input.nextInt();
        System.out.print("Number of rooms: ");
        int numberOfRooms = input.nextInt();
        input.nextLine(); // Clear buffer
        
        System.out.print("Enter hotel name for check-in: ");
        String hotelName = input.nextLine();
        
        Hotel selectedHotel = null;
        for (Hotel hotel : hotels) {
            if (hotel.name.equalsIgnoreCase(hotelName)) {
                selectedHotel = hotel;
                break;
            }
        }
        
        if (selectedHotel == null) {
            System.out.println("Hotel not found!");
            return;
        }
        
        if (selectedHotel.availableRooms < numberOfRooms) {
            System.out.println("Not enough rooms available!");
            return;
        }
        
        selectedHotel.availableRooms -= numberOfRooms;
        Guest guest = new Guest(name, contactNumber, checkInDate, numberOfDays, numberOfRooms, selectedHotel);
        guests.add(guest);
        
        System.out.println("\nCheck-in successful!");
        System.out.println("Total bill: $" + guest.totalBill);
    }

    public static void checkOutGuest() {
        if (guests.isEmpty()) {
            System.out.println("No guests to check out!");
            return;
        }
        
        viewGuests();
        input.nextLine(); // Clear buffer
        System.out.print("Enter guest name to check out: ");
        String name = input.nextLine();
        
        Guest guestToRemove = null;
        for (Guest guest : guests) {
            if (guest.name.equalsIgnoreCase(name)) {
                guestToRemove = guest;
                guest.hotel.availableRooms += guest.numberOfRooms;
                break;
            }
        }
        
        if (guestToRemove != null) {
            guests.remove(guestToRemove);
            System.out.println("Guest checked out successfully!");
        } else {
            System.out.println("Guest not found!");
        }
    }

    public static void viewGuests() {
        if (guests.isEmpty()) {
            System.out.println("No guests currently staying!");
            return;
        }
        
        System.out.println("\n=== Current Guests ===");
        for (Guest guest : guests) {
            System.out.println("Name: " + guest.name);
            System.out.println("Hotel: " + guest.hotel.name);
            System.out.println("Check-in Date: " + guest.checkInDate);
            System.out.println("Number of Rooms: " + guest.numberOfRooms);
            System.out.println("Total Bill: $" + guest.totalBill);
            System.out.println("------------------------");
        }
    }

    public static void report() {
        System.out.println("\n=== Hotel Management System Report ===");
        System.out.println("Total Hotels: " + hotels.size());
        System.out.println("Total Guests: " + guests.size());
        
        int totalRooms = 0;
        int totalOccupiedRooms = 0;
        double totalRevenue = 0;
        
        for (Hotel hotel : hotels) {
            totalRooms += hotel.totalRooms;
            totalOccupiedRooms += (hotel.totalRooms - hotel.availableRooms);
        }
        
        for (Guest guest : guests) {
            totalRevenue += guest.totalBill;
        }
        
        System.out.println("Total Rooms: " + totalRooms);
        System.out.println("Occupied Rooms: " + totalOccupiedRooms);
        System.out.println("Available Rooms: " + (totalRooms - totalOccupiedRooms));
        System.out.println("Current Revenue: $" + totalRevenue);
    }
}
