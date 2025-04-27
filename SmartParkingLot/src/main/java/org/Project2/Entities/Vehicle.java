package org.Project2.Entities;

import org.Project2.enums.VehicleType;

public class Vehicle {
    private VehicleType type;

    public Vehicle(VehicleType type) {
        this.type = type;
    }

    public VehicleType getType() {
        return type;
    }
}
