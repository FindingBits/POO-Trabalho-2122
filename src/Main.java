import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class Main {
    public static void readFile(String path) {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not read the file.");
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        System.out.println("Trabalho de Grupo POO 2021/2022\n");
        String choice = null;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Digite um comando: ");
            choice = scan.nextLine();
            switch (choice) {
                case "info":
                    System.out.println("Realizado por:\n-> A94013 - Joao Guedes\n-> A91650 - Catarina Quintas\n-> A91681 - Pedro Martins\n");
                    break;
                case "configDefault":
                    System.out.println("Usada a config default do grupo.\n");
                    readFile("configs/default.txt");
                    break;
                case "configLoad":
                    System.out.println("Sera carregada uma config.\n");
                    System.out.println("Insira o caminho do ficheiro: ");
                    Scanner scanTemp = new Scanner(System.in);
                    String path = scanTemp.nextLine();
                    readFile(path);
                    scanTemp.close();
                    break;
            } // end of switch
        } while (!choice.equals("quit")); // end of loop
    }
}
