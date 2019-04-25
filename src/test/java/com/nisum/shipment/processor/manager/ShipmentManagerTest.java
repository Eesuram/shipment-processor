package com.nisum.shipment.processor.manager;

import com.nisum.shipment.processor.dto.ZipCodeRange;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * JUnit class of ShipmentManager
 * @link com.nisum.shipment.processor.manager.ShipmentManager
 *
 * @author Eswar Ambati
 */
@RunWith(SpringRunner.class)
public class ShipmentManagerTest {

    @Autowired
    private ShipmentManager shipmentManager;

    @TestConfiguration
    static class configureTestBeans {
        @Bean
        public ShipmentManager shipmentManager() {
            return new ShipmentManagerImpl();
        }
    }

    @Test
    public void testOptimizeZipCodeRanges_Empty() {
        List<ZipCodeRange> zipCodeRangeList = null;

        List<ZipCodeRange> optimizedRangeList = shipmentManager.optimizeZipCodeRanges(zipCodeRangeList);

        Assert.assertNotNull(optimizedRangeList);
        Assert.assertEquals(0, optimizedRangeList.size());
    }

    @Test
    public void testOptimizeZipCodeRanges_NoOverlap() {
        List<ZipCodeRange> zipCodeRangeList = Arrays.asList(new ZipCodeRange("94133", "94133"),
                new ZipCodeRange("94200", "94299"), new ZipCodeRange("94600", "94699"));

        List<ZipCodeRange> optimizedRangeList = shipmentManager.optimizeZipCodeRanges(zipCodeRangeList);

        Assert.assertNotNull(optimizedRangeList);
        Assert.assertEquals(3, optimizedRangeList.size());
        Assert.assertEquals("94133", optimizedRangeList.get(0).getStart());
        Assert.assertEquals("94133", optimizedRangeList.get(0).getEnd());
        Assert.assertEquals("94200", optimizedRangeList.get(1).getStart());
        Assert.assertEquals("94299", optimizedRangeList.get(1).getEnd());
        Assert.assertEquals("94600", optimizedRangeList.get(2).getStart());
        Assert.assertEquals("94699", optimizedRangeList.get(2).getEnd());
    }

    @Test
    public void testOptimizeZipCodeRanges_Overlap_SortedInput() {
        List<ZipCodeRange> zipCodeRangeList = Arrays.asList(new ZipCodeRange("94133", "94133"),
                new ZipCodeRange("94200", "94299"), new ZipCodeRange("94226", "94399"));

        List<ZipCodeRange> optimizedRangeList = shipmentManager.optimizeZipCodeRanges(zipCodeRangeList);

        Assert.assertNotNull(optimizedRangeList);
        Assert.assertEquals(2, optimizedRangeList.size());
        Assert.assertEquals("94133", optimizedRangeList.get(0).getStart());
        Assert.assertEquals("94133", optimizedRangeList.get(0).getEnd());
        Assert.assertEquals("94200", optimizedRangeList.get(1).getStart());
        Assert.assertEquals("94399", optimizedRangeList.get(1).getEnd());
    }

    @Test
    public void testOptimizeZipCodeRanges_Overlap() {
        List<ZipCodeRange> zipCodeRangeList = Arrays.asList(new ZipCodeRange("94001", "94100"),
                new ZipCodeRange("94080", "94105"), new ZipCodeRange("94090", "94200"),
                new ZipCodeRange("94220", "94300"), new ZipCodeRange("94402", "94430"),
                new ZipCodeRange("94099", "94206"), new ZipCodeRange("94200", "94400"),
                new ZipCodeRange("94420", "94440"), new ZipCodeRange("94450", "94500")
        );

        List<ZipCodeRange> optimizedRangeList = shipmentManager.optimizeZipCodeRanges(zipCodeRangeList);

        Assert.assertNotNull(optimizedRangeList);
        Assert.assertEquals(3, optimizedRangeList.size());
        Assert.assertEquals("94001", optimizedRangeList.get(0).getStart());
        Assert.assertEquals("94400", optimizedRangeList.get(0).getEnd());
        Assert.assertEquals("94402", optimizedRangeList.get(1).getStart());
        Assert.assertEquals("94440", optimizedRangeList.get(1).getEnd());
        Assert.assertEquals("94450", optimizedRangeList.get(2).getStart());
        Assert.assertEquals("94500", optimizedRangeList.get(2).getEnd());
    }
}
