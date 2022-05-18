import java.lang.String;
import java.util.Objects;

public class SmartBulb extends SmartDevice{

    public enum LightMode {
        NEUTRAL,
        WARM,
        COLD
    }
    private LightMode mode;
    private double dimension;
    protected double dailyConsumption;

    /**
     * compute the consumption based on the light mode
     * @param mode LightMode
     * @return the consumption
     */
    private double auxConsumption(LightMode mode){
        double aux=0;
        switch(mode){
            case WARM:
                // 15w WARM
                aux = (double)(15*(dimension*dimension))/1000;
                break;
            case COLD:
                // 20w COLD
                aux = (double)(20*(dimension*dimension))/1000;
                break;
            case NEUTRAL:
                // 10w NEUTRAL
                aux = (double)(10*(dimension*dimension))/1000;
                break;
        }
        return aux;

    }

    public SmartBulb(String factoryID,double mCost,Status status, LightMode mode, double dimension) {
        super(factoryID,mCost,status);
        this.mode = mode;
        this.dimension = dimension;
        this.dailyConsumption = auxConsumption(mode);

    }
    public SmartBulb(String factoryID,double mCost,Status status) {
        super(factoryID,mCost,status);
        this.mode = LightMode.NEUTRAL;
        this.dimension = 0;
        this.dailyConsumption = auxConsumption(this.mode);
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

    public double getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public double getDailyConsumption() {
        return dailyConsumption;
    }

    public void setDailyConsumption(double dailyConsumption) {
        this.dailyConsumption = dailyConsumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartBulb smartBulb = (SmartBulb) o;
        return dimension == smartBulb.dimension && Double.compare(smartBulb.dailyConsumption, dailyConsumption) == 0  && mode == smartBulb.mode;
    }
    @Override
    public int hashCode() {
        return Objects.hash( mode, dimension, dailyConsumption);
    }

    @Override
    public String toString() {
        return "SmartBulb{" +
                "device={" + super.toString() + "},"+
                "mode=" + mode +
                ", dimension=" + dimension +
                ", dailyConsumption=" + dailyConsumption +
                '}';
    }

    @Override
    public SmartBulb clone(){
        return new SmartBulb(this);
    }

}
