package com.nisum.shipment.processor.exception;

import com.nisum.shipment.processor.dto.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

/**
 * Generic class to handle exception and return meaning Error response
 *
 * @author Eswar Ambati
 */
@ControllerAdvice
public class ShipmentExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(ShipmentExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    private static final String DEFAULT_ERROR_MESSAGE = "Unexpected error occurred";

    /**
     * Method to transform exception to Error response
     *
     * @param shipmentException
     * @return ResponseEntity with Error DTO
     */
    @ExceptionHandler(value = {ShipmentException.class})
    public ResponseEntity handleException(ShipmentException shipmentException) {
        Error error = new Error();
        error.setCode(shipmentException.getCode());
        error.setMessage(resolveMessage(shipmentException));

        // ShipmentException thrown on Input Validation failure, so setting Precondition failed status
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(error);
    }

    private String resolveMessage(ShipmentException e) {
        try {
            return messageSource.getMessage(e.getCode(), e.getArgs(), Locale.US);
        } catch (NoSuchMessageException nse) {
            log.error("Unable to resolve the message for code {}", e.getCode());
        }

        return DEFAULT_ERROR_MESSAGE;
    }
}
