import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Invoice {
    private String code;
    private Ambient amb;
    private double consumoTotal;
    private FornecedorEnergia provider;
    private Casa house;
    private ArrayList<String> codeIDs;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return amb.getCalendar();
    }


    public double getConsumoTotal() {
        return consumoTotal;
    }

    public void setConsumoTotal(double consumoTotal) {
        this.consumoTotal = consumoTotal;
    }

    public FornecedorEnergia getProvider() {
        return provider;
    }

    public void setProvider(FornecedorEnergia provider) {
        this.provider = provider;
    }

    public Casa getHouse() {
        return house;
    }

    public void setHouse(Casa house) {
        this.house = house;
    }

    public ArrayList<String> getCodeIDs() {
        return codeIDs;
    }

    public void setCodeIDs(ArrayList<String> codeIDs) {
        this.codeIDs = codeIDs;
    }

    public Invoice(Ambient amb, FornecedorEnergia provider, Casa house) throws DeviceExistsInDivision, IOException {
        this.codeIDs=new ArrayList<String>();
        this.code=generateInvoiceCode();
        this.consumoTotal=house.getTotalConsumption()*provider.getTax()*provider.getDailyEnergyCost()*amb.getElapsed()/10;
        this.amb = amb;
        this.provider = provider;
        this.house = house;
        createInvoice();
    }

    /**
     * generator for the invoice
     * @throws IOException Error creating invoice file!
     */
    public void createInvoice() throws IOException {
        try{
            List<String> lines = Arrays.asList("FATURA","Codigo: "+getCode(),getProvider().getCompany(),"Exm(o)(a): "+getHouse().getOwner(),"NIF: "+getHouse().getNIF(),"Custo diario energetico (kwh): "+getProvider().getDailyEnergyCost(),"IVA energetico: "+getProvider().getTax(),"Periodo (comeco): "+amb.getStart().toString(),"Periodo (termino): "+getDate().toString(),"Consumo Total (Euros): "+String.format("%.2f", getConsumoTotal()), "Obrigado por confiar na nossa empresa!");
            Path file = Paths.get("invoices/"+code+".txt");
            Files.write(file, lines, StandardCharsets.UTF_8);
        }
        catch(Exception e){
            throw new IOException("Error creating invoice file!");
        }

    }

    /**
     * invoice codes manager
     * @param codeID invoice codes
     * @return true/false if exists or not
     */
    public boolean addCodeID(String codeID) {
        if (this.codeIDs.contains(codeID)) {
            return true;
        } else {
            this.codeIDs.add(codeID);
            return false;
        }
    }

    /**
     * generate invoice codes
     * @return invoice code
     */
    public String generateInvoiceCode(){
        String charSelect = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder temp = new StringBuilder();
        Random rnd = new Random();
        while (temp.length() < 5) {
            int index = (int) (rnd.nextFloat() * charSelect.length());
            temp.append(charSelect.charAt(index));
        }
        if(addCodeID(temp.toString())){
            generateInvoiceCode();
        }
        return temp.toString();
    }
}
