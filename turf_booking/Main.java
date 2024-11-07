package turf_booking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    static ArrayList<Turf> turfs = Turf.loadTurfs();


    public static void displayTurfs() {
        for (Turf turf : turfs) {
            turf.display();
            System.out.println("------------------------------------");
        }
    }

    public static void bookTurf(Scanner sc) {
        System.out.println("Enter your name:");
        String name = sc.next();
        sc.nextLine();

        displayTurfs();
        System.out.println("Enter Turf ID to book:");
        int turf_id = sc.nextInt();

        System.out.println("Enter booking date (dd-MM-yyyy):");
        String dateInput = sc.next();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date booking_date;
        try {
            booking_date = sdf.parse(dateInput);
        } catch (ParseException e) {
            System.out.println("Invalid date format.");
            return;
        }

        System.out.println("Enter the slot number (1-4):");
        int slot = sc.nextInt();

        Booking booking = new Booking(name, turf_id, booking_date, slot);
        if (booking.isAvailable()) {
            booking.saveBooking();
            System.out.println("Booking successful!");
        } else {
            System.out.println("Slot is not available.");
        }
    }

    // View bookings for a user
    public static void viewBookings(Scanner sc) {
        System.out.println("Enter your name to view bookings:");
        String name = sc.next();
        Booking.viewBookings(name);
    }

    // Cancel a booking
    public static void cancelBooking(Scanner sc) {
        System.out.println("Enter your name:");
        String name = sc.next();

        System.out.println("Enter Turf ID:");
        int turf_id = sc.nextInt();

        System.out.println("Enter booking date (dd-MM-yyyy):");
        String dateInput = sc.next();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date booking_date;
        try {
            booking_date = sdf.parse(dateInput);
        } catch (ParseException e) {
            System.out.println("Invalid date format.");
            return;
        }

        System.out.println("Enter the slot number (1-4):");
        int slot = sc.nextInt();

        Booking.cancelBooking(name, turf_id, booking_date, slot);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Welcome to Turf Booking System!");
            System.out.println("1. Book a Turf");
            System.out.println("2. View Bookings");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    bookTurf(sc);
                    break;
                case 2:
                    viewBookings(sc);
                    break;
                case 3:
                    cancelBooking(sc);
                    break;
                case 4:
                    System.out.println("Exiting... Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        sc.close();
    }
}

