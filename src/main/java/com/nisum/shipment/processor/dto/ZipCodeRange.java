package com.nisum.shipment.processor.dto;

/**
 * POJO to hold zipcode range (start and end values)
 *
 * @author Eswar Ambati
 */
public class ZipCodeRange {
    private String start;
    private String end;

    public ZipCodeRange() {

    }

    public ZipCodeRange(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(start).append("-").append(end);
        return stringBuilder.toString();
    }
}
