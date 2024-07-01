package com.rc.booking.occupancy.api;

import com.rc.booking.room.dto.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OccupancyDto {
    private RoomType roomType;
    private int roomsCount;
    private BigDecimal sum;
}
