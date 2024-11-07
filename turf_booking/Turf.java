package turf_booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Turf {
    private int turf_id;
    private String turf_name;
    private String address;
    private long phone_no;
    private String owner_name;
    private int slot_count;
    private int price;

    public Turf(int turf_id, String turf_name, String address, long phone_no, String owner_name, int slot_count, int price) {
        this.turf_id = turf_id;
        this.turf_name = turf_name;
        this.address = address;
        this.phone_no = phone_no;
        this.owner_name = owner_name;
        this.slot_count = slot_count;
        this.price = price;
    }

    public static ArrayList<Turf> loadTurfs() {
        ArrayList<Turf> turfs = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM turfs")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Turf turf = new Turf(
                        rs.getInt("turf_id"),
                        rs.getString("turf_name"),
                        rs.getString("address"),
                        rs.getLong("phone_no"),
                        rs.getString("owner_name"),
                        rs.getInt("slot_count"),
                        rs.getInt("price")
                );
                turfs.add(turf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return turfs;
    }

    public void display() {
        System.out.println("Turf ID: " + turf_id);
        System.out.println("Turf Name: " + turf_name);
        System.out.println("Owner: " + owner_name);
        System.out.println("Address: " + address);
        System.out.println("Phone Number: " + phone_no);
        System.out.println("Slots Available: " + slot_count);
        System.out.println("Price: " + price);
    }

    public int getTurfID() {
        return turf_id;
    }
}
