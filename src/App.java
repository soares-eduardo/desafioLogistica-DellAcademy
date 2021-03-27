import java.util.HashMap;
import java.util.Scanner;

public class App {

    static void showMenu() {
        System.out.println("\n!--- Escolha uma opção ---!\n" + "1 - Configurar custo por KM\n"
                + "2 - Consultar trecho\n" + "3 - Consultar rota\n" + "4 - Terminar o programa\n");
    }

    static Scanner leia = new Scanner(System.in);
    static double custoKm = 0;

    public static void main(String[] args) throws Exception {

        HashMap<String, Integer> distancias = new HashMap<String, Integer>();
        distancias.put("POA-TORRES", 150);
        distancias.put("TORRES-POA", 148);
        distancias.put("POA-CURITIBA", 650);
        distancias.put("CURITIBA-POA", 645);
        
        int op = 0;
        do{
            op = 0;
            showMenu();
            System.out.println("Escolha a opção: ");
            op = Integer.parseInt(leia.nextLine());

            switch (op) {
                case 1:
                    configurarCustoKm();
                    break;
                case 2:
                    System.out.println(custoKm);
                    break;
                case 3:
                    break;
            }

        }while(op != 0);
    }

    public static void configurarCustoKm(){
        System.out.println("Informe o custo por KM: ");
        custoKm = Double.parseDouble(leia.nextLine());
        while(custoKm <= 0){
            System.out.println("Valor inválido. Digite novamente.");
            custoKm = Double.parseDouble(leia.nextLine());
        }
    }
}
