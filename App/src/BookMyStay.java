import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Inventory (Thread-Safe)
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 2);
        rooms.put("Double", 2);
    }

    // Critical Section (SYNCHRONIZED)
    public synchronized boolean bookRoom(String roomType, String guestName) {

        int available = rooms.getOrDefault(roomType, 0);

        if (available > 0) {
            System.out.println(guestName + " booking " + roomType + " room...");

            // Simulate delay (to expose race condition if not synchronized)
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}

            rooms.put(roomType, available - 1);

            System.out.println("✅ Booking confirmed for " + guestName);
            return true;
        } else {
            System.out.println("❌ No rooms available for " + guestName);
            return false;
        }
    }

    public void display() {
        System.out.println("\nFinal Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + ": " + rooms.get(type));
        }
    }
}

// Shared Booking Queue
class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    // Add request
    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
    }

    // Remove request (Thread-safe)
    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}

// Thread Class
class BookingProcessor extends Thread {
    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            BookingRequest request;

            // Critical section: fetching request
            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) break;

            // Critical section handled inside inventory
            inventory.bookRoom(request.roomType, request.guestName);
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

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        // Simulating multiple guests
        queue.addRequest(new BookingRequest("Guest1", "Single"));
        queue.addRequest(new BookingRequest("Guest2", "Single"));
        queue.addRequest(new BookingRequest("Guest3", "Single")); // extra request
        queue.addRequest(new BookingRequest("Guest4", "Double"));
        queue.addRequest(new BookingRequest("Guest5", "Double"));

        // Multiple threads (Concurrent processing)
        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);
        BookingProcessor t3 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {}

        inventory.display();
    }
}