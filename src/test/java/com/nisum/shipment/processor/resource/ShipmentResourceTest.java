package com.nisum.shipment.processor.resource;

import com.nisum.shipment.processor.constant.ShipmentConstants;
import com.nisum.shipment.processor.dto.ZipCodeRange;
import com.nisum.shipment.processor.exception.ShipmentException;
import com.nisum.shipment.processor.manager.ShipmentManager;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * JUnit class of ShipmentResource
 * @link com.nisum.shipment.processor.resource.ShipmentResource
 *
 * @author Eswar Ambati
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ShipmentResource.class)
public class ShipmentResourceTest {
    @Autowired
    private MockMvc mockResource;

    @MockBean
    private ShipmentManager shipmentManager;

    @Test
    public void testHealthCheck() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/shipment/healthcheck");
        mockResource.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void testInvalidURI() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/shipment/test");
        mockResource.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testProcess() throws Exception {
        Mockito.when(shipmentManager.optimizeZipCodeRanges(Mockito.anyList())).thenReturn(Arrays.asList(new ZipCodeRange("94100", "94299")));

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/shipment/process")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"restrictedZipCodeRanges\": [{\"start\": \"94133\",\"end\": \"94133\"},{\"start\": \"94100\",\"end\": \"94299\"}]}");
        mockResource.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restrictedZipCodeRanges[0].start", Matchers.is("94100")))
                .andExpect(jsonPath("$.restrictedZipCodeRanges[0].end", Matchers.is("94299")));
    }

    @Test
    public void testProcess_InvalidInput() throws Exception {
        Mockito.when(shipmentManager.optimizeZipCodeRanges(Mockito.anyList())).thenThrow(new ShipmentException(ShipmentConstants.INVALID));

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/shipment/process")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"restrictedZipCodeRanges\": [{\"start\": \"AA133\",\"end\": \"94133\"},{\"start\": \"94100\",\"end\": \"94299\"}]}");
        mockResource.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", Matchers.is("002")));
    }
}
