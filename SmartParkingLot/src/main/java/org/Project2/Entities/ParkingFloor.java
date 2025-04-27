package org.Project2.Entities;

import org.Project2.enums.SpaceSize;
import org.Project2.enums.VehicleType;

import java.util.List;

public class ParkingFloor {
    private int floorNumber;
    private List<ParkingSpace> parkingSpaces;
    private EntryPoint entryPoint;
    private ExitPoint exitPoint;

    public ParkingFloor(int floorNumber, List<ParkingSpace> parkingSpaces, EntryPoint entryPoint, ExitPoint exitPoint) {
        this.floorNumber = floorNumber;
        this.parkingSpaces = parkingSpaces;
        this.entryPoint = entryPoint;
        this.exitPoint = exitPoint;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public EntryPoint getEntryPoint() {
        return entryPoint;
    }

    public ExitPoint getExitPoint() {
        return exitPoint;
    }

    public ParkingSpace findAvailableSpace(VehicleType vehicleType) {
        SpaceSize requiredSize = getSpaceSize(vehicleType);
        for (ParkingSpace space : parkingSpaces) {
            if (space.getSpaceSize() == requiredSize)
                if (!space.isOccupied()) {
                    return space;
                }
        }
        return null;
    }

    private SpaceSize getSpaceSize(VehicleType vehicleType) {
        switch (vehicleType) {
            case BIKE:
                return SpaceSize.SMALL;
            case CAR:
                return SpaceSize.MEDIUM;
            case LORRY:
                return SpaceSize.LARGE;
            default:
                return null;
        }
    }
    public int getAvailableSpacesCount(VehicleType vehicleType) {
        SpaceSize requiredSize = getSpaceSize(vehicleType);
        int count = 0;
        for (ParkingSpace space : parkingSpaces) {
            if (!space.isOccupied() && space.getSpaceSize() == requiredSize) {
                count++;
            }
        }
        return count;
    }
}
