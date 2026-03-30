import java.util.*;

// Custom Exception
class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

// Booking Class
class Booking {
    String bookingId;
    String roomType;

    public Booking(String bookingId, String roomType) {
        this.bookingId = bookingId;
        this.roomType = roomType;
    }
}

// Inventory Management
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public void decrement(String type) throws BookingException {
        int available = rooms.getOrDefault(type, 0);
        if (available <= 0) {
            throw new BookingException("No rooms available for booking.");
        }
        rooms.put(type, available - 1);
    }

    public void increment(String type) {
        rooms.put(type, rooms.getOrDefault(type, 0) + 1);
    }

    public void display() {
        System.out.println("\nCurrent Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + ": " + rooms.get(type));
        }
    }
}

// Booking Service
class BookingService {
    private Map<String, Booking> bookingHistory = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Create Booking
    public void createBooking(String bookingId, String roomType) throws BookingException {

        if (bookingHistory.containsKey(bookingId)) {
            throw new BookingException("Booking ID already exists.");
        }

        inventory.decrement(roomType);

        Booking booking = new Booking(bookingId, roomType);
        bookingHistory.put(bookingId, booking);

        System.out.println("Booking confirmed: " + bookingId);
    }

    // Cancel Booking (Rollback Logic)
    public void cancelBooking(String bookingId) throws BookingException {

        // Validation
        if (!bookingHistory.containsKey(bookingId)) {
            throw new BookingException("Booking does not exist.");
        }

        Booking booking = bookingHistory.get(bookingId);

        // Step 1: Push to stack (LIFO rollback)
        rollbackStack.push(bookingId);

        // Step 2: Restore inventory
        inventory.increment(booking.roomType);

        // Step 3: Remove booking from history
        bookingHistory.remove(bookingId);

        System.out.println("Booking cancelled: " + bookingId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recent Cancellations): " + rollbackStack);
    }
}

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        try {
            System.out.println("=== Book My Stay ===");

            // Create Booking
            System.out.print("Enter Booking ID: ");
            String id = sc.nextLine();

            System.out.print("Enter Room Type (Single/Double/Suite): ");
            String type = sc.nextLine();

            service.createBooking(id, type);

            // Cancel Booking
            System.out.print("\nEnter Booking ID to cancel: ");
            String cancelId = sc.nextLine();

            service.cancelBooking(cancelId);

        } catch (BookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Final state
        inventory.display();
        service.showRollbackStack();

        sc.close();
    }
}