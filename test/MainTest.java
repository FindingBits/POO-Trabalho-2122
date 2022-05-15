import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashMap;

public class MainTest {
    @Test
    public void testNumberDevices() throws DivisionExistsExeption, DeviceExistsInDivision {
        SmartSpeaker speaker = new SmartSpeaker("ABC",10.0,SmartDevice.Status.ON,50,"RFM","Sony");
        SmartBulb light = new SmartBulb("CBA",5.0, SmartDevice.Status.ON, SmartBulb.LightMode.WARM,10);
        SmartCamera camera = new SmartCamera("BAC",30.0,SmartDevice.Status.ON,720,20.0);

        Casa home = new Casa("Joao",999999999,"EDP");
        home.addDivision("Sala");
        home.addDevice("Sala",speaker);
        home.addDevice("Sala",light);
        home.addDevice("Sala",camera);
        assertEquals(3,home.numberOfDevices(),"Incorrect number of devices");
    }
    @Test
    public void testDivisionInHome() throws DivisionExistsExeption {
        Casa home = new Casa("Joao",999999999,"EDP");
        home.addDivision("Sala");
        assertTrue(home.getDivisions().containsKey("Sala"),"Incorrect room creation");
    }

    @Test
    public void testTime() throws DivisionExistsExeption, DeviceExistsInDivision, InterruptedException {
        SmartSpeaker speaker = new SmartSpeaker("ABC",10.0,SmartDevice.Status.ON,50,"RFM","Sony");
        SmartBulb light = new SmartBulb("CBA",5.0, SmartDevice.Status.ON, SmartBulb.LightMode.WARM,10);
        SmartCamera camera = new SmartCamera("BAC",30.0,SmartDevice.Status.ON,720,20.0);

        Casa home = new Casa("Joao",999999999,"EDP");
        home.addDivision("Sala");
        home.addDevice("Sala",speaker);
        home.addDevice("Sala",light);
        home.addDevice("Sala",camera);
        sleep(1000);
        home.turnAllOFF();
        assertNotEquals(0,home.getTotalConsumption());
        System.out.println(home.getTotalConsumption());

    }
}
