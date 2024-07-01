package com.rc.booking.occupancy.rest;

import com.rc.booking.occupancy.api.CalculateOccupancyParams;
import com.rc.booking.occupancy.api.OccupancyDto;
import com.rc.booking.occupancy.api.RoomsOccupancyFacade;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
class RoomOccupancyController {

    private RoomsOccupancyFacade roomsOccupancyFacade;

    @PostMapping("/occupancy/calculate")
    public Flux<OccupancyDto> calculateOccupancy(@Valid @RequestBody final CalculateOccupancyParams params) {
        return Flux.fromIterable(roomsOccupancyFacade.calculateRoomOccupancy(params));
    }
}
