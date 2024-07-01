package com.rc.booking.occupancy;

import com.rc.booking.room.dto.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
class RoomsOccupancyDetails {
    @Getter
    private RoomType roomType;
    private List<BigDecimal> roomPricing;

    public int getRoomsCount() {
        return roomPricing.size();
    }

    public BigDecimal getSum() {
        return roomPricing.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
