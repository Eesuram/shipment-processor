package com.nisum.shipment.processor.exception;

/**
 * Customized Exception class to handle business validations
 *
 * @author Eswar Ambati
 */
public class ShipmentException extends RuntimeException {
    private String code;
    private String[] args;
    private String developerMessage;

    public ShipmentException(String code) {
        this.code = code;
    }

    public ShipmentException(String code, String[] args) {
        this.code = code;
        this.args = args;
    }

    public ShipmentException(String code, String[] args, String developerMessage) {
        this.code = code;
        this.args = args;
        this.developerMessage = developerMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }
}
