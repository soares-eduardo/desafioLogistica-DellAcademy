import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class App {

    static Scanner leia = new Scanner(System.in);
    static double custoKm = 0;

    // Variaveis relativas ao carregamento do CSV no Hashmap
    static int linhasCSV = 24;
    static int colunasCSV = 24;
    static String separadorCSV = ";";
    static String nomeCSV = "DNIT-Distancias.csv";

    // HashMap para armazenar os trechos lidos
    static HashMap<String, Integer> distancias = new HashMap<String, Integer>();

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
            if (custoKm == 0) {
                configurarCustoKm(); // Pede que o usuário digite o custo por Km no seu primeiro acesso. Poderá
                                     // editar nas opções do menu.
            }
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
            case 4:
                op = 0; // Encerra o programa.
            }
        } while (op != 0);
    }

    // Método que expoem o menu principal.
    public static void mostrarMenu() {
        System.out.println("\n!--- Escolha uma opção ---!\n" + "1 - Configurar custo por KM\n"
                + "2 - Consultar trecho\n" + "3 - Consultar rota\n" + "4 - Terminar o programa\n");
    }

    // Método que interpreta o CSV e armazena no hashmap o resultado
    public static void interpretaCSV() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(nomeCSV));

        String linhaReader = null;

        // Armazenamos a primeira linha(header) para montar o texto do trecho
        String headers[] = (bufferedReader.readLine()).split(separadorCSV);

        // Itera sobre todas as linhas e colunas
        for (int linha = 0; linha < linhasCSV; linha++) {
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

    // Método que que adquiri do usuário o custo por Km
    public static void configurarCustoKm() {

        System.out.println("Informe o custo por KM: ");
        custoKm = Double.parseDouble(leia.nextLine());

        // Caso o usuário digite um valor negativo, o programa informa o erro e repete a
        // requisição.
        while (custoKm <= 0) {
            System.out.println("Valor inválido. Digite novamente.");
            custoKm = Double.parseDouble(leia.nextLine());
        }
    }

    // Método para consultar o trecho de uma cidade para outra
    public static void consultarTrecho() {

        String origem;
        String destino;
        String trecho; // Irá armazenar o trecho no formato da chave do Hashmap (Ex: ALAGOAS-PARÁ)
        double custoTotal = 0;
        int distancia = 0;

        System.out.println("Digite o nome da cidade de origem: ");
        origem = leia.nextLine();
        System.out.println("Digite o nome da cidade de destino: ");
        destino = leia.nextLine();

        // Após adquirir o nome da origem e do destino, o programa monta a String no
        // padrão da chave do Hashmap
        trecho = origem.toUpperCase() + "-" + destino.toUpperCase();

        // Verifica se a chave existe, ou seja, se é um trecho válido
        if (distancias.containsKey(trecho)) {

            // Busca a distancia do trecho informado
            distancia = distancias.get(trecho);

            // Calcula o custo total com a distancia adquirida
            custoTotal = distancia * custoKm;

            // Informa os dados ao usuário
            System.out.println("Distancia entre " + origem + " e " + destino + " é " + distancia + " km.");
            System.out.println("Custo de R$ " + custoTotal);

        } else {

            // Se o trecho for inválido (chave não encontrada no Hashmap), retorna a
            // seguinte
            // mensagem
            System.out.println("Trecho inválido");
        }
    }

    public static void consultarRota() {

        String cidades;
        String rota; // Será usada para armazenar os pares de trecho no padrão ORIGEM-DESTINO
        int distanciaTotal = 0; // Será usada para armazenar a distancia total da rota
        boolean trechoValido = true;

        // Coleta a rota do usuário em uma String
        System.out.println("Digite o nome de duas ou mais cidades separadas por virgula: ");
        cidades = leia.nextLine();

        // Separa a String por virgula e um espaço após ela, colocando os dados em um
        // array
        String[] aux = cidades.split(", ");
        String[] trechos = new String[aux.length - 1];

        // Percorre o array de cidades até seu penultimo indice, pois o ultimo é o
        // destino final.
        for (int i = 0; i < aux.length - 1; i++) {

            // Formata os pares de cidades no padrão da chave ORIGEM-DESTINO
            rota = aux[i].toUpperCase() + "-" + aux[i + 1].toUpperCase();
            trechos[i] = rota;

            if (distancias.containsKey(rota)) {
                // A cada consulta no Hashmap, coleta a distancia do trecho
                distanciaTotal += distancias.get(rota);

                // Informa o trecho e sua distancia
                System.out.println(rota + " -> " + distancias.get(rota));
            } else {
                // Caso haja alguma cidade inválida, retorna:
                System.out.println("Trecho inválido");
                trechoValido = false;
                break;
            }
        }

        // Se o trecho for válido, retorna o resto das informações. Senão, permanece a
        // mensagem de "Trecho inválido"
        if (trechoValido) {

            // Expoem as informações adquiridas
            System.out.println("Distancia total: " + distanciaTotal);
            System.out.println("O custo total é de: " + custoKm * distanciaTotal);
            System.out.println("O total de litros gastos foi de: " + distanciaTotal * 2.57);
            System.out.println("A viagem durou " + distanciaTotal / 283 + " dias.");
        }
    }
}
