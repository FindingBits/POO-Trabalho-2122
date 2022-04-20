import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashMap;

public class MainTest {
    @Test
    public void testNumberDevices() throws DivisionExistsExeption, DeviceExistsInDivision, CloneNotSupportedException {
        SmartSpeaker speaker = new SmartSpeaker("ABC",10.0,SmartDevice.Status.ON,50,"RFM","Sony");
        SmartBulb light = new SmartBulb("CBA",5.0, SmartDevice.Status.ON, SmartBulb.LightMode.WARM,10);
        SmartCamera camera = new SmartCamera("BAC",30.0,SmartDevice.Status.ON,720,20.0);

        Casa home = new Casa("Joao",999999999);
        home.addDivision("Sala");
        home.addDevice("Sala",speaker);
        home.addDevice("Sala",light);
        home.addDevice("Sala",camera);
        home.getAllDevices();
        assertEquals(3,home.numberOfDivices(),"Incorrect number of devices");
    }
    @Test
    public void testDivisionInHome() throws DivisionExistsExeption {
        Casa home = new Casa("Joao",999999999);
        home.addDivision("Sala");
        assertTrue(home.getDivisions().containsKey("Sala"),"Incorrect room creation");
    }
}
