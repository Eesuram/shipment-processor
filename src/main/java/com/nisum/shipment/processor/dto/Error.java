package com.nisum.shipment.processor.dto;

/**
 * DTO for Error response
 *
 * @author Eswar Ambati
 */
public class Error {
    private String code;
    private String message;
    private String developerMessage;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }
}
