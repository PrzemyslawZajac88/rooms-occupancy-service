package com.rc.booking.occupancy.api;

import java.util.List;

public interface RoomsOccupancyFacade {
    List<OccupancyDto> calculateRoomOccupancy(final CalculateOccupancyParams params);
}
