import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) throws ParserException, DeviceExistsInDivision, DivisionExistsExeption {
        System.out.println("\n- Trabalho de Grupo POO 2021/2022 -\n");
        String choice = null;
        Scanner scan = new Scanner(System.in);
        Parser parser = new Parser();
        do {
            System.out.println("Digite um comando: ");
            choice = scan.nextLine();
            switch (choice) {
                case "info":
                    System.out.println("Realizado por:\n-> A94013 - Joao Guedes\n-> A91650 - Catarina Quintas\n-> A91681 - Pedro Martins\n");
                    break;
                case "configDefault":
                    System.out.println("Usada a config default do grupo.");
                    parser.parse("default.txt");
                    ArrayList<FornecedorEnergia> providers=parser.getTempProviders();
                    ArrayList<Casa> houses=parser.getTempHouses();
                    System.out.println("Dados Carregados, estabelecido ambiente.");
                    System.out.println("DEBUG.providers: "+providers);
                    System.out.println("DEBUG.houses: "+houses);
                    System.out.println("Digite um comando de ambiente: ");
                    break;
                case "configLoad":
                    System.out.println("Sera carregada uma config.\n");
                    System.out.println("Insira o nome do ficheiro: ");
                    Scanner scanTemp = new Scanner(System.in);
                    String path = scanTemp.nextLine();
                    parser.parse(path);
                    scanTemp.close();
                    break;
            } // end of switch
        } while (!choice.equals("quit")); // end of loop
    }
}
