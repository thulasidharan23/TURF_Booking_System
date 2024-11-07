package turf_booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Booking {
    private String booking_candidate;
    private int turf_id;
    private Date booking_date;
    private int slot;

    public Booking(String booking_candidate, int turf_id, Date booking_date, int slot) {
        this.booking_candidate = booking_candidate;
        this.turf_id = turf_id;
        this.booking_date = booking_date;
        this.slot = slot;
    }

    public boolean isAvailable() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookings WHERE turf_id = ? AND booking_date = ? AND slot = ?")) {
            stmt.setInt(1, turf_id);
            stmt.setDate(2, new java.sql.Date(booking_date.getTime()));
            stmt.setInt(3, slot);
            ResultSet rs = stmt.executeQuery();
            return !rs.next();  // If no results, the slot is available
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void saveBooking() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO bookings (booking_candidate, turf_id, booking_date, slot) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, booking_candidate);
            stmt.setInt(2, turf_id);
            stmt.setDate(3, new java.sql.Date(booking_date.getTime()));
            stmt.setInt(4, slot);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewBookings(String booking_candidate) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookings WHERE booking_candidate = ?")) {
            stmt.setString(1, booking_candidate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Booking ID: " + rs.getInt("booking_id"));
                System.out.println("Turf ID: " + rs.getInt("turf_id"));
                System.out.println("Booking Date: " + rs.getDate("booking_date"));
                System.out.println("Slot: " + rs.getInt("slot"));
                System.out.println("------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cancelBooking(String booking_candidate, int turf_id, Date booking_date, int slot) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM bookings WHERE booking_candidate = ? AND turf_id = ? AND booking_date = ? AND slot = ?")) {
            stmt.setString(1, booking_candidate);
            stmt.setInt(2, turf_id);
            stmt.setDate(3, new java.sql.Date(booking_date.getTime()));
            stmt.setInt(4, slot);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Booking canceled successfully.");
            } else {
                System.out.println("No matching booking found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
