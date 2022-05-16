import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws ParserException, DeviceExistsInDivision, DivisionExistsExeption, IOException {

        Ambient ambient = new Ambient(Calendar.getInstance());
        Parser parser = new Parser();
        ArrayList<FornecedorEnergia> providers=new ArrayList<FornecedorEnergia>();
        ArrayList<Casa> houses=new ArrayList<Casa>();

        System.out.println("\n- Trabalho de Grupo POO 2021/2022 -\n");
        String choice = null;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Type a command: ");
            choice = scan.nextLine();
            switch (choice) {
                case "info":
                    System.out.println("Realizado por:\n-> A94013 - Joao Guedes\n-> A91650 - Catarina Quintas\n-> A91681 - Pedro Martins\n");
                    break;
                case "configDefault":
                    System.out.println("Using the group default config file.");

                    parser.parse("configs/default.txt");
                    providers=parser.getTempProviders();
                    houses=parser.getTempHouses();

                    Scanner scanPostDefault = new Scanner(System.in);
                    String[] chunk;
                    do {
                        System.out.println("Type a ambient command: ");
                        String scanDefault = scanPostDefault.nextLine();
                        chunk = scanDefault.split(",");
                        switch (chunk[0]) {
                            case "advance":
                                System.out.println("Previous time: " + ambient.getCalendar().toString() + "");
                                ambient.advanceDays(Integer.parseInt(chunk[2]));
                                System.out.println("Current time: " + ambient.getCalendar().toString() + "");
                                break;
                            case "current":
                                System.out.println("Current time: " + ambient.getCalendar().toString() + "");
                                break;
                            case "create":
                                System.out.println("Creating: " + chunk[1] + "");
                                switch (chunk[1]) {
                                    case "house":
                                        houses.add(parser.parseCasa(chunk[2]+","+chunk[3]+","+chunk[4]));
                                        System.out.println("Added a new House!");
                                        break;
                                    case "provider":
                                        providers.add(parser.parseFornecedor(chunk[2]+","+chunk[3]+","+chunk[4]));
                                        System.out.println("Added a new Provider!");
                                        break;
                                    case "device":
                                        System.out.println("To do..");
                                        break;
                                }
                                break;
                            case "generateInvoice":
                                System.out.println("Generating invoice for: " + chunk[1] + " clients...");
                                FornecedorEnergia forn=null;
                                for (int i = 0; i < providers.size(); i++) {
                                    if(Objects.equals(providers.get(i).getCompany(), chunk[1])){
                                        forn=providers.get(i);
                                        break;
                                    }
                                }
                                if(forn==null){
                                    System.out.println("Provider not found");
                                }else{
                                    for (int i = 0; i < houses.size(); i++) {
                                        if(Objects.equals(houses.get(i).getProvider(), chunk[1])){
                                            Invoice inv = new Invoice(ambient.getCalendar(),forn,houses.get(i));
                                        }
                                    }
                                }
                                break;
                            default:
                                System.out.println("Invalid ambient Command!");
                                break;
                        }
                    } while (!chunk[0].equals("quit"));
                    scanPostDefault.close();
                    System.out.println("Exiting ambient...\n");
                    break;
                default:
                    System.out.println("Invalid project Command!");
                    break;
            }
        } while (!choice.equals("quit"));
        System.out.println("Exiting...\n");
    }
}
