package org.Project2.Entities;

import org.Project2.Exceptions.InvalidFloorNumberException;

import java.util.List;

public class ParkingLot {
    private int id;
    private String location;
    private List<ParkingFloor> parkingFloors;

    public ParkingLot(int id, String location, List<ParkingFloor> parkingFloors) {
        this.id = id;
        this.location = location;
        this.parkingFloors = parkingFloors;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public List<ParkingFloor> getParkingFloors() {
        return parkingFloors;
    }

    public ParkingFloor getFloor(int floorNumber) throws InvalidFloorNumberException {
        for (ParkingFloor floor : parkingFloors) {
            if (floor.getFloorNumber() == floorNumber) {
                return floor;
            }
        }
        throw new InvalidFloorNumberException("Invalid floor number: " + floorNumber);
    }
}
