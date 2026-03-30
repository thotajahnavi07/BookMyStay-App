import java.util.HashMap;
import java.util.Map;

// RoomInventory Class (Version 3.0)
class RoomInventory {

    // HashMap to store room type and availability
    private HashMap<String, Integer> inventory;

    // Constructor initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Register room types with counts
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Method to get availability of a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Method to update room availability
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    // Display entire inventory
    public void displayInventory() {
        System.out.println("===== Current Room Inventory =====");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}


// Main Application Class
public class BookMyStay {

    public static void main(String[] args) {

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        inventory.displayInventory();

        System.out.println("\nChecking availability of Single Room...");
        System.out.println("Available: " + inventory.getAvailability("Single Room"));

        System.out.println("\nUpdating availability of Single Room...");
        inventory.updateAvailability("Single Room", 4);

        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();

        System.out.println("\nApplication Terminated.");
    }
}