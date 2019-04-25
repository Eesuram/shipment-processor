package com.nisum.shipment.processor.dto;

import java.util.List;

/**
 * DTO for Input Request
 *
 * @author Eswar Ambati
 */
public class Request {
    private List<ZipCodeRange> restrictedZipCodeRanges;

    public List<ZipCodeRange> getRestrictedZipCodeRanges() {
        return restrictedZipCodeRanges;
    }

    public void setRestrictedZipCodeRanges(List<ZipCodeRange> restrictedZipCodeRanges) {
        this.restrictedZipCodeRanges = restrictedZipCodeRanges;
    }
}
