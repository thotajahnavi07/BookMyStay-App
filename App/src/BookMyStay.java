import java.io.*;
import java.util.*;

// Booking Class (Serializable)
class Booking implements Serializable {
    String bookingId;
    String roomType;

    public Booking(String bookingId, String roomType) {
        this.bookingId = bookingId;
        this.roomType = roomType;
    }

    public String toString() {
        return bookingId + " (" + roomType + ")";
    }
}

// System State (Serializable)
class SystemState implements Serializable {
    Map<String, Integer> inventory;
    Map<String, Booking> bookings;

    public SystemState(Map<String, Integer> inventory,
                       Map<String, Booking> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "hotel_data.ser";

    // SAVE (Serialization)
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("💾 Data saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // LOAD (Deserialization)
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("📂 Data loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("⚠ No previous data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("⚠ Corrupted data. Starting fresh.");
        }

        return null;
    }
}

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        Map<String, Integer> inventory;
        Map<String, Booking> bookings;

        // 🔹 Step 1: Load existing state
        SystemState state = PersistenceService.load();

        if (state != null) {
            inventory = state.inventory;
            bookings = state.bookings;
        } else {
            // Default state
            inventory = new HashMap<>();
            inventory.put("Single", 5);
            inventory.put("Double", 3);

            bookings = new HashMap<>();
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== Book My Stay (Persistent System) ===");

        // 🔹 Step 2: New Booking
        System.out.print("Enter Booking ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Room Type (Single/Double): ");
        String type = sc.nextLine();

        if (!inventory.containsKey(type)) {
            System.out.println("Invalid room type.");
        } else if (inventory.get(type) <= 0) {
            System.out.println("No rooms available.");
        } else {
            inventory.put(type, inventory.get(type) - 1);
            bookings.put(id, new Booking(id, type));
            System.out.println("✅ Booking successful!");
        }

        // 🔹 Step 3: Display Current State
        System.out.println("\nCurrent Inventory:");
        for (String t : inventory.keySet()) {
            System.out.println(t + ": " + inventory.get(t));
        }

        System.out.println("\nBooking History:");
        for (Booking b : bookings.values()) {
            System.out.println(b);
        }

        // 🔹 Step 4: Save state before exit
        SystemState newState = new SystemState(inventory, bookings);
        PersistenceService.save(newState);

        sc.close();
    }
}