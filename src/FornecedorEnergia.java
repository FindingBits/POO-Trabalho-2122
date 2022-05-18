import java.util.ArrayList;
import java.util.Objects;

public class FornecedorEnergia {
    private String company;
    private double dailyEnergyCost;
    private double tax;

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
        this.tax = tax;
    }

    public FornecedorEnergia(String company, double custo, double tax){
        this.setCompany(company);
        this.setDailyEnergyCost(custo);
        this.setTax(tax);
    }

    public FornecedorEnergia(FornecedorEnergia fe){
        this(fe.getCompany(), fe.getDailyEnergyCost(),fe.getTax());
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
                '}';
    }
}
