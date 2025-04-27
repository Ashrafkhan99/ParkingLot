package org.Project2.Entities;

import org.Project2.enums.VehicleType;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Ticket {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private VehicleType vehicleType;
    private int floorNumber;
    private int spaceNumber;
    private LocalDateTime entryTimestamp;
    private double chargePerHour;
    private double amountToBePaid;

    public Ticket(VehicleType vehicleType, int floorNumber, int spaceNumber, double chargePerHour) {
        this.id = count.incrementAndGet();
        this.vehicleType = vehicleType;
        this.floorNumber = floorNumber;
        this.spaceNumber = spaceNumber;
        this.entryTimestamp = LocalDateTime.now();
        this.chargePerHour = chargePerHour;
        this.amountToBePaid = 0;
    }

    public int getId() {
        return id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getSpaceNumber() {
        return spaceNumber;
    }

    public LocalDateTime getEntryTimestamp() {
        return entryTimestamp;
    }

    public double getChargePerHour() {
        return chargePerHour;
    }

    public double getAmountToBePaid() {
        return amountToBePaid;
    }

    public void setAmountToBePaid(double amountToBePaid) {
        this.amountToBePaid = amountToBePaid;
    }
}
