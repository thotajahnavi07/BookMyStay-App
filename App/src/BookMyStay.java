import java.util.*;

// Add-On Service class
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Add-On Service Manager
class AddOnServiceManager {
    // Map: Reservation ID → List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Get services for a reservation
    public List<AddOnService> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total cost of services
    public double calculateTotalCost(String reservationId) {
        double total = 0;
        List<AddOnService> services = getServices(reservationId);

        for (AddOnService s : services) {
            total += s.getCost();
        }
        return total;
    }
}

// Main class
public class BookMyStay {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.print("Enter Reservation ID: ");
        String reservationId = sc.nextLine();

        // Sample services
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService pickup = new AddOnService("Airport Pickup", 800);

        System.out.println("\nSelect Add-On Services:");
        System.out.println("1. WiFi (₹200)");
        System.out.println("2. Breakfast (₹500)");
        System.out.println("3. Airport Pickup (₹800)");
        System.out.println("4. Done");

        while (true) {
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            if (choice == 4) break;

            switch (choice) {
                case 1:
                    manager.addService(reservationId, wifi);
                    break;
                case 2:
                    manager.addService(reservationId, breakfast);
                    break;
                case 3:
                    manager.addService(reservationId, pickup);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }

        // Display selected services
        List<AddOnService> selectedServices = manager.getServices(reservationId);

        System.out.println("\nSelected Services for Reservation " + reservationId + ":");
        for (AddOnService s : selectedServices) {
            System.out.println("- " + s);
        }

        // Total cost
        double totalCost = manager.calculateTotalCost(reservationId);
        System.out.println("Total Add-On Cost: ₹" + totalCost);

        sc.close();
    }
}