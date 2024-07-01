package com.rc.booking.occupancy;

import com.google.common.collect.ImmutableMap;
import com.rc.booking.room.dto.RoomType;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
class RoomsOccupancy {

    private static final RoomsOccupancyDetails EMPTY = new RoomsOccupancyDetails(RoomType.NONE, new ArrayList<>());
    private Map<RoomType, RoomsOccupancyDetails> roomOccupancyDetailsMap;

    public RoomsOccupancy(final List<RoomsOccupancyDetails> data) {
        final var builder = ImmutableMap.<RoomType, RoomsOccupancyDetails>builder();
        data.forEach(details -> builder.put(details.getRoomType(), details));
        roomOccupancyDetailsMap = builder.build();
    }

    public int getEconomyRoomsOccupancyCount() {
        return getDetails(RoomType.ECONOMY).getRoomsCount();
    }

    public int getPremiumRoomsOccupancyCount() {
        return getDetails(RoomType.PREMIUM).getRoomsCount();
    }

    public BigDecimal getPremiumRoomsSummaryPrice() {
        return getDetails(RoomType.PREMIUM).getSum();
    }

    public BigDecimal getEconomyRoomsSummaryPrice() {
        return getDetails(RoomType.ECONOMY).getSum();
    }

    public RoomsOccupancyDetails getDetails(final RoomType type) {
        final var roomDetails = roomOccupancyDetailsMap.get(type);
        return roomDetails != null ? roomDetails : EMPTY;
    }
}

