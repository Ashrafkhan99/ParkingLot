package org.Project2.Entities;

public class EntryPoint {
    private int id;
    private String location;
    private int parkingLotId;
    private int floorNumber;

    public EntryPoint(int id, String location, int parkingLotId, int floorNumber) {
        this.id = id;
        this.location = location;
        this.parkingLotId = parkingLotId;
        this.floorNumber = floorNumber;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public int getParkingLotId() {
        return parkingLotId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
