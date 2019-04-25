package com.nisum.shipment.processor.dto;

import java.util.List;

/**
 * DTO for success response
 *
 * @author Eswar Ambati
 */
public class Response {
    private List<ZipCodeRange> restrictedZipCodeRanges;

    public Response() {

    }

    public Response(List<ZipCodeRange> restrictedZipCodeRanges) {
        this.restrictedZipCodeRanges = restrictedZipCodeRanges;
    }

    public List<ZipCodeRange> getRestrictedZipCodeRanges() {
        return restrictedZipCodeRanges;
    }

    public void setRestrictedZipCodeRanges(List<ZipCodeRange> restrictedZipCodeRanges) {
        this.restrictedZipCodeRanges = restrictedZipCodeRanges;
    }
}
