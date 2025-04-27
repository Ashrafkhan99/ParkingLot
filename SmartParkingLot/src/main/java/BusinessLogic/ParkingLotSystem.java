package BusinessLogic;

import org.Project2.Entities.ParkingFloor;
import org.Project2.Entities.ParkingLot;
import org.Project2.Entities.ParkingSpace;
import org.Project2.Entities.Ticket;
import org.Project2.Exceptions.*;
import org.Project2.enums.VehicleType;

import java.time.LocalDateTime;
import java.util.*;

public class ParkingLotSystem {
    private ParkingLot parkingLot;
    private Map<Integer, Ticket> activeTickets;

    public ParkingLotSystem(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        this.activeTickets = new HashMap<>();
    }

    public Ticket checkIn(VehicleType vehicleType, int entryFloorNumber) throws NoSpaceAvailableException, InvalidVehicleTypeException, InvalidFloorNumberException, InvalidEntryPointException {
        if (vehicleType == null) {
            throw new InvalidVehicleTypeException("Vehicle type cannot be null.");
        }

        ParkingFloor entryFloor = parkingLot.getFloor(entryFloorNumber);
        if (entryFloor.getEntryPoint() == null) {
            throw new InvalidEntryPointException("Invalid entry point for floor: " + entryFloorNumber);
        }

        ParkingFloor nearestFloor = findNearestFloor(vehicleType);
        if (nearestFloor == null) {
            throw new NoSpaceAvailableException("No parking space available for " + vehicleType);
        }

        ParkingSpace availableSpace = nearestFloor.findAvailableSpace(vehicleType);
        if (availableSpace == null) {
            throw new NoSpaceAvailableException("No parking space available for " + vehicleType + " on floor " + nearestFloor.getFloorNumber());
        }

        availableSpace.setOccupied(true);
        double chargePerHour = getChargePerHour(vehicleType);
        Ticket ticket = new Ticket(vehicleType, nearestFloor.getFloorNumber(), availableSpace.getSpaceId(), chargePerHour);
        activeTickets.put(ticket.getId(), ticket);
        return ticket;
    }

    public void checkOut(int ticketId, int exitFloorNumber) throws InvalidTicketException, InvalidExitPointException, InvalidFloorNumberException {
        Ticket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            throw new InvalidTicketException("Invalid ticket ID: " + ticketId);
        }

        if (ticket.getFloorNumber() != exitFloorNumber) {
            throw new InvalidExitPointException("Vehicle must exit from the same floor it parked on. Expected floor: " + ticket.getFloorNumber() + ", but found: " + exitFloorNumber);
        }
        ParkingFloor exitFloor = parkingLot.getFloor(exitFloorNumber);
        if (exitFloor.getExitPoint() == null) {
            throw new InvalidExitPointException("Invalid exit point for floor: " + exitFloorNumber);
        }

        ParkingSpace space = null;
        for(ParkingSpace parkingSpace : exitFloor.getParkingSpaces()){
            if(parkingSpace.getSpaceId() == ticket.getSpaceNumber()){
                space = parkingSpace;
            }
        }
        if(space == null){
            throw new InvalidTicketException("Invalid ticket ID: " + ticketId);
        }
        space.setOccupied(false);

        LocalDateTime exitTime = LocalDateTime.now();
        long hoursParked = java.time.Duration.between(ticket.getEntryTimestamp(), exitTime).toHours();
        double amountToBePaid = hoursParked * ticket.getChargePerHour();
        ticket.setAmountToBePaid(amountToBePaid);

        System.out.println("Ticket ID: " + ticket.getId());
        System.out.println("Vehicle Type: " + ticket.getVehicleType());
        System.out.println("Floor Number: " + ticket.getFloorNumber());
        System.out.println("Space Number: " + ticket.getSpaceNumber());
        System.out.println("Entry Time: " + ticket.getEntryTimestamp());
        System.out.println("Exit Time: " + exitTime);
        System.out.println("Amount to be paid: " + ticket.getAmountToBePaid());

        activeTickets.remove(ticketId);
    }

    private ParkingFloor findNearestFloor(VehicleType vehicleType) {
        ParkingFloor nearestFloor = null;
        int maxAvailableSpaces = -1;

        List<ParkingFloor> floors = parkingLot.getParkingFloors();
        Collections.sort(floors, Comparator.comparingInt(ParkingFloor::getFloorNumber));

        for (ParkingFloor floor : floors) {
            int availableSpaces = floor.getAvailableSpacesCount(vehicleType);
            if (availableSpaces > maxAvailableSpaces) {
                maxAvailableSpaces = availableSpaces;
                nearestFloor = floor;
            }
        }
        return nearestFloor;
    }

    private double getChargePerHour(VehicleType vehicleType) {
        switch (vehicleType) {
            case BIKE:
                return 5.0;
            case CAR:
                return 10.0;
            case LORRY:
                return 20.0;
            default:
                return 0.0;
        }
    }
}
