package com.rc.booking.occupancy;

import com.google.common.collect.ImmutableList;
import com.rc.booking.occupancy.api.OccupancyDto;
import com.rc.booking.occupancy.api.RoomsOccupancyFacade;
import com.rc.booking.room.dto.RoomType;
import com.rc.booking.occupancy.api.CalculateOccupancyParams;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class RoomsOccupancyService implements RoomsOccupancyFacade {

    private RoomsOccupancyDomainService roomsOccupancyService;

    public List<OccupancyDto> calculateRoomOccupancy(final CalculateOccupancyParams params) {
        final var occupancyRooms = roomsOccupancyService.calculateOccupancy(params);

        final ImmutableList.Builder<OccupancyDto> builder = ImmutableList.builder();

        if (occupancyRooms.getEconomyRoomsOccupancyCount() > 0) {
            builder.add(new OccupancyDto(RoomType.ECONOMY,
                    occupancyRooms.getEconomyRoomsOccupancyCount(),
                    occupancyRooms.getEconomyRoomsSummaryPrice()));
        }

        if (occupancyRooms.getPremiumRoomsOccupancyCount() > 0) {
            builder.add(new OccupancyDto(RoomType.PREMIUM,
                    occupancyRooms.getPremiumRoomsOccupancyCount(),
                    occupancyRooms.getPremiumRoomsSummaryPrice()));
        }

        return builder.build();
    }

}
