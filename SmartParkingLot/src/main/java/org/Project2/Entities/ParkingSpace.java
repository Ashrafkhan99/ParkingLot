package org.Project2.Entities;

import org.Project2.enums.SpaceSize;

public class ParkingSpace {
    private int spaceId;
    private SpaceSize spaceSize;
    private boolean occupied;

    public ParkingSpace(int spaceId, SpaceSize spaceSize) {
        this.spaceId = spaceId;
        this.spaceSize = spaceSize;
        this.occupied = false;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public SpaceSize getSpaceSize() {
        return spaceSize;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
