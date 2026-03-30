import java.util.*;

// ---------------- Reservation ----------------
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
}

// ---------------- Room Inventory ----------------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

// ---------------- Booking Request Queue ----------------
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request received from " + reservation.getGuestName());
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

// ---------------- Room Allocation Service ----------------
class RoomAllocationService {

    private RoomInventory inventory;

    // Map room types to assigned room IDs
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processReservation(Reservation reservation) {

        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) > 0) {

            // Generate unique room ID
            String roomId = roomType.replace(" ", "").toUpperCase() + "-" + UUID.randomUUID().toString().substring(0,5);

            // Get existing set or create new
            allocatedRooms.putIfAbsent(roomType, new HashSet<>());

            Set<String> roomSet = allocatedRooms.get(roomType);

            // Ensure uniqueness
            if (!roomSet.contains(roomId)) {

                roomSet.add(roomId);

                // Decrease inventory immediately
                inventory.decreaseAvailability(roomType);

                System.out.println("\nReservation Confirmed!");
                System.out.println("Guest: " + reservation.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: " + roomId);

            }

        } else {

            System.out.println("\nReservation Failed for " + reservation.getGuestName());
            System.out.println("No available rooms for: " + roomType);
        }
    }
}

// ---------------- Main Class ----------------
public class BookMyStay {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue requestQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService(inventory);

        // Booking requests (FIFO)
        requestQueue.addRequest(new Reservation("Alice", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Double Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        requestQueue.addRequest(new Reservation("David", "Suite Room")); // may fail

        System.out.println("\nProcessing Booking Requests...\n");

        while (requestQueue.hasRequests()) {

            Reservation reservation = requestQueue.getNextRequest();
            allocationService.processReservation(reservation);

        }

        inventory.displayInventory();

        System.out.println("\nAll requests processed.");
    }
}