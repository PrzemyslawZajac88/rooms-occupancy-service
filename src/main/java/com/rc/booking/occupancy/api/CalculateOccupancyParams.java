package com.rc.booking.occupancy.api;

import com.rc.booking.room.dto.RoomType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

@Data
@AllArgsConstructor
public class CalculateOccupancyParams {

    @NotEmpty
    private List<BigDecimal> customersPotentialPayment = Collections.emptyList();

    @NotEmpty
    private Map<RoomType, Integer> rooms = Collections.emptyMap();

    public int getRoomsCount(final RoomType roomType) {
        final var roomCount = rooms.get(roomType);
        return roomCount == null ? 0 : roomCount;
    }
}
