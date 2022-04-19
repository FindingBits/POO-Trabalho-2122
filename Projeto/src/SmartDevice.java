import java.lang.String;
public abstract class SmartDevice {
    private String factoryID;

    public SmartDevice(String factoryID) {
        this.factoryID = factoryID;
    }

    public String getFactoryID() {
        return factoryID;
    }

    public void setFactoryID(String factoryID) {
        this.factoryID = factoryID;
    }
}
