import java.util.*;

// Custom Exception for invalid bookings
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Room Inventory Class
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public boolean isValidRoomType(String type) {
        return rooms.containsKey(type);
    }

    public int getAvailableRooms(String type) {
        return rooms.getOrDefault(type, 0);
    }

    public void bookRoom(String type, int count) throws InvalidBookingException {
        int available = getAvailableRooms(type);

        // Guarding system state
        if (count > available) {
            throw new InvalidBookingException("Not enough rooms available.");
        }

        rooms.put(type, available - count);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Availability:");
        for (String type : rooms.keySet()) {
            System.out.println(type + " Rooms: " + rooms.get(type));
        }
    }
}

// Validator Class
class BookingValidator {

    public static void validate(String roomType, int count, RoomInventory inventory)
            throws InvalidBookingException {

        // Input validation
        if (roomType == null || roomType.isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (count <= 0) {
            throw new InvalidBookingException("Booking count must be greater than zero.");
        }

        if (inventory.getAvailableRooms(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for selected type.");
        }
    }
}

// Main Class
public class BookMyStay {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();

        try {
            System.out.println("=== Book My Stay ===");

            System.out.print("Enter Room Type (Single/Double/Suite): ");
            String roomType = sc.nextLine();

            System.out.print("Enter number of rooms: ");
            int count = sc.nextInt();

            // Step 1: Validate input (Fail-Fast)
            BookingValidator.validate(roomType, count, inventory);

            // Step 2: Process booking
            inventory.bookRoom(roomType, count);

            System.out.println("Booking successful!");

        } catch (InvalidBookingException e) {
            // Graceful failure handling
            System.out.println("Booking Failed: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }

        // System continues safely
        inventory.displayInventory();
        sc.close();
    }
}