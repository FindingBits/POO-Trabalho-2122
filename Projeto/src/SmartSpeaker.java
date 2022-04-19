import java.util.Objects;
import java.lang.String;

public abstract class SmartSpeaker extends SmartDevice {
    private int volume;
    private String radio;
    private String brand;
    private double dailyConsumption;

    public SmartSpeaker(String factoryID, int volume, String radio, String brand, double dailyConsumption) {
        super(factoryID);
        this.volume = volume;
        this.radio = radio;
        this.brand = brand;
        this.dailyConsumption = dailyConsumption;
    }
    public SmartSpeaker(String factoryID) {
        super(factoryID);
        this.volume = 0;
        this.radio = "108.0 Mhz";
        this.brand = "n/a";
        this.dailyConsumption = 0;
    }
    public SmartSpeaker(SmartSpeaker smart) {
        super(smart.getFactoryID());
        this.volume = smart.getVolume();
        this.radio = smart.getRadio();
        this.brand = smart.getBrand();
        this.dailyConsumption = smart.getDailyConsumption();
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

    public double getDailyConsumption() {
        return dailyConsumption;
    }

    public void setDailyConsumption(double dailyConsumption) {
        this.dailyConsumption = dailyConsumption;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartSpeaker that = (SmartSpeaker) o;
        return volume == that.volume && Double.compare(that.dailyConsumption, dailyConsumption) == 0 && Objects.equals(radio, that.radio) && Objects.equals(brand, that.brand);
    }

    public int hashCode() {
        return Objects.hash(volume, radio, brand, dailyConsumption);
    }

    public String toString() {
        return "SmartSpeaker{" +
                "volume=" + volume +
                ", radio='" + radio + '\'' +
                ", brand='" + brand + '\'' +
                ", dailyConsumption=" + dailyConsumption +
                '}';
    }
}
