import java.util.Objects;
import java.lang.String;

public class SmartCamera extends SmartDevice {
    private int resolution;
    private double fileSize;
    protected double dailyConsumption;

    public SmartCamera(String factoryID,double mCost,Status status, int resolution, double fileSize) {
        super(factoryID,mCost,status);
        this.resolution = resolution;
        this.fileSize = fileSize;
        this.dailyConsumption=(double)resolution*fileSize/1000;
    }
    public SmartCamera(String factoryID,double mCost,Status status) {
        super(factoryID,mCost,status);
        this.resolution = 720;
        this.fileSize = 10;
        this.dailyConsumption=(double)this.getFileSize()*this.getResolution()/1000;
    }
    public SmartCamera(SmartCamera smart) {
        super(smart.getFactoryID(), smart.getMCost(),smart.getStatus());
        this.resolution = smart.getResolution();
        this.fileSize = smart.getFileSize();
        this.dailyConsumption=(double)smart.getFileSize()*smart.getResolution()/1000;
    }

    @Override
    public double getDailyConsumption() {
        return dailyConsumption;
    }

    public void setDailyConsumption(double dailyConsumption) {
        this.dailyConsumption = dailyConsumption;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartCamera that = (SmartCamera) o;
        return resolution == that.resolution && Double.compare(that.fileSize, fileSize) == 0;
    }
    @Override
    public int hashCode() {
        return Objects.hash(resolution, fileSize);
    }

    @Override
    public String toString() {
        return "SmartCamera{" +
                "device={" + super.toString() + "},"+
                "resolution=" + resolution +
                ", fileSize=" + fileSize +
                ", dailyConsumption=" + dailyConsumption +
                '}';
    }

    @Override
    public SmartCamera clone(){
        return new SmartCamera(this);
    }



}
