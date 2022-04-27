import java.lang.String;
import java.util.Objects;

public class SmartDevice {
    public enum Status {
        OFF,
        ON
    }
    private String factoryID;
    private double mCost;
    private Status status;
    protected long start;
    protected long finish;
    protected long timeElapsed;

    public SmartDevice(String factoryID,double mCost,Status status) {
        this.factoryID = factoryID;
        this.mCost = mCost;
        this.status = status;
        if(Objects.equals(status,Status.ON)) this.start=System.currentTimeMillis();
        else this.start=0;
        this.finish=0;
        this.timeElapsed=0;
    }
    public SmartDevice(SmartDevice device) {
        this.factoryID = device.getFactoryID();
        this.mCost = device.getMCost();
        this.status = device.getStatus();
        if(Objects.equals(device.getStatus(),Status.ON)) this.start=System.currentTimeMillis();
        else this.start=0;
        this.finish=0;
        this.timeElapsed=0;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getFinish() {
        return finish;
    }

    public void setFinish(long finish) {
        this.finish = finish;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public String getFactoryID() {
        return factoryID;
    }

    public double getMCost() {
        return mCost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setFactoryID(String factoryID) {
        this.factoryID = factoryID;
    }

    public void setMCost(double mCost) {
        this.mCost = mCost;
    }

    public static void turnON(SmartDevice device){
        if(device.getStatus()==SmartDevice.Status.OFF){
            device.setStatus(SmartDevice.Status.ON);
            device.setStart(System.currentTimeMillis());
        }
    }
    public static void turnOFF(SmartDevice device){
        if(device.getStatus()==SmartDevice.Status.ON){
            device.setStatus(SmartDevice.Status.OFF);
            device.setFinish(System.currentTimeMillis());
            device.setTimeElapsed(device.getFinish()-device.getStart());
        }
    }


    public String toString() {
        return "SmartDevice{" +
                "factoryID='" + factoryID + '\'' +
                ", mCost=" + mCost +
                ", status=" + status +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartDevice that = (SmartDevice) o;
        return Double.compare(that.mCost, mCost) == 0 && Objects.equals(factoryID, that.factoryID) && status == that.status;
    }

    public int hashCode() {
        return Objects.hash(factoryID, mCost, status);
    }

    public SmartDevice clone(){
        return new SmartDevice(this);
    }



}
