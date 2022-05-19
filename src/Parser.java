import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
/*
Guide to provided file:
--Fornecedores
Fornecedor:<NomeFornecedor>
Casa:<NomeProprietario>,<NifProprietario>,<NomeFornecedor>
currentDivisionEDevices:<currentDivision>,<Devices>
SmartCamera:<Resolucao>,<Tamanho>,<Consumo>
SmartSpeaker:<Volume>,<CanalRadio>,<Marca>,<Consumo>
SmartBulb:<Tonalidade>,<Diametro>,<Consumo>
 */
public class Parser {
    private ArrayList<FornecedorEnergia> tempProviders;
    private ArrayList<Casa> tempHouses;
    private ArrayList<String> FactoryIDs;

    public Parser() {
        this.tempProviders = new ArrayList<FornecedorEnergia>();
        this.tempHouses = new ArrayList<Casa>();
        this.FactoryIDs = new ArrayList<String>();
    }

    public ArrayList<FornecedorEnergia> getTempProviders() {
        return tempProviders;
    }

    public ArrayList<Casa> getTempHouses() {
        return tempHouses;
    }

    public ArrayList<String> getFactoryIDs() {
        return FactoryIDs;
    }

    /**
     * manager for providers
     * @param provider provider
     * @return true/false if exists
     */
    public boolean addProvider(FornecedorEnergia provider){
        if(this.tempProviders.contains(provider)){
            return true;
        }
        else{
            this.tempProviders.add(provider);
            return false;
        }

    }

    /**
     * manager for houses
     * @param house houses
     * @return true/false if exists
     */
    public boolean addHouse(Casa house){
        if(this.tempHouses.contains(house)){
            return true;
        }
        else{
            this.tempHouses.add(house);
            return false;
        }

    }

    /**
     * manager for factoryIDs
     * @param factoryID factoryID
     * @return true/false if exists
     */
    public boolean addFactoryID(String factoryID){
        if(this.FactoryIDs.contains(factoryID)){
            return true;
        }
        else{
            this.FactoryIDs.add(factoryID);
            return false;
        }

    }

    /**
     * generator for factory IDs
     * @return factory ID
     */
    public String generateFactoryID(){
        String charSelect = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder temp = new StringBuilder();
        Random rnd = new Random();
        while (temp.length() < 5) {
            int index = (int) (rnd.nextFloat() * charSelect.length());
            temp.append(charSelect.charAt(index));
        }
        if(addFactoryID(temp.toString())){
            generateFactoryID();
        }
        return temp.toString();
    }

    /**
     * parser for text files (.txt) compatible with the app
     * @param path pathToFile
     * @throws ParserException ParserException
     * @throws DivisionExistsExeption DivisionExistsExeption
     * @throws DeviceExistsInDivision DeviceExistsInDivision
     */
    public void parse(String path) throws ParserException, DivisionExistsExeption, DeviceExistsInDivision {
        List<String> lines = readFile(path);
        String[] startLine;
        String currentDivision = null;
        Casa currentHouse = null;
        int counter=1;
        for (String linha : lines) {
            startLine = linha.split(":", 2);
            switch(startLine[0]){
                case "Fornecedor":
                    addProvider(parseFornecedor(startLine[1]));
                    break;
                case "Casa":
                    if(currentHouse!=null){
                        currentHouse.turnAllON();
                        addHouse(currentHouse);
                    }
                    currentHouse = parseCasa(startLine[1]);
                    break;
                case "Divisao":
                    if (currentHouse == null) throw new ParserException("Invalid previous house! Current line: "+counter);
                    currentDivision = startLine[1];
                    currentHouse.addDivision(currentDivision);
                    break;
                case "SmartBulb":
                    if (currentDivision == null) throw new ParserException("Invalid previous division! Current line: "+counter);
                    SmartBulb tempBulb = parseSmartBulb(startLine[1]);
                    currentHouse.addDevice(currentDivision,tempBulb);
                    break;
                case "SmartCamera":
                    if (currentDivision == null) throw new ParserException("Invalid previous division! Current line: "+counter);
                    SmartCamera tempCamera = parseSmartCamera(startLine[1]);
                    currentHouse.addDevice(currentDivision,tempCamera);
                    break;
                case "SmartSpeaker":
                    if (currentDivision == null) throw new ParserException("Invalid previous division! Current line: "+counter);
                    SmartSpeaker tempSpeaker = parseSmartSpeaker(startLine[1]);
                    currentHouse.addDevice(currentDivision,tempSpeaker);
                    break;
                default:
                    throw new ParserException("Invalid line! Current line: "+counter);
            }
            counter+=1;
        }
        if(currentHouse!=null){
            currentHouse.turnAllON();
            addHouse(currentHouse);
        }
        System.out.println("Data successfully parsed, creating ambient...");
    }


