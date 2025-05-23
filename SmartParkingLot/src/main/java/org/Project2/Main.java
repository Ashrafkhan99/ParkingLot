package org.Project2;

import BusinessLogic.ParkingLotSystem;
import org.Project2.Entities.*;
import org.Project2.Exceptions.*;
import org.Project2.enums.SpaceSize;
import org.Project2.enums.VehicleType;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Create parking spaces for floor 1
        List<ParkingSpace> floor1Spaces = Arrays.asList(
                new ParkingSpace(1, SpaceSize.SMALL),
                new ParkingSpace(2, SpaceSize.MEDIUM),
                new ParkingSpace(3, SpaceSize.LARGE),
                new ParkingSpace(4, SpaceSize.SMALL),
                new ParkingSpace(5, SpaceSize.MEDIUM)
        );
        // Create parking spaces for floor 2
        List<ParkingSpace> floor2Spaces = Arrays.asList(
                new ParkingSpace(1, SpaceSize.SMALL),
                new ParkingSpace(2, SpaceSize.MEDIUM),
                new ParkingSpace(3, SpaceSize.LARGE),
                new ParkingSpace(4, SpaceSize.SMALL),
                new ParkingSpace(5, SpaceSize.MEDIUM),
                new ParkingSpace(6, SpaceSize.LARGE)
        );

        // Create entry and exit points for floor 1
        EntryPoint floor1Entry = new EntryPoint(1, "Entry 1", 1, 1);
        ExitPoint floor1Exit = new ExitPoint(1, "Exit 1", 1, 1);
        // Create entry and exit points for floor 2
        EntryPoint floor2Entry = new EntryPoint(2, "Entry 2", 1, 2);
        ExitPoint floor2Exit = new ExitPoint(2, "Exit 2", 1, 2);

        // Create parking floors
        ParkingFloor floor1 = new ParkingFloor(1, floor1Spaces, floor1Entry, floor1Exit);
        ParkingFloor floor2 = new ParkingFloor(2, floor2Spaces, floor2Entry, floor2Exit);

        // Create parking lot
        ParkingLot parkingLot = new ParkingLot(1, "City Center", Arrays.asList(floor1, floor2));

        // Create parking lot system
        ParkingLotSystem system = new ParkingLotSystem(parkingLot);

        // --- Success Scenarios for Fee Calculation ---
        System.out.println("\n--- Success Scenarios for Fee Calculation ---");

        // Scenario 1: Bike parked for 1 hour
        System.out.println("\n--- Scenario 1: Bike parked for 1 hour ---");
        try {
            Ticket bikeTicket = system.checkIn(VehicleType.BIKE, 1);
            System.out.println("Bike Ticket generated: " + bikeTicket.getId());

            // Simulate 1 hour passing
            simulateTimePassing(bikeTicket, 1);

            system.checkOut(bikeTicket.getId(), 1);
            // Expected: 5.0
        } catch (Exception e) {
            System.err.println("Error in Scenario 1: " + e.getMessage());
        }

        // Scenario 2: Car parked for 3 hours
        System.out.println("\n--- Scenario 2: Car parked for 3 hours ---");
        try {
            Ticket carTicket = system.checkIn(VehicleType.CAR, 2);
            System.out.println("Car Ticket generated: " + carTicket.getId());

            // Simulate 3 hours passing
            simulateTimePassing(carTicket, 3);

            system.checkOut(carTicket.getId(), 2);
            // Expected: 30.0
        } catch (Exception e) {
            System.err.println("Error in Scenario 2: " + e.getMessage());
        }

        // Scenario 3: Lorry parked for 5 hours
        System.out.println("\n--- Scenario 3: Lorry parked for 5 hours ---");
        try {
            Ticket lorryTicket = system.checkIn(VehicleType.LORRY, 1);
            System.out.println("Lorry Ticket generated: " + lorryTicket.getId());

            // Simulate 5 hours passing
            simulateTimePassing(lorryTicket, 5);

            system.checkOut(lorryTicket.getId(), 1);
            // Expected: 100.0
        } catch (Exception e) {
            System.err.println("Error in Scenario 3: " + e.getMessage());
        }

        // Scenario 4: Bike parked for 0 hours
        System.out.println("\n--- Scenario 4: Bike parked for 0 hours ---");
        try {
            Ticket bikeTicket = system.checkIn(VehicleType.BIKE, 1);
            System.out.println("Bike Ticket generated: " + bikeTicket.getId());

            // Simulate 0 hours passing
            simulateTimePassing(bikeTicket, 0);

            system.checkOut(bikeTicket.getId(), 1);
            // Expected: 0.0
        } catch (Exception e) {
            System.err.println("Error in Scenario 4: " + e.getMessage());
        }

        // Scenario 5: Car parked for 1.5 hours
        System.out.println("\n--- Scenario 5: Car parked for 1.5 hours ---");
        try {
            Ticket carTicket = system.checkIn(VehicleType.CAR, 2);
            System.out.println("Car Ticket generated: " + carTicket.getId());

            // Simulate 1.5 hours passing
            simulateTimePassing(carTicket, 1);

            // Simulate 30 minutes passing
            simulateTimePassing(carTicket, 0,30);

            system.checkOut(carTicket.getId(), 2);
            // Expected: 10.0
        } catch (Exception e) {
            System.err.println("Error in Scenario 5: " + e.getMessage());
        }

        // Scenario 6: Lorry parked for 2.5 hours
        System.out.println("\n--- Scenario 6: Lorry parked for 2.5 hours ---");
        try {
            Ticket lorryTicket = system.checkIn(VehicleType.LORRY, 1);
            System.out.println("Lorry Ticket generated: " + lorryTicket.getId());

            // Simulate 2.5 hours passing
            simulateTimePassing(lorryTicket, 2);
            simulateTimePassing(lorryTicket, 0,30);

            system.checkOut(lorryTicket.getId(), 1);
            // Expected: 40.0
        } catch (Exception e) {
            System.err.println("Error in Scenario 6: " + e.getMessage());
        }

        // --- Normal Check-in and Check-out (to set up some initial state) ---
        try {
            Ticket ticket1 = system.checkIn(VehicleType.BIKE, 1);
            System.out.println("Ticket 1 generated: " + ticket1.getId() + " Floor: " + ticket1.getFloorNumber() + " Space: " + ticket1.getSpaceNumber());
            Ticket ticket2 = system.checkIn(VehicleType.CAR, 1);
            System.out.println("Ticket 2 generated: " + ticket2.getId() + " Floor: " + ticket2.getFloorNumber() + " Space: " + ticket2.getSpaceNumber());
            Ticket ticket3 = system.checkIn(VehicleType.LORRY, 1);
            System.out.println("Ticket 3 generated: " + ticket3.getId() + " Floor: " + ticket3.getFloorNumber() + " Space: " + ticket3.getSpaceNumber());
            Ticket ticket4 = system.checkIn(VehicleType.BIKE, 1);
            System.out.println("Ticket 4 generated: " + ticket4.getId() + " Floor: " + ticket4.getFloorNumber() + " Space: " + ticket4.getSpaceNumber());
            Ticket ticket5 = system.checkIn(VehicleType.CAR, 2);
            System.out.println("Ticket 5 generated: " + ticket5.getId() + " Floor: " + ticket5.getFloorNumber() + " Space: " + ticket5.getSpaceNumber());
            Ticket ticket6 = system.checkIn(VehicleType.LORRY, 2);
            System.out.println("Ticket 6 generated: " + ticket6.getId() + " Floor: " + ticket6.getFloorNumber() + " Space: " + ticket6.getSpaceNumber());

            // Check out vehicles
            system.checkOut(ticket1.getId(), 1);
            system.checkOut(ticket2.getId(), 1);
            system.checkOut(ticket3.getId(), 1);
            system.checkOut(ticket4.getId(), 1);
            system.checkOut(ticket5.getId(), 2);
            system.checkOut(ticket6.getId(), 2);
        } catch (NoSpaceAvailableException | InvalidVehicleTypeException | InvalidFloorNumberException |
                 InvalidEntryPointException | InvalidTicketException | InvalidExitPointException e) {
            System.err.println("Error during initial setup: " + e.getMessage());
        }

        // 1. No Space Available
        System.out.println("\n--- Testing No Space Available ---");
        try {
            // Fill up all spaces on floor 1
            system.checkIn(VehicleType.BIKE, 1);
            system.checkIn(VehicleType.CAR, 1);
            system.checkIn(VehicleType.LORRY, 1);
            system.checkIn(VehicleType.BIKE, 1);
            system.checkIn(VehicleType.CAR, 1);

            // Try to park one more vehicle on floor 1 (should fail)
            system.checkIn(VehicleType.BIKE, 1);
        } catch (NoSpaceAvailableException | InvalidVehicleTypeException | InvalidFloorNumberException |
                 InvalidEntryPointException e) {
            System.err.println("Expected Error (No Space Available): " + e.getMessage());
        }

        // 2. Invalid Vehicle Type
        System.out.println("\n--- Testing Invalid Vehicle Type ---");
        try {
            system.checkIn(null, 1); // Passing null as VehicleType
        } catch (InvalidVehicleTypeException | NoSpaceAvailableException | InvalidFloorNumberException |
                 InvalidEntryPointException e) {
            System.err.println("Expected Error (Invalid Vehicle Type): " + e.getMessage());
        }

        // 3. Invalid Floor Number (Check-in)
        System.out.println("\n--- Testing Invalid Floor Number (Check-in) ---");
        try {
            system.checkIn(VehicleType.CAR, 99); // Floor 99 doesn't exist
        } catch (InvalidFloorNumberException | NoSpaceAvailableException | InvalidVehicleTypeException |
                 InvalidEntryPointException e) {
            System.err.println("Expected Error (Invalid Floor Number - Check-in): " + e.getMessage());
        }

        // 5. Invalid Ticket ID
        System.out.println("\n--- Testing Invalid Ticket ID ---");
        try {
            system.checkOut(999, 1); // Ticket 999 doesn't exist
        } catch (InvalidTicketException | InvalidExitPointException | InvalidFloorNumberException e) {
            System.err.println("Expected Error (Invalid Ticket ID): " + e.getMessage());
        }

        // 6. Invalid Exit Floor
        System.out.println("\n--- Testing Invalid Exit Floor ---");
        try {
            Ticket ticket8 = system.checkIn(VehicleType.BIKE, 1); // Park on floor 1
            system.checkOut(ticket8.getId(), 2); // Try to exit from floor 2
        } catch (InvalidExitPointException | NoSpaceAvailableException | InvalidVehicleTypeException |
                 InvalidFloorNumberException | InvalidTicketException | InvalidEntryPointException e) {
            System.err.println("Expected Error (Invalid Exit Floor): " + e.getMessage());
        }

        // 4. Invalid Entry Point
        System.out.println("\n--- Testing Invalid Entry Point ---");
        try {
            // Create a floor without an entry point
            List<ParkingSpace> floor3Spaces = List.of(new ParkingSpace(1, SpaceSize.SMALL));
            ParkingFloor floor3 = new ParkingFloor(3, floor3Spaces, null, floor2Exit); // No entry point
            parkingLot.getParkingFloors().add(floor3);

            system.checkIn(VehicleType.BIKE, 3); // Try to check in on floor 3
        } catch (InvalidEntryPointException | NoSpaceAvailableException | InvalidVehicleTypeException |
                 InvalidFloorNumberException e) {
            System.err.println("Expected Error (Invalid Entry Point): " + e.getMessage());
        }

        // 7. Invalid Exit Point
        System.out.println("\n--- Testing Invalid Exit Point ---");
        try {
            // Create a floor without an exit point
            List<ParkingSpace> floor4Spaces = List.of(new ParkingSpace(1, SpaceSize.SMALL));
            ParkingFloor floor4 = new ParkingFloor(4, floor4Spaces, floor2Entry, null); // No exit point
            parkingLot.getParkingFloors().add(floor4);
            Ticket ticket7 = system.checkIn(VehicleType.BIKE, 4);
            system.checkOut(ticket7.getId(), 4); // Try to check out from floor 4
        } catch (InvalidExitPointException | NoSpaceAvailableException | InvalidVehicleTypeException |
                 InvalidFloorNumberException | InvalidTicketException | InvalidEntryPointException e) {
            System.err.println("Expected Error (Invalid Exit Point): " + e.getMessage());
        }
    }

    // Helper method to simulate time passing
    private static void simulateTimePassing(Ticket ticket, int hours) {
        LocalDateTime newEntryTime = ticket.getEntryTimestamp().minusHours(hours);
        ticket.setEntryTimestamp(newEntryTime);
    }

    // Helper method to simulate time passing
    private static void simulateTimePassing(Ticket ticket, int hours, int minutes) {
        LocalDateTime newEntryTime = ticket.getEntryTimestamp().minusHours(hours).minusMinutes(minutes);
        ticket.setEntryTimestamp(newEntryTime);
    }
}
