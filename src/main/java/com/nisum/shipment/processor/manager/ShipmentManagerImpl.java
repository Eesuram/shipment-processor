package com.nisum.shipment.processor.manager;

import com.nisum.shipment.processor.dto.ZipCodeRange;
import com.nisum.shipment.processor.util.RangeComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation class to ShipmentManager
 * @link com.nisum.shipment.processor.manager.ShipmentManager
 *
 * @author Eswar Ambati
 */
@Component
public class ShipmentManagerImpl implements ShipmentManager {
    private final Logger log = LoggerFactory.getLogger(ShipmentManagerImpl.class);

    @Override
    public List<ZipCodeRange> optimizeZipCodeRanges(List<ZipCodeRange> zipCodeRanges) {
        log.debug("START :: optimizing zipcode ranges");

        List<ZipCodeRange> optimizedRange = new ArrayList<>();
        if (CollectionUtils.isEmpty(zipCodeRanges)) {
            return optimizedRange;
        }

        // Sort the zipcode ranges by startValue
        Collections.sort(zipCodeRanges, new RangeComparator());

        for (int index = 0; index < zipCodeRanges.size(); ) {
            // Map the range start & end values to current by default
            ZipCodeRange result = new ZipCodeRange();
            result.setStart(zipCodeRanges.get(index).getStart());
            result.setEnd(zipCodeRanges.get(index).getEnd());

            // Ignore overlapping ranges and set the the endValue to result, and find the next index
            index = ignoreOverlapRanges(zipCodeRanges, result, index + 1);
            optimizedRange.add(result);
        }
        log.debug("END :: optimizing zipcode ranges");

        return optimizedRange;
    }

    private int ignoreOverlapRanges(List<ZipCodeRange> sortedRange, ZipCodeRange zipCodeRange, int nextIndex) {
        // If nextIndex is not available, keep the current endValue to result and return
        if (nextIndex >= sortedRange.size()) {
            return nextIndex;
        }

        int currEnd = Integer.parseInt(zipCodeRange.getEnd());
        int nextStart = Integer.parseInt(sortedRange.get(nextIndex).getStart());
        int nextEnd = Integer.parseInt(sortedRange.get(nextIndex).getEnd());

        // current endValue is less than next start value, keep the current endValue to result and return
        if (currEnd < nextStart) {
            return nextIndex;
        }

        // current endValue is less than or equal to next endValue, set next endValue to result, check for further ranges
        if (currEnd <= nextEnd) {
            zipCodeRange.setEnd(sortedRange.get(nextIndex).getEnd());
        }

        // current endValue is greater than next range values, ignore and move further ranges
        return ignoreOverlapRanges(sortedRange, zipCodeRange, nextIndex + 1);
    }
}
