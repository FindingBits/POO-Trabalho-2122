import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) throws ParserException, DeviceExistsInDivision, DivisionExistsExeption {
        System.out.println("Trabalho de Grupo POO 2021/2022\n");
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
                    System.out.println("Usada a config default do grupo.\n");
                    parser.parse("default.txt");
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
