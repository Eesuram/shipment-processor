package com.nisum.shipment.processor.manager;

import com.nisum.shipment.processor.dto.ZipCodeRange;

import java.util.List;

/**
 * Manager class to handle business logic for shipment processing
 *
 * @author Eswar Ambati
 */
public interface ShipmentManager {

    /**
     * Method to process the zipcode ranges and reduce to improve lookup performance
     *
     * @param zipCodeRanges
     * @return optimized list of zipcode ranges
     */
    List<ZipCodeRange> optimizeZipCodeRanges(List<ZipCodeRange> zipCodeRanges);
}
