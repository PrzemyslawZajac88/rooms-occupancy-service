package com.rc.booking.occupancy;

import com.rc.booking.occupancy.api.CalculateOccupancyParams;
import com.rc.booking.room.dto.RoomType;
import com.rc.booking.shared.ddd.DomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@DomainService
class RoomsOccupancyDomainService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomsOccupancyDomainService.class);

    private final BigDecimal premiumPriceThreshold = BigDecimal.valueOf(100);

    public RoomsOccupancy calculateOccupancy(final CalculateOccupancyParams params) {
        final var premiumPricing = new ArrayList<BigDecimal>();
        final var economyPricing = new ArrayList<BigDecimal>();

        var premiumRoomCountLeft = params.getRoomsCount(RoomType.PREMIUM);
        var economyRoomCount = params.getRoomsCount(RoomType.ECONOMY);

        final var potentialPaymentsFromHighest = new ArrayList<>(params.getCustomersPotentialPayment());
        sortFromHighest(potentialPaymentsFromHighest);

        final var upgradeMaxPointer = potentialPaymentsFromHighest.size() - economyRoomCount;

        int pointer;
        for (pointer = 0; pointer < potentialPaymentsFromHighest.size(); pointer++) {
            final var potentialFee = potentialPaymentsFromHighest.get(pointer);

            if (isPremiumPrice(potentialFee)) {
                if (premiumRoomCountLeft > 0) {
                    premiumPricing.add(potentialFee);
                    premiumRoomCountLeft--;
                } else {
                    // omit customers
                }

            } else if (premiumRoomCountLeft > 0 && pointer < upgradeMaxPointer) {

                premiumPricing.add(potentialFee);
                premiumRoomCountLeft--;

            } else if (economyRoomCount > 0) {
                economyPricing.add(potentialFee);
                economyRoomCount--;
            } else break;
        }

        final var roomsOccupancyDetails = List.of(
                new RoomsOccupancyDetails(RoomType.ECONOMY, economyPricing),
                new RoomsOccupancyDetails(RoomType.PREMIUM, premiumPricing)
        );

        return new RoomsOccupancy(roomsOccupancyDetails);
    }

    private boolean isPremiumPrice(BigDecimal potentialFee) {
        return potentialFee.compareTo(premiumPriceThreshold) >= 0;
    }

    /**
     * sorting and reversing => Sorting from highest
     */
    private void sortFromHighest(final List<BigDecimal> customersPotentialPayment) {
        customersPotentialPayment.sort(Comparator.reverseOrder());
    }

}
