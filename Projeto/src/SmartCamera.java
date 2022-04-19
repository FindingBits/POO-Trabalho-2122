import java.util.Objects;
import java.lang.String;

public abstract class SmartCamera extends SmartDevice {
    private int resolution;
    private double fileSize;
    private double dailyConsumption;

    public SmartCamera(String factoryID, int resolution, double fileSize,double dailyConsumption) {
        super(factoryID);
        this.resolution = resolution;
        this.fileSize = fileSize;
        this.dailyConsumption=dailyConsumption;
    }
    public SmartCamera(String factoryID) {
        super(factoryID);
        this.resolution = 720;
        this.fileSize = 10;
        this.dailyConsumption=this.getFileSize()*this.getResolution();
    }
    public SmartCamera(SmartCamera smart) {
        super(smart.getFactoryID());
        this.resolution = smart.getResolution();
        this.fileSize = smart.getFileSize();
        this.dailyConsumption=smart.getFileSize()*smart.getResolution();
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartCamera that = (SmartCamera) o;
        return resolution == that.resolution && Double.compare(that.fileSize, fileSize) == 0;
    }

    public int hashCode() {
        return Objects.hash(resolution, fileSize);
    }

    public String toString() {
        return "SmartCamera{" +
                "resolution=" + resolution +
                ", fileSize=" + fileSize +
                '}';
    }

}
