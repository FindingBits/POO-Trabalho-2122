import java.util.*;

public class Casa{
    private String owner;
    private int NIF;
    HashMap<String, ArrayList<SmartDevice>> divisions;

    public Casa(String owner, int NIF) {
        this.owner = owner;
        this.NIF = NIF;
        this.divisions = new HashMap<String, ArrayList<SmartDevice>>();
    }
    public Casa(Casa home) {
        this.owner = home.getOwner();
        this.NIF = home.getNIF();
        this.divisions = home.getDivisions();
    }
    public Casa() {
        this.owner = "n/a";
        this.NIF = 999999999;
        this.divisions = new HashMap<String, ArrayList<SmartDevice>>();
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getNIF() {
        return NIF;
    }

    public void setNIF(int NIF) {
        this.NIF = NIF;
    }


    public HashMap<String, ArrayList<SmartDevice>> getDivisions() {
        return divisions;
    }

    public void setDivisions(HashMap<String, ArrayList<SmartDevice>> divisions) {
        this.divisions = divisions;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Casa casa = (Casa) o;
        return NIF == casa.NIF && Objects.equals(owner, casa.owner) && Objects.equals(divisions, casa.divisions);
    }

    public int hashCode() {
        return Objects.hash(owner, NIF, divisions);
    }

    public String toString() {
        return "Casa{" +
                "owner='" + owner + '\'' +
                ", NIF=" + NIF +
                ", divisions=" + divisions +
                '}';
    }

    public void addDivision(String division) throws DivisionExistsExeption{
        for (HashMap.Entry<String, ArrayList<SmartDevice>> entry : this.getDivisions().entrySet()){
            if(Objects.equals(entry.getKey(),division)){
                throw new DivisionExistsExeption("Division already exists! Division: "+division);
            }
        }
        divisions.put(division,new ArrayList<SmartDevice>());
    }

    public void addDevice(String division,SmartDevice device) throws DeviceExistsInDivision{
        for (HashMap.Entry<String, ArrayList<SmartDevice>> entry : this.getDivisions().entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (Objects.equals(entry.getValue().get(i).getFactoryID(), device.getFactoryID()))
                    throw new DeviceExistsInDivision("Device already exists in Division!: "+device);
            }
        }
        for (HashMap.Entry<String, ArrayList<SmartDevice>> entry : this.getDivisions().entrySet()){
            if (Objects.equals(entry.getKey(), division))
                entry.getValue().add(device.clone());
        }

    }

    /**
     * Gets all the devices in the home
     * @return All SmartDevices in the home
     */
    public ArrayList<SmartDevice> getAllDevices(){
        ArrayList<SmartDevice> devices=new ArrayList<SmartDevice>();
        for (HashMap.Entry<String, ArrayList<SmartDevice>> entry : this.getDivisions().entrySet()){
            for (int i = 0; i < entry.getValue().size(); i++){
                devices.add(entry.getValue().get(i).clone());
            }
        }
        return devices;
    }

    /**
     * Gets a device in the home
     * @return Desired SmartDevice in the home
     * @throws DeviceExistsInDivision Device doesn't exist in the home
     */
    public SmartDevice getDevice(String FactoryID) throws DeviceExistsInDivision{
        for (HashMap.Entry<String, ArrayList<SmartDevice>> entry : this.getDivisions().entrySet()){
            for (int i = 0; i < entry.getValue().size(); i++){
                if(Objects.equals(entry.getValue().get(i).getFactoryID(),FactoryID))
                    return (entry.getValue().get(i).clone());
            }
        }
        throw new DeviceExistsInDivision("Device doesn't exist in the home!");
    }

    public void getAllTimeON(){
        for (HashMap.Entry<String, ArrayList<SmartDevice>> entry : this.getDivisions().entrySet()){
            for (int i = 0; i < entry.getValue().size(); i++){
                System.out.println(entry.getValue().get(i).getFactoryID()+" -> "+entry.getValue().get(i).getTimeElapsed());
            }
        }
    }
    

    public void turnAllON(){
        for (HashMap.Entry<String, ArrayList<SmartDevice>> entry : this.getDivisions().entrySet()){
            for (int i = 0; i < entry.getValue().size(); i++){
                SmartDevice.turnON(entry.getValue().get(i));
            }
        }
    }
    public void turnAllOFF(){
        for (HashMap.Entry<String, ArrayList<SmartDevice>> entry : this.getDivisions().entrySet()){
            for (int i = 0; i < entry.getValue().size(); i++){
                SmartDevice.turnOFF(entry.getValue().get(i));
            }
        }
    }
    public void turnSpecificON(String factoryID){
        for (HashMap.Entry<String, ArrayList<SmartDevice>> entry : this.getDivisions().entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (Objects.equals(entry.getValue().get(i).getFactoryID(), factoryID))
                    SmartDevice.turnON(entry.getValue().get(i));
            }
        }
    }

    public void turnSpecificOFF(String factoryID){
        for (HashMap.Entry<String, ArrayList<SmartDevice>> entry : this.getDivisions().entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (Objects.equals(entry.getValue().get(i).getFactoryID(), factoryID))
                    SmartDevice.turnOFF(entry.getValue().get(i));
            }
        }
    }

    public double getTotalConsumption() throws DeviceExistsInDivision {
        double total=0;
        for (HashMap.Entry<String, ArrayList<SmartDevice>> entry : this.getDivisions().entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                total += this.getDevice(entry.getValue().get(i).getFactoryID()).getDailyConsumption();
            }
        }
        return total;
    }

    /**
     * Gets the number of SmartDevices in the home.
     * @return number of SmartDevices in the home
     */
    public int numberOfDevices(){
        int devices=0;
        for (HashMap.Entry<String, ArrayList<SmartDevice>> entry : this.getDivisions().entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                devices+=1;
            }
        }
        return devices;
    }



}
