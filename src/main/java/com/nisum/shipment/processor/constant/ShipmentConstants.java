package com.nisum.shipment.processor.constant;

/**
 * Class to hold constants used in Shipment application
 *
 * @author Eswar Ambati
 */
public interface ShipmentConstants {

    // Message Constants
    String EMPTY = "001";
    String INVALID = "002";
    String INVALID_RANGE = "003";

    // Pattern Constants
    String ZIPCODE_PATTERN = "^[0-9]{5}$";
}
