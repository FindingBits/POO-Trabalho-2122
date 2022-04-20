import java.lang.String;
import java.util.Objects;

public class SmartBulb extends SmartDevice{

    public enum LightMode {
        NEUTRAL,
        WARM,
        COLD
    }
    private LightMode mode;
    private int dimension;
    private double dailyConsumption;

    public SmartBulb(String factoryID,double mCost,Status status, LightMode mode, int dimension) {
        super(factoryID,mCost,status);
        this.mode = mode;
        this.dimension = dimension;
        this.dailyConsumption = 0;
        // dailyConsumption = (watt) * mm2 (mm square) * energyPrice watt/h / 10 *
        switch(mode){
            case WARM:
                // 15w WARM
                //this.dailyConsumption = 15*((dimension/10)
        }
    }
    public SmartBulb(String factoryID,double mCost,Status status) {
        super(factoryID,mCost,status);
        this.mode = LightMode.NEUTRAL;
        this.dimension = 0;
        this.dailyConsumption = 0;
    }
    public SmartBulb(SmartBulb smart) {
        super(smart.getFactoryID(), smart.getMCost(),smart.getStatus());
        this.mode = smart.getMode();
        this.dimension = smart.getDimension();
        this.dailyConsumption = smart.getDailyConsumption();
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
        return dimension == smartBulb.dimension && Double.compare(smartBulb.dailyConsumption, dailyConsumption) == 0  && mode == smartBulb.mode;
    }

    public int hashCode() {
        return Objects.hash( mode, dimension, dailyConsumption);
    }

    public String toString() {
        return "SmartBulb{" +
                "device={" + super.toString() + "},"+
                "mode=" + mode +
                ", dimension=" + dimension +
                ", dailyConsumption=" + dailyConsumption +
                '}';
    }
}
