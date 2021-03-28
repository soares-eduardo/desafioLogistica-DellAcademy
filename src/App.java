import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;

public class App {

    static void mostrarMenu() {
        System.out.println("\n!--- Escolha uma opção ---!\n" + "1 - Configurar custo por KM\n"
                + "2 - Consultar trecho\n" + "3 - Consultar rota\n" + "4 - Terminar o programa\n");
    }

    static Scanner leia = new Scanner(System.in);
    static double custoKm = 0;
    
    //HASH MAP
    public static int linhasCSV = 24;
    public static int colunasCSV = 24;
    public static String separadorCSV = ";";
    public static String nomeCSV = "DNIT-Distancias.csv";

    // HashMap para armazenar os trechos lidos
    public static HashMap<String, Integer> distancias = new HashMap<String, Integer>();

    // Método que interpreta o CSV e armazena no hashmap o resultado
    public static void interpretaCSV() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(nomeCSV));

        String linhaReader = null;

        // Armazenamos a primeira linha(header) para montar o texto do trecho
        String headers[] = (bufferedReader.readLine()).split(separadorCSV);

        // Itera sobre todas as linhas e colunas
        for (int linha = 0; linha < linhasCSV; linha ++) {
            linhaReader = bufferedReader.readLine();
            String str[] = linhaReader.split(separadorCSV);
            for (int coluna = 0; coluna < colunasCSV; coluna++) {
                // Monta a string que representa o trecho usando o header
                String trecho = headers[coluna] + "-" + headers[linha];
                // Relaciona o trecho com a distância e armazena no hashmap
                distancias.put(trecho, Integer.parseInt(str[coluna]));
            }
        }
        bufferedReader.close();
    }

    public static void main(String[] args) throws Exception {

        // Tenta efetuar a interpretação do CSV, caso contrário imprime erro e encerra
        try {
            interpretaCSV();
        } catch (IOException e) {
            System.out.println("Erro ao ler o CSV! Encerrando...");
            System.exit(1); // Encerra a aplicação com erro
        }

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

    public static void consultarRota() {

        String cidades;
        String rota;
        int distanciaTotal = 0;

        System.out.println("Digite o nome de duas ou mais cidades separadas por virgula: ");
        cidades = leia.nextLine();

        //cidades = cidades.replaceAll("\\s", "");

        String[] aux = cidades.split(", ");

        for (int i = 0; i < aux.length - 1; i++) {
            rota = aux[i].toUpperCase() + "-" + aux[i + 1].toUpperCase();
            distanciaTotal += distancias.get(rota);
            System.out.println(rota + " -> " + distancias.get(rota));
        }

        System.out.println("Distancia total: " + distanciaTotal);
        System.out.println("O custo total é de: " + custoKm * distanciaTotal);
        System.out.println("O total de litros gastos foi de: " + distanciaTotal / 2.57);
        System.out.println("A viagem durou " + calcularKmDias(distanciaTotal) + " dias.");

    }

    public static double calcularKmDias(int km) {
        double dias = (24 * km) / 238;
        return dias;
    }
}
