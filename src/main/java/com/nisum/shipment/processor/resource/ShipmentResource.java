package com.nisum.shipment.processor.resource;

import com.nisum.shipment.processor.dto.Request;
import com.nisum.shipment.processor.dto.Response;
import com.nisum.shipment.processor.dto.ZipCodeRange;
import com.nisum.shipment.processor.manager.ShipmentManager;
import com.nisum.shipment.processor.util.ShipmentValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class to provide shipment rest end-points which consumes and produces JSON response
 *
 * @author Eswar Ambati
 */
@RestController
@RequestMapping("/shipment")
@Api(tags = "Shipment Resource")
public class ShipmentResource {
    private final Logger log = LoggerFactory.getLogger(ShipmentResource.class);

    @Autowired
    private ShipmentManager shipmentManager;

    /**
     * End-point (/api/shipment/healthcheck) to check application health, whether it is running or not
     *
     * @return ResponseEntity with Success message with Status 200
     */
    @ApiOperation(
            value = "Health check of the Shipping Resource",
            notes = "Service to check the health of Shipment resource"
    )
    @RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
    public ResponseEntity checkHealth() {
        log.debug("Running application health check");
        return ResponseEntity.ok("Application Running Successfully....");
    }

    /**
     * End-point (/api/shipment/process) to validate and process the shipment
     *
     * @param request - list of zipcode ranges
     * @return ResponseEntity with optimized list of zipcode ranges with status 200
     */
    @ApiOperation(
            value = "Process Shipment Request",
            notes = "Validate given range of restricted zipcodes and process the request"
    )
    @RequestMapping(value = "/process",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response process(@RequestBody Request request) {
        log.debug("Process Shipment Request");

        // Validate Input
        ShipmentValidator.validate(request);

        // Process the input and get optimized zipcode range
        List<ZipCodeRange> optimizeZipCodeRanges = shipmentManager.optimizeZipCodeRanges(request.getRestrictedZipCodeRanges());

        return ResponseEntity.ok(new Response(optimizeZipCodeRanges)).getBody();
    }


}
