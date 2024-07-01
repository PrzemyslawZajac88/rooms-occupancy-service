package com.rc.booking.occupancy;

import com.google.common.collect.ImmutableMap;
import com.rc.booking.occupancy.api.CalculateOccupancyParams;
import com.rc.booking.room.dto.RoomType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RoomsOccupancyDomainServiceTest {

    private final RoomsOccupancyDomainService rService = new RoomsOccupancyDomainService();

    @Test
    public void shouldDistributeOccupancy_1() {
        //given
        final var params = new CalculateOccupancyParams(getDefaultPaymentList(),
                ImmutableMap.of(RoomType.PREMIUM, 3, RoomType.ECONOMY, 3));

        //when
        final RoomsOccupancy roomsOccupancy = rService.calculateOccupancy(params);

        //then
        assertRoomOccupancy(roomsOccupancy.getDetails(RoomType.PREMIUM), 3, 738.0);
        assertRoomOccupancy(roomsOccupancy.getDetails(RoomType.ECONOMY), 3, 167.99);
    }

    @Test
    public void shouldDistributeOccupancy_2() {
        //given
        final var params = new CalculateOccupancyParams(getDefaultPaymentList(),
                ImmutableMap.of(RoomType.PREMIUM, 7, RoomType.ECONOMY, 5));

        //when
        final RoomsOccupancy roomsOccupancy = rService.calculateOccupancy(params);

        //then

        assertRoomOccupancy(roomsOccupancy.getDetails(RoomType.PREMIUM), 6, 1054.0);
        assertRoomOccupancy(roomsOccupancy.getDetails(RoomType.ECONOMY), 4, 189.99);
    }

    @Test
    public void shouldDistributeOccupancy_3() {
        //given
        final var params = new CalculateOccupancyParams(getDefaultPaymentList(),
                ImmutableMap.of(RoomType.PREMIUM, 2, RoomType.ECONOMY, 7));

        //when
        final RoomsOccupancy roomsOccupancy = rService.calculateOccupancy(params);

        //then
        assertRoomOccupancy(roomsOccupancy.getDetails(RoomType.PREMIUM), 2, 583.0);
        assertRoomOccupancy(roomsOccupancy.getDetails(RoomType.ECONOMY), 4, 189.99);
    }

    @Test
    public void shouldDistributeOccupancy_4() {
        //given
        final var params = new CalculateOccupancyParams(getDefaultPaymentList(),
                ImmutableMap.of(RoomType.PREMIUM, 7, RoomType.ECONOMY, 1));

        //when
        final RoomsOccupancy roomsOccupancy = rService.calculateOccupancy(params);

        //then
        assertRoomOccupancy(roomsOccupancy.getDetails(RoomType.PREMIUM), 7, 1153.99);
        assertRoomOccupancy(roomsOccupancy.getDetails(RoomType.ECONOMY), 1, 45.0);
    }

    private void assertRoomOccupancy(final RoomsOccupancyDetails roomsOccupancy, final int roomsCount, final double price) {
        assertThat(roomsOccupancy.getRoomsCount()).isEqualTo(roomsCount);
        assertThat(roomsOccupancy.getSum()).isEqualTo(price > 0 ? BigDecimal.valueOf(price): BigDecimal.ZERO);
    }

    private List<BigDecimal> getDefaultPaymentList() {
        return Stream.of(23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209)
                .map(Number::doubleValue)
                .map(BigDecimal::valueOf)
                .toList();
    }

    @Test
    public void shouldDistributeOccupancyWhenEmptyLists() {
        //given
        final var params = new CalculateOccupancyParams(getDefaultPaymentList(),
                ImmutableMap.of());

        //when
        final RoomsOccupancy roomsOccupancy = rService.calculateOccupancy(params);

        //then
        assertRoomOccupancy(roomsOccupancy.getDetails(RoomType.PREMIUM), 0, 0);
        assertRoomOccupancy(roomsOccupancy.getDetails(RoomType.ECONOMY), 0, 0);
    }

}