import java.lang.String;
import java.util.Objects;

public class SmartBulb extends SmartDevice{

    private enum Status {
        OFF,
        ON
    }
    private enum LightMode {
        NEUTRAL,
        WARM,
        COLD
    }

    private Status status;
    private LightMode mode;
    private int dimension;
    private double dailyConsumption;

    public SmartBulb(String factoryID,Status status, LightMode mode, int dimension, double dailyConsumption) {
        super(factoryID);
        this.status = status;
        this.mode = mode;
        this.dimension = dimension;
        this.dailyConsumption = dailyConsumption;
    }
    public SmartBulb(String factoryID) {
        super(factoryID);
        this.status = Status.OFF;
        this.mode = LightMode.NEUTRAL;
        this.dimension = 0;
        this.dailyConsumption = 0;
    }
    public SmartBulb(SmartBulb smart) {
        super(smart.getFactoryID());
        this.status = smart.getStatus();
        this.mode = smart.getMode();
        this.dimension = smart.getDimension();
        this.dailyConsumption = smart.getDailyConsumption();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LightMode getMode() {
        return mode;
    }

    public void setMode(LightMode mode) {
        this.mode = mode;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public double getDailyConsumption() {
        return dailyConsumption;
    }

    public void setDailyConsumption(double dailyConsumption) {
        this.dailyConsumption = dailyConsumption;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartBulb smartBulb = (SmartBulb) o;
        return dimension == smartBulb.dimension && Double.compare(smartBulb.dailyConsumption, dailyConsumption) == 0 && status == smartBulb.status && mode == smartBulb.mode;
    }

    public int hashCode() {
        return Objects.hash(status, mode, dimension, dailyConsumption);
    }

    public String toString() {
        return "SmartBulb{" +
                "status=" + status +
                ", mode=" + mode +
                ", dimension=" + dimension +
                ", dailyConsumption=" + dailyConsumption +
                '}';
    }
}
