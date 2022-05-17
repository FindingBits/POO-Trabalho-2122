import java.io.IOException;

public class View {
    public static void main(String[] args) throws ParserException, DeviceExistsInDivision, DivisionExistsExeption, IOException, HouseNotFoundException {
        Controller control = new Controller();
        System.out.println("\n- Trabalho de Grupo POO 2021/2022 -\n");
        control.initializeControl();
        System.out.println("Exiting...\n");
    }
}
