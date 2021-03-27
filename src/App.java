import java.util.HashMap;
import java.util.Scanner;

public class App {

    static void mostrarMenu() {
        System.out.println("\n!--- Escolha uma opção ---!\n" + "1 - Configurar custo por KM\n"
                + "2 - Consultar trecho\n" + "3 - Consultar rota\n" + "4 - Terminar o programa\n");
    }

    static Scanner leia = new Scanner(System.in);
    static double custoKm = 0;
    static HashMap<String, Integer> distancias = new HashMap<String, Integer>();

    public static void main(String[] args) throws Exception {

        distancias.put("POA-TORRES", 150);
        distancias.put("TORRES-POA", 148);
        distancias.put("POA-CURITIBA", 650);
        distancias.put("CURITIBA-POA", 645);
        distancias.put("TORRES-FLORIPA", 765);
        distancias.put("FLORIPA-TORRES", 780);

        int op = 0;

        do {
            op = 0;
            mostrarMenu();
            System.out.println("Escolha a opção: ");
            op = Integer.parseInt(leia.nextLine());

            switch (op) {
            case 1:
                configurarCustoKm();
                break;
            case 2:
                consultarTrecho();
                break;
            case 3:
                consultarRota();
                break;
            }

        } while (op != 0);
    }

    public static void configurarCustoKm() {
        System.out.println("Informe o custo por KM: ");
        custoKm = Double.parseDouble(leia.nextLine());
        while (custoKm <= 0) {
            System.out.println("Valor inválido. Digite novamente.");
            custoKm = Double.parseDouble(leia.nextLine());
        }
    }

    public static void consultarTrecho() {

        String origem;
        String destino;
        String trecho;
        double custoTotal = 0;
        int distancia = 0;

        System.out.println("Digite o nome da cidade de origem: ");
        origem = leia.nextLine();
        System.out.println("Digite o nome da cidade de destino: ");
        destino = leia.nextLine();
        trecho = origem.toUpperCase() + "-" + destino.toUpperCase();

        if (distancias.containsKey(trecho)) {

            distancia = distancias.get(trecho);
            custoTotal = distancia * custoKm;

            System.out.println("Distancia entre " + origem + " e " + destino + " é " + distancia + " km.");
            System.out.println("Custo de R$ " + custoTotal);

        } else {
            System.out.println("Trecho inválido");
        }
    }

    // porto alegre, florianopolis, Curitiba
    // [porto alegre,florianopolis,curitiba]
    // [porto alegre-florianopolis,florianopolis-curitiba]

    public static void consultarRota() {

        String cidades;
        String rota;
        int distanciaTotal = 0;

        System.out.println("Digite o nome de duas ou mais cidades separadas por virgula: ");
        cidades = leia.nextLine();

        cidades = cidades.replaceAll("\\s", "");

        String[] aux = cidades.split(",");

        for (int i = 0; i < aux.length - 1; i++) {
            rota = aux[i].toUpperCase() + "-" + aux[i+1].toUpperCase();
            distanciaTotal += distancias.get(rota);
            System.out.println(rota + " -> " + distancias.get(rota)); 
        }

        System.out.println("Distancia total: " + distanciaTotal);
        System.out.println("O custo total é de: " + custoKm * distanciaTotal);
        System.out.println("O total de litros gastos foi de: " + distanciaTotal / 2.57);
        System.out.println("A viagem durou " + calcularKmDias(distanciaTotal) + " dias.");

    }

    public static double calcularKmDias(int km){
        double dias = (24 * km) / 238;
        return dias;
    }
}
