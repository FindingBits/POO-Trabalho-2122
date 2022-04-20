import java.util.ArrayList;
import java.util.HashMap;

public class Debug {
    public static void main(String[] args){

        SmartSpeaker speaker = new SmartSpeaker("ABC",10.0,SmartDevice.Status.ON,50,"RFM","Sony");
        SmartBulb light = new SmartBulb("CBA",5.0, SmartDevice.Status.ON, SmartBulb.LightMode.WARM,10);
        SmartCamera camera = new SmartCamera("BAC",30.0,SmartDevice.Status.ON,720,20.0);

        ArrayList<SmartDevice> devices = new ArrayList<SmartDevice>();
        devices.add(speaker);
        devices.add(light);
        devices.add(camera);

        HashMap<String,ArrayList<SmartDevice>> divisions = new HashMap<String,ArrayList<SmartDevice>>();
        divisions.put("Sala",devices);

        Casa home = new Casa("Joao",999999999,divisions);
        home.turnAllOFF();
        home.turnSpecificON("ABC");
        System.out.println(home);
    }
}
