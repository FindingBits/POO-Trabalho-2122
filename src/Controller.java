import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Scanner;


public class Controller {

    private Ambient ambient;
    private Parser parser;
    private ArrayList<FornecedorEnergia> providers;
    private ArrayList<Casa> houses;

    public Ambient getAmbient() {
        return ambient;
    }

    public void setAmbient(Ambient ambient) {
        this.ambient = ambient;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public ArrayList<FornecedorEnergia> getProviders() {
        return providers;
    }

    public void setProviders(ArrayList<FornecedorEnergia> providers) {
        this.providers = providers;
    }

    public ArrayList<Casa> getHouses() {
        return houses;
    }

    public void setHouses(ArrayList<Casa> houses) {
        this.houses = houses;
    }

    public Controller() {
        this.ambient = new Ambient(Calendar.getInstance());
        this.parser = new Parser();
        this.providers=new ArrayList<FornecedorEnergia>();
        this.houses=new ArrayList<Casa>();
    }

    public void generateInvoice(String provider) throws DeviceExistsInDivision, IOException {
        FornecedorEnergia forn=null;
        for (int i = 0; i < getProviders().size(); i++) {
            if(Objects.equals(getProviders().get(i).getCompany(), provider)){
                forn=getProviders().get(i);
                break;
            }
        }
        if(forn==null){
            System.out.println("Provider not found!");
        }else{
            for (int i = 0; i < getHouses().size(); i++) {
                if(Objects.equals(getHouses().get(i).getProvider(), provider)){
                    Invoice inv = new Invoice(getAmbient().getCalendar(),forn,houses.get(i));
                }
            }
            System.out.println("Finished generating invoice for: " + provider + " clients!");
        }
    }

    public void updateHouseInput(String owner,String typeAlter,String division,SmartDevice device) throws HouseNotFoundException, DivisionExistsExeption, DeviceExistsInDivision, ParserException {
        try{
            for (int i = 0; i < getHouses().size(); i++) {
                if(Objects.equals(getHouses().get(i).getOwner(), owner)){
                    if(Objects.equals(typeAlter, "addDiv")){
                        getHouses().get(i).addDivision(division);
                        System.out.println("Informaton updated!");
                    }else if(Objects.equals(typeAlter, "addDev")){
                        getHouses().get(i).addDevice(division,device);
                        System.out.println("Informaton updated!");
                    }
                }
            }
            throw new HouseNotFoundException("House not found!");
        }
        catch (Exception e){
            throw new ParserException("Error updating house!\nExtra:"+e.toString());
        }

    }

    public void create(String parse) throws ParserException, HouseNotFoundException, DeviceExistsInDivision, DivisionExistsExeption {
        String[] chunk = parse.split(",");
        switch (chunk[0]) {
            case "house":
                getHouses().add(getParser().parseCasa(chunk[2]+","+chunk[3]+","+chunk[4]));
                System.out.println("Added a new House!");
                break;
            case "provider":
                getProviders().add(getParser().parseFornecedor(chunk[2]+","+chunk[3]+","+chunk[4]));
                System.out.println("Added a new Provider!");
                break;
            case "device":
                System.out.println("Creating device "+chunk[1]+"...");
                if(Objects.equals(chunk[1], "smartbulb")){
                    SmartBulb tempBulb=getParser().parseSmartBulb(chunk[2]+","+chunk[3]);
                    updateHouseInput(chunk[1],"addDev",chunk[2],tempBulb);
                }else if(Objects.equals(chunk[1], "smartcamera")){
                    SmartCamera tempCamera=getParser().parseSmartCamera(chunk[2]+","+chunk[3]+","+chunk[3]);
                    updateHouseInput(chunk[1],"addDev",chunk[2],tempCamera);
                }else if(Objects.equals(chunk[1], "smartspeaker")){
                    SmartSpeaker tempSpeaker=getParser().parseSmartSpeaker(chunk[2]+","+chunk[3]);
                    updateHouseInput(chunk[1],"addDev",chunk[2],tempSpeaker);
                }
                break;
            case "division":
                System.out.println("Creating division "+chunk[2]+"...");
                updateHouseInput(chunk[1],"addDiv",chunk[2],null);
                break;
            default:
                System.out.println("Informaton provided not correct!");
                break;
        }
    }

    public void generateEnvironment() throws ParserException, DeviceExistsInDivision, IOException, HouseNotFoundException, DivisionExistsExeption {
        Scanner scanPostDefault = new Scanner(System.in);
        String[] chunk;
        do {
            System.out.println("Type a ambient command: ");
            String scanDefault = scanPostDefault.nextLine();
            chunk = scanDefault.split(",");
            if(chunk[0].equals("quit") && chunk[1].equals("ambient")) break;
            switch (chunk[0]) {
                case "advance":
                    System.out.println("Previous time: " + getAmbient().getCalendar().toString() + "");
                    getAmbient().advanceDays(Integer.parseInt(chunk[2]));
                    System.out.println("Current time: " + getAmbient().getCalendar().toString() + "");
                    break;
                case "current":
                    System.out.println("Current time: " + getAmbient().getCalendar().toString() + "");
                    break;
                case "create":
                    System.out.println("Creating: " + chunk[1] + "");
                    create(chunk[0]+","+chunk[1]+","+chunk[2]+","+chunk[3]+","+chunk[4]);
                    break;
                case "generateInvoice":
                    System.out.println("Generating invoice for: " + chunk[1] + " clients...");
                    generateInvoice(chunk[1]);
                    break;
                default:
                    System.out.println("Invalid ambient Command!");
                    break;
            }
        } while (!chunk[0].equals("quit"));
        System.out.println("Exiting ambient...");
    }

    public void initializeControl() throws DeviceExistsInDivision, IOException, ParserException, DivisionExistsExeption, HouseNotFoundException {
        String choice = null;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Type a command: ");
            choice = scan.nextLine();
            if(choice.equals("quit")) break;
            switch (choice) {
                case "info":
                    System.out.println("Realizado por:\n-> A94013 - Joao Guedes\n-> A91650 - Catarina Quintas\n-> A91681 - Pedro Martins\n");
                    break;
                case "saveCurrent":
                    System.out.println("To do..");
                    break;
                case "createNew":
                    System.out.println("Creating empty environment...");
                    generateEnvironment();
                    break;
                case "configDefault":
                    System.out.println("Using the group default config file.");
                    System.out.println("Using this command replaces previous devices,houses,providers.");
                    getParser().parse("configs/default.txt");
                    setProviders(getParser().getTempProviders());
                    setHouses(getParser().getTempHouses());
                    generateEnvironment();
                default:
                    System.out.println("Invalid project Command!");
                    break;
            }
        } while (!choice.equals("quit"));
        scan.close();
    }

}
