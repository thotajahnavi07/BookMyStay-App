import java.util.HashMap;
import java.util.Map;

// ---------------- Room Domain Model ----------------
abstract class Room {

    protected String roomType;
    protected double price;

    public Room(String roomType, double price) {
        this.roomType = roomType;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Price per Night: $" + price);
    }
}

// Concrete Room Types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 100);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 180);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 350);
    }
}

// ---------------- Inventory ----------------
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // Example unavailable room
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return inventory;
    }
}

// ---------------- Search Service ----------------
class RoomSearchService {

    private RoomInventory inventory;
    private Map<String, Room> roomCatalog;

    public RoomSearchService(RoomInventory inventory) {

        this.inventory = inventory;

        roomCatalog = new HashMap<>();
        roomCatalog.put("Single Room", new SingleRoom());
        roomCatalog.put("Double Room", new DoubleRoom());
        roomCatalog.put("Suite Room", new SuiteRoom());
    }

    public void searchAvailableRooms() {

        System.out.println("===== Available Rooms =====");

        for (String roomType : roomCatalog.keySet()) {

            int available = inventory.getAvailability(roomType);

            if (available > 0) {

                Room room = roomCatalog.get(roomType);
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
    }
}

// ---------------- Main Class ----------------
public class BookMyStay {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        RoomSearchService searchService = new RoomSearchService(inventory);

        searchService.searchAvailableRooms();

        System.out.println("Search completed. Inventory state unchanged.");
    }
}