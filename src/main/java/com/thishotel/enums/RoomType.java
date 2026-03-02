package com.thishotel.enums;

public enum RoomType {
    SINGLE(1),
    DOUBLE(2),
    TRIPLE(3),
    QUADRUPLE(4);

    private final int maxOccupancy;

    RoomType(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }
}
