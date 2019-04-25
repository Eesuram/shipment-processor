package com.nisum.shipment.processor.util;

import com.nisum.shipment.processor.dto.ZipCodeRange;

import java.util.Comparator;

/**
 * Comparator class to sort the ZipCodeRanage list in ascending order by start value
 *
 * @author Eswar Ambati
 */
public class RangeComparator implements Comparator<ZipCodeRange> {

    /**
     * Method to compare the start value of ZipCodeRange and sort by ascending order
     *
     * @param current ZipCodeRange
     * @param next ZipCodeRange
     * @return
     */
    public int compare(ZipCodeRange current, ZipCodeRange next) {
        int start = Integer.parseInt(current.getStart());
        int end = Integer.parseInt(next.getStart());

        if (start < end) {
            return -1;
        } else {
            return 1;
        }
    }
}
