package unit;

import com.tsystems.dto.StationDTO;
import com.tsystems.utils.HaversineUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(MockitoJUnitRunner.class)
public class HaversineUtilTest {

    @Test
    public void testCalculateDistanceInKm() {
        StationDTO stationFrom = new StationDTO(1,"stationFrom", 55.751244, 37.618423);
        StationDTO stationTo = new StationDTO(2,"stationTo", 51.67204, 39.1843);
        double result = HaversineUtil.calculateDistanceInKm(stationFrom, stationTo);

        double expected = 465.1;

        Assert.assertEquals(expected, result, 0.1);
    }

}
