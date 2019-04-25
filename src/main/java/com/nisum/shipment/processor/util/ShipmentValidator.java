package com.nisum.shipment.processor.util;

import com.nisum.shipment.processor.constant.ShipmentConstants;
import com.nisum.shipment.processor.dto.Request;
import com.nisum.shipment.processor.dto.ZipCodeRange;
import com.nisum.shipment.processor.exception.ShipmentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.regex.Pattern;

/**
 * Validator class to check the input and throw appropriate error message
 *
 * @author Eswar Ambati
 */
public class ShipmentValidator {
    private static final Logger log = LoggerFactory.getLogger(ShipmentValidator.class);

    /**
     * Method to validate input request having list of zipcode ranges
     *
     * @param request
     * @throws ShipmentException
     */
    public static void validate(Request request) throws ShipmentException {
        log.debug("START :: Request Validation");
        if (CollectionUtils.isEmpty(request.getRestrictedZipCodeRanges())) {
            throw new ShipmentException(ShipmentConstants.EMPTY, new String[]{"ZipCode Range Input"});
        }

        request.getRestrictedZipCodeRanges().stream().forEach(zipCodeRange -> {
            validateZipCode(zipCodeRange.getStart());
            validateZipCode(zipCodeRange.getEnd());
            validateRange(zipCodeRange);
        });
        log.debug("END :: Request Validation");
    }

    /**
     * Method to validate zipcode range (startValue should be greater or equal to endValue)
     * @param zipCodeRange
     */
    private static void validateRange(ZipCodeRange zipCodeRange) {
        int startValue = Integer.parseInt(zipCodeRange.getStart());
        int endValue = Integer.parseInt(zipCodeRange.getEnd());

        if (startValue > endValue) {
            log.error("ZipCode Range is Invalid {}", zipCodeRange.toString());
            throw new ShipmentException(ShipmentConstants.INVALID_RANGE, new String[]{zipCodeRange.toString()});
        }
    }

    /**
     * Method to validate zipcode, should have 5 digits
     * @param zipcode
     */
    private static void validateZipCode(String zipcode) {
        boolean valid = Pattern.matches(ShipmentConstants.ZIPCODE_PATTERN, zipcode);
        if (!valid) {
            log.error("ZipCode {} is not valid", zipcode);
            throw new ShipmentException(ShipmentConstants.INVALID, new String[]{"ZipCode", zipcode});
        }
    }
}
