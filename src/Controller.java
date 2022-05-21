import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


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

    public FornecedorEnergia getProvider(String name){
        for (int i = 0; i < getProviders().size(); i++) {
            if(Objects.equals(getProviders().get(i).getCompany(), name)){
                return getProviders().get(i);
            }
        }
        return null;
    }

    /**
     * Statistics: most expensive house to run
     * @return the most expensive house to run
     * @throws DeviceExistsInDivision device with error
     */
    public Casa mostExpensiveHouse() throws DeviceExistsInDivision {
        if(getAmbient().getElapsed()==0){
            System.out.println("Time must be advanced for the mostExpensiveHouse calculation to work!");
            return null;
        }
        double c=getHouses().get(0).getTotalConsumption()*getProvider(getHouses().get(0).getProvider()).getTax()*getProvider(getHouses().get(0).getProvider()).getDailyEnergyCost()*getAmbient().getElapsed()/10;
        int j=0;
        for(int i=1;i< getHouses().size();i++){
            if((getHouses().get(i).getTotalConsumption()*getProvider(getHouses().get(i).getProvider()).getTax()*getProvider(getHouses().get(i).getProvider()).getDailyEnergyCost()*getAmbient().getElapsed()/10)>c){
                c=getHouses().get(i).getTotalConsumption()*getProvider(getHouses().get(i).getProvider()).getTax()*getProvider(getHouses().get(i).getProvider()).getDailyEnergyCost()*getAmbient().getElapsed()/10;
                j=i;
            }
        }
        return getHouses().get(j);
    }

    /**
     * Statistics: most rentable provider
     * @return the most rentable provider
     */
    public FornecedorEnergia mostRentableProvider(){
        if(getAmbient().getElapsed()==0){
            System.out.println("Time must be advanced for the mostRentableProvider calculation to work!");
            return null;
        }
        double c=getProviders().get(0).getTax()+getProviders().get(0).getDailyEnergyCost();
        int j=0;
        for(int i=1;i< getProviders().size();i++){
            if((getProviders().get(i).getTax()+getProviders().get(i).getDailyEnergyCost())>c){
                c=getProviders().get(i).getTax()+getProviders().get(i).getDailyEnergyCost();
                j=i;
            }
        }
        return getProviders().get(j);
    }

    /**
     * generates invoices for the provided provider
     * @param provider provided provider
     * @throws DeviceExistsInDivision DeviceExistsInDivision
     * @throws IOException IOException
     */
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
                    Invoice inv = new Invoice(getAmbient(),forn,houses.get(i));
                }
            }
            System.out.println("Finished generating invoice for: " + provider + " clients!");
        }
    }

    /**
     * adds a house device or division
     * @param owner house
     * @param typeAlter addDiv/addDev
     * @param division division
     * @param device house device/null
     * @throws HouseNotFoundException HouseNotFoundException
     * @throws DivisionExistsExeption DivisionExistsExeption
     * @throws DeviceExistsInDivision DeviceExistsInDivision
     * @throws ParserException Error updating house!
     */
    public void updateHouseInput(String owner,String typeAlter,String division,SmartDevice device) throws HouseNotFoundException, DivisionExistsExeption, DeviceExistsInDivision, ParserException {
        try{
            for (int i = 0; i < getHouses().size(); i++) {
                if(Objects.equals(getHouses().get(i).getOwner(), owner)){
                    if(Objects.equals(typeAlter, "addDiv")){
                        getHouses().get(i).addDivision(division);
                        System.out.println("Informaton updated!");
                    }else if(Objects.equals(typeAlter, "addDev")){
                        System.out.println("Informaton updating...");
                        getHouses().get(i).addDevice(division,device);
                        System.out.println("Informaton updated!");
                    }
                }
            }
        }
        catch (Exception e){
            throw new ParserException("Error updating house!\nExtra:"+e.toString());
        }

    }

    /**
     * update the Status (ON/OFF) of all devices in the home
     * @param owner house
     * @param typeAlter OFF/ON
     * @throws ParserException Error changing states in house!
     */
    public void updateHouseStates(String owner,String typeAlter) throws ParserException {
        try{
            for (int i = 0; i < getHouses().size(); i++) {
                if(Objects.equals(getHouses().get(i).getOwner(), owner)){
                    if(Objects.equals(typeAlter, "OFF")){
                        getHouses().get(i).turnAllOFF();
                        System.out.println("Turned all off for: "+owner+" house!");
                    }else if(Objects.equals(typeAlter, "ON")){
                        getHouses().get(i).turnAllON();
                        System.out.println("Turned all on for: "+owner+" house!");
                    }
                }
            }
        }
        catch (Exception e){
            throw new ParserException("Error changing states in house!\nExtra:"+e.toString());
        }

    }

    public void generateFile() throws IOException {
        List<String> lines = Arrays.asList("Ficheiro de leitura.","Fornecedores:",getProviders().stream().toList().toString(),"Casas:",getHouses().stream().toList().toString(), "SmartDevices, SmartHouses e Controlo/Eficiencia Energetica");
        Path file = Paths.get("configs/save.txt");
        Files.write(file, lines, StandardCharsets.UTF_8);
    }

    /**
     * controller to subdivide operations to all creating type functions
     * @param parse full string command
     * @throws ParserException ParserException
     * @throws HouseNotFoundException HouseNotFoundException
     * @throws DeviceExistsInDivision DeviceExistsInDivision
     * @throws DivisionExistsExeption DivisionExistsExeption
     */
    public void create(String parse) throws ParserException, HouseNotFoundException, DeviceExistsInDivision, DivisionExistsExeption {
        String[] chunk = parse.split(",");
        switch (chunk[0]) {
            case "house":
                getHouses().add(getParser().parseCasa(chunk[1]+","+chunk[2]+","+chunk[3]));
                System.out.println("Added a new House!");
                break;
            case "provider":
                getProviders().add(getParser().parseFornecedor(chunk[1]+","+chunk[2]+","+chunk[3]));
                System.out.println("Added a new Provider!");
                break;
            case "device":
                System.out.println("Creating device "+chunk[1]+"...");
                if(Objects.equals(chunk[1], "smartbulb")){
                    System.out.println("Creating...");
                    SmartBulb tempBulb=getParser().parseSmartBulb(chunk[4]+","+chunk[5]);
                    updateHouseInput(chunk[2],"addDev",chunk[3],tempBulb);
                }else if(Objects.equals(chunk[1], "smartcamera")){
                    SmartCamera tempCamera=getParser().parseSmartCamera(chunk[4]+","+chunk[5]);
                    updateHouseInput(chunk[2],"addDev",chunk[3],tempCamera);
                }else if(Objects.equals(chunk[1], "smartspeaker")){
                    SmartSpeaker tempSpeaker=getParser().parseSmartSpeaker(chunk[4]+","+chunk[5]+",Generic");
                    updateHouseInput(chunk[2],"addDev",chunk[3],tempSpeaker);
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

    /**
     * generates the full environment of the program
     * @throws ParserException ParserException
     * @throws DeviceExistsInDivision DeviceExistsInDivision
     * @throws IOException IOException
     * @throws HouseNotFoundException HouseNotFoundException
     * @throws DivisionExistsExeption DivisionExistsExeption
     */
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
                case "stats":
                    System.out.println("Current time: " + getAmbient().getCalendar().toString() + "");
                    System.out.println("Creating: statistics");
                    System.out.println("Most Expensive House: "+mostExpensiveHouse());
                    System.out.println("Most Rentable Provider (by tax and daily cost): "+mostRentableProvider());
                    System.out.println("To review invoices check 'invoices' folder after generating invoices!");
                    break;
                case "turnAllOff":
                    System.out.println("[Turned all devices off] Current time: " + getAmbient().getCalendar().toString() + "");
                    updateHouseStates(chunk[1],"OFF");
                    break;
                case "turnAllOn":
                    System.out.println("[Turned all devices on] Current time: " + getAmbient().getCalendar().toString() + "");
                    updateHouseStates(chunk[1],"ON");
                    break;
                case "create":
                    System.out.println("Creating: " + chunk[1] + "");
                    create(chunk[1]+","+chunk[2]+","+chunk[3]+","+chunk[4]+","+chunk[5]+","+chunk[6]);
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

    /**
     * generates the control for the program to run
     * @throws DeviceExistsInDivision DeviceExistsInDivision
     * @throws IOException IOException
     * @throws ParserException ParserException
     * @throws DivisionExistsExeption DivisionExistsExeption
     * @throws HouseNotFoundException HouseNotFoundException
     */
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
                    System.out.println("Saving read by user only file...");
                    generateFile();
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
                case "configLoad":
                    System.out.println("Using a provided config file.");
                    System.out.println("Using this command replaces previous devices,houses,providers.");
                    System.out.println("Type a file name:");
                    String choiceLoad = null;
                    Scanner scanLoad = new Scanner(System.in);
                    choiceLoad = scanLoad.nextLine();
                    getParser().parse("configs/"+choiceLoad);
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
