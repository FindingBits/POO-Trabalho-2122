import java.util.Objects;
import java.lang.String;

public class SmartSpeaker extends SmartDevice {
    private int volume;
    private String radio;
    private String brand;
    private double dailyConsumption;

    private double auxConsumption(String brand){
        double aux=0;
        switch(brand){
            case "Sony":
                // 150w
                aux = (double)(15*(150))/100;
                break;
            case "Bose":
                // 200w
                aux = (double)(20*(200))/100;
                break;
            case "Bang&Olufsen":
                // 300w
                aux = (double)(10*(300))/100;
                break;
            default:
                // 50w cheap speaker :D
                aux = (double)(10*(50))/100;
                break;
        }
        return aux;

    }

    public SmartSpeaker(String factoryID,double mCost,Status status, int volume, String radio, String brand) {
        super(factoryID,mCost,status);
        this.volume = volume;
        this.radio = radio;
        this.brand = brand;
        this.dailyConsumption = auxConsumption(brand);
    }
    public SmartSpeaker(String factoryID,double mCost,Status status) {
        super(factoryID,mCost,status);
        this.volume = 0;
        this.radio = "108.0 Mhz";
        this.brand = "n/a";
        this.dailyConsumption = auxConsumption(this.brand);
    }
    public SmartSpeaker(SmartSpeaker smart) {
        super(smart.getFactoryID(), smart.getMCost(),smart.getStatus());
        this.volume = smart.getVolume();
        this.radio = smart.getRadio();
        this.brand = smart.getBrand();
        this.dailyConsumption = auxConsumption(smart.getBrand());
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
        SmartSpeaker that = (SmartSpeaker) o;
        return volume == that.volume && Double.compare(that.dailyConsumption, dailyConsumption) == 0 && Objects.equals(radio, that.radio) && Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volume, radio, brand, dailyConsumption);
    }

    @Override
    public String toString() {
        return "SmartSpeaker{" +
                "device={" + super.toString() + "},"+
                "volume=" + volume +
                ", radio='" + radio + '\'' +
                ", brand='" + brand + '\'' +
                ", dailyConsumption=" + dailyConsumption +
                '}';
    }
    @Override
    public SmartSpeaker clone(){
        return new SmartSpeaker(this);
    }

}
