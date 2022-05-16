import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws ParserException, DeviceExistsInDivision, DivisionExistsExeption {

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

                    parser.parse("default.txt");
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
                            default:
                                System.out.println("Invalid ambient Command!");
                        } // end of switch
                    } while (!chunk[0].equals("quit")); // end of loop
                    scanPostDefault.close();
                    break;
                default:
                    System.out.println("Invalid project Command!");
                    break;
            }
        } while (!choice.equals("quit"));
    }
}
