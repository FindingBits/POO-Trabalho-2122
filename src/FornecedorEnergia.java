import java.util.ArrayList;
import java.util.Objects;

public class FornecedorEnergia {
    private String company;
    private double dailyEnergyCost;
    private double tax;
    private ArrayList<Casa> houses;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getDailyEnergyCost() {
        return dailyEnergyCost;
    }

    public void setDailyEnergyCost(double dailyEnergyCost) {
        this.dailyEnergyCost = dailyEnergyCost;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        tax = tax;
    }

    public void setHouses(ArrayList<Casa> houses) {
        this.houses = houses;
    }

    public ArrayList<Casa> getHouses() {
        return houses;
    }

    public FornecedorEnergia(){
        this.setCompany("");
        this.setDailyEnergyCost(0);
        this.setTax(0);
        this.setHouses(new ArrayList<Casa>());
    }

    public FornecedorEnergia(String company, double custo, double tax, ArrayList<Casa> houses){
        this.setCompany(company);
        this.setDailyEnergyCost(custo);
        this.setTax(tax);
        this.setHouses(new ArrayList<Casa>(houses));
    }

    public FornecedorEnergia(FornecedorEnergia fe){
        this(fe.getCompany(), fe.getDailyEnergyCost(), fe.getTax(),fe.getHouses());
    }

    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        FornecedorEnergia fe = (FornecedorEnergia) o;
        return (this.company.equals(fe.company) && Objects.equals(this.dailyEnergyCost,fe.dailyEnergyCost) && Objects.equals(this.tax,fe.tax));
    }

    public FornecedorEnergia clone(){
        return new FornecedorEnergia(this);
    }

    @Override
    public String toString() {
        return "FornecedorEnergia{" +
                "company='" + company + '\'' +
                ", dailyEnergyCost=" + dailyEnergyCost +
                ", tax=" + tax +
                ", houses=" + houses +
                '}';
    }

    public double houseBaseDailyCost(int index) throws HouseNotFoundException, DeviceExistsInDivision {
        double total = 0;
        if (index < this.houses.size()){
            return getDailyEnergyCost() * this.houses.get(index).getTotalConsumption();
        }
        throw new HouseNotFoundException("House does not exist!");
    }

    public double houseTotalDailyCost(int index) throws HouseNotFoundException, DeviceExistsInDivision {
        double total = 0;
        if (index < this.houses.size()){
            total = houseBaseDailyCost(index) * (1 + this.getTax());
        }
        else{
            throw new HouseNotFoundException("House does not exist!");
        }
        return total;
    }
    /*
    public double PrecoDiaPorDispositivo(SmartDevice device) throws DeviceExistsInDivision {
        double total = 0;
        for (Casa c: this.houses){
            if(Objects.equals(c.getDevice(device.getFactoryID()).getFactoryID(), device.getFactoryID())){
                return c.getDeviceConsumption() * this.getDailyEnergyCost() * (1 + getTax());
            }
        }
        throw new DeviceExistsInDivision("Device does not exist in the home!");
    }*/
}