    /**
     * file reader
     * @param nomeFich file name
     * @return lines of file
     * @throws ParserException Error reading line, could not Parse!
     */
    public List<String> readFile(String nomeFich) throws ParserException {
        List<String> lines;
        try { lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8);return lines; }
        catch(IOException exc) { throw new ParserException("Error reading line, could not Parse!\nExtra information:"+exc.toString()); }
    }

    /**
     * parser for houses
     * @param input house attributes
     * @return the new house
     * @throws ParserException Inconsistent info, could not Parse!
     */
    public Casa parseCasa(String input) throws ParserException{
        try {
            String[] chunk = input.split(",");
            return new Casa(chunk[0],Integer.parseInt(chunk[1]),chunk[2]);
        }
        catch(Exception e){ throw new ParserException("Inconsistent house info, could not Parse!\nExtra information:"+e.toString()); }

    }
    /**
     * parser for providers
     * @param input provider attributes
     * @return the new provider
     * @throws ParserException Inconsistent info, could not Parse!
     */
    public FornecedorEnergia parseFornecedor(String input) throws ParserException{
        try {
            String[] chunk = input.split(",");
            return new FornecedorEnergia(chunk[0],Double.parseDouble(chunk[2]),Double.parseDouble(chunk[1]));
        }
        catch(Exception e){ throw new ParserException("Inconsistent provider info, could not Parse!\nExtra information:"+e.toString()); }

    }
    /**
     * parser for SmartBulb
     * @param input SmartBulb attributes
     * @return the new SmartBulb
     * @throws ParserException Inconsistent info, could not Parse!
     */
    public SmartBulb parseSmartBulb(String input) throws ParserException {
        try {
            String[] chunk = input.split(",");
            SmartBulb.LightMode mode;
            if(Objects.equals(chunk[0], "Warm")){
                mode = SmartBulb.LightMode.WARM;
            }else if(Objects.equals(chunk[0],"Neutral")){
                mode = SmartBulb.LightMode.NEUTRAL;
            }else if(Objects.equals(chunk[0],"Cold")){
                mode = SmartBulb.LightMode.COLD;
            }else{
                throw new ParserException("Invalid LightMode!");
            }
            return new SmartBulb(generateFactoryID(),5,SmartDevice.Status.OFF,mode,Double.parseDouble(chunk[1]));
        }
        catch(Exception e){ throw new ParserException("Inconsistent bulb info, could not Parse!\nExtra information:"+e.toString()); }
    }
    /**
     * parser for SmartSpeaker
     * @param input SmartSpeaker attributes
     * @return the new SmartSpeaker
     * @throws ParserException Inconsistent info, could not Parse!
     */
    public SmartSpeaker parseSmartSpeaker(String input) throws ParserException {
        try {
            String[] chunk = input.split(",");
            return new SmartSpeaker(generateFactoryID(),7,SmartDevice.Status.OFF,Integer.parseInt(chunk[0]),chunk[2],chunk[1]);
        }
        catch(Exception e){ throw new ParserException("Inconsistent speaker info, could not Parse!\nExtra information:"+e.toString()); }
    }
    /**
     * parser for SmartCamera
     * @param input SmartCamera attributes
     * @return the new SmartCamera
     * @throws ParserException Inconsistent info, could not Parse!
     */
    public SmartCamera parseSmartCamera(String input) throws ParserException {
        try {
            String[] chunk = input.split(",");
            String requiredString = chunk[0].substring(chunk[0].indexOf("x") + 1, chunk[0].indexOf(")"));
            return new SmartCamera(generateFactoryID(),9,SmartDevice.Status.OFF,Integer.parseInt(requiredString),Integer.parseInt(chunk[1]));
        }
        catch(Exception e){ throw new ParserException("Inconsistent speaker info, could not Parse!\nExtra information:"+e.toString()); }
    }

}

