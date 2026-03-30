import java.util.LinkedList;
import java.util.Queue;

// ---------------- Reservation Class ----------------
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// ---------------- Booking Request Queue ----------------
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display queued requests
    public void displayQueue() {

        System.out.println("\n===== Booking Request Queue =====");

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }

    // Peek next request (without removing)
    public Reservation peekNextRequest() {
        return requestQueue.peek();
    }
}

// ---------------- Main Class ----------------
public class BookMyStay {

    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display queue (FIFO order)
        bookingQueue.displayQueue();

        // Show next request to be processed
        Reservation next = bookingQueue.peekNextRequest();

        if (next != null) {
            System.out.println("\nNext request to process:");
            next.displayReservation();
        }

        System.out.println("\nRequests stored in arrival order. No rooms allocated yet.");
    }
}