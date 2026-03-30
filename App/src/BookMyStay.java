import java.util.*;

// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double amount;

    public Reservation(String reservationId, String guestName, String roomType, double amount) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.amount = amount;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Amount: ₹" + amount;
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Booking Report Service
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("\n--- Booking History ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {
        int totalBookings = reservations.size();
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalRevenue += r.getAmount();
        }

        System.out.println("\n--- Booking Summary ---");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

// Main Class
public class BookMyStay {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        System.out.print("Enter number of bookings to confirm: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        // Simulate confirmed bookings
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for booking " + (i + 1));

            System.out.print("Reservation ID: ");
            String id = sc.nextLine();

            System.out.print("Guest Name: ");
            String name = sc.nextLine();

            System.out.print("Room Type: ");
            String room = sc.nextLine();

            System.out.print("Amount: ");
            double amount = sc.nextDouble();
            sc.nextLine();

            Reservation reservation = new Reservation(id, name, room, amount);

            // Add to history (after confirmation)
            history.addReservation(reservation);
        }

        // Admin views history
        List<Reservation> allBookings = history.getAllReservations();

        reportService.displayAllBookings(allBookings);
        reportService.generateSummary(allBookings);

        sc.close();
    }
}