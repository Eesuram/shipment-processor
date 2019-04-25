package com.nisum.shipment.processor.util;

import com.nisum.shipment.processor.dto.Request;
import com.nisum.shipment.processor.dto.ZipCodeRange;
import com.nisum.shipment.processor.exception.ShipmentException;
import org.junit.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;

import java.util.Arrays;

/**
 * JUnit class of ShipmentValidator
 * @link com.nisum.shipment.processor.util.ShipmentValidator
 *
 * @author Eswar Ambati
 */
public class ShipmentValidatorTest {

    @Test
    public void testValidate() {
        Request request = new Request();
        request.setRestrictedZipCodeRanges(Arrays.asList(new ZipCodeRange("94133", "94133"),
                new ZipCodeRange("94200", "94299"), new ZipCodeRange("94600", "94600")));

        ShipmentValidator.validate(request);
    }

    @Test(expected = ShipmentException.class)
    public void testValidate_Empty() {
        Request request = new Request();
        ShipmentValidator.validate(request);
    }

    @Test(expected = ShipmentException.class)
    public void testValidate_InvalidRange() {
        Request request = new Request();
        request.setRestrictedZipCodeRanges(Arrays.asList(new ZipCodeRange("94133", "94033"),
                new ZipCodeRange("94200", "94299"), new ZipCodeRange("94600", "94600")));

        ShipmentValidator.validate(request);
    }

    @Test(expected = ShipmentException.class)
    public void testValidate_InvalidStartZipcode() {
        Request request = new Request();
        request.setRestrictedZipCodeRanges(Arrays.asList(new ZipCodeRange("94133", "94033"),
                new ZipCodeRange("200", "94299"), new ZipCodeRange("94600", "94600")));

        ShipmentValidator.validate(request);
    }

    @Test(expected = ShipmentException.class)
    public void testValidate_InvalidEndZipcode() {
        Request request = new Request();
        request.setRestrictedZipCodeRanges(Arrays.asList(new ZipCodeRange("94133", "A4033"),
                new ZipCodeRange("94200", "94299"), new ZipCodeRange("94600", "94600")));

        ShipmentValidator.validate(request);
    }
}
