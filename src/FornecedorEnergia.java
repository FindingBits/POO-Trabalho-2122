import java.util.ArrayList;
import java.util.Objects;

public class FornecedorEnergia {
    private String empresa;
    private double custoDiarioEnergia;
    private double Imposto;
    private ArrayList<Casa> casas;

    // gets e sets

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public double getCustoDiarioEnergia() {
        return custoDiarioEnergia;
    }

    public void setCustoDiarioEnergia(double custoDiarioEnergia) {
        this.custoDiarioEnergia = custoDiarioEnergia;
    }

    public double getImposto() {
        return Imposto;
    }

    public void setImposto(double imposto) {
        Imposto = imposto;
    }

    public void setCasas(ArrayList<Casa> casas) {
        this.casas = casas;
    }

    public ArrayList<Casa> getCasas() {
        return casas;
    }

    public FornecedorEnergia(){
        this.setEmpresa("");
        this.setCustoDiarioEnergia(0);
        this.setImposto(0);
        this.setCasas(new ArrayList<Casa>());
    }

    public FornecedorEnergia(String empresa, double custo, double imposto, ArrayList<Casa> casas){
        this.setEmpresa(empresa);
        this.setCustoDiarioEnergia(custo);
        this.setImposto(imposto);
        this.setCasas(new ArrayList<Casa>(casas));
    }

    public FornecedorEnergia(FornecedorEnergia fe){
        this(fe.getEmpresa(), fe.getCustoDiarioEnergia(), fe.getImposto(),fe.getCasas());
    }

    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        FornecedorEnergia fe = (FornecedorEnergia) o;
        return (this.empresa.equals(fe.empresa) && Objects.equals(this.custoDiarioEnergia,fe.custoDiarioEnergia) && Objects.equals(this.Imposto,fe.Imposto));
    }

    public FornecedorEnergia clone(){
        return new FornecedorEnergia(this);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome da Empresa: " + this.empresa);
        sb.append("\nValor base do custo diario: " + this.custoDiarioEnergia);
        sb.append("\nImposto: " + this.Imposto);
        return sb.toString();
    }

    public double custoBaseDiaCasa(int index){
        double total = 0;
        if (index < this.casas.size()){
            total = getCustoDiarioEnergia() * this.casas.get(index).getConsumption();
        }
        return total;
    }

    public double custoTotalDiaCasa(int index){
        double total = 0;
        if (index < this.casas.size()){
            total = custoBaseDiaCasa(index) * (1 + this.getImposto());
        }
        return total;
    }

    public double PrecoDiaPorDispositivo(String dispositivo){
        double total = 0;
        for (Casa c: this.casas){
            if(c.getDevice == dispositivo){
                total += c.getDeviceConsumption() * this.getCustoDiarioEnergia() * (1 + getImposto());
            }
        }
        return total;
    }
}
