import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CurrencyConverterApp {

    // Clave de la API
    private static final String API_KEY = "031505551e6c410197a24f04";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    // Historial de conversiones
    private static final List<String> conversionHistory = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, String> currencies = loadCurrencies();
        boolean exit = false;

        while (!exit) {
            //clearScreen();
            System.out.println("--- Conversor de Divisas ---");
            System.out.println("1. Convertir Divisas");
            System.out.println("2. Ver Historial de Conversiones");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (choice) {
                case 1:
                    performConversion(scanner, currencies);
                    break;
                case 2:
                    displayHistory();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    pause();
            }
        }
        System.out.println("Gracias por usar el Conversor de Divisas.");
    }

    // Método para realizar la conversión de divisas
    private static void performConversion(Scanner scanner, Map<Integer, String> currencies) {
        //clearScreen();
        System.out.println("--- Lista de Divisas ---");
        currencies.forEach((key, value) -> System.out.println(key + ". " + value));

        System.out.print("Seleccione la divisa de origen (número): ");
        int fromChoice = scanner.nextInt();
        System.out.print("Seleccione la divisa de destino (número): ");
        int toChoice = scanner.nextInt();

        if (!currencies.containsKey(fromChoice) || !currencies.containsKey(toChoice)) {
            System.out.println("Selección inválida de divisas.");
            pause();
            return;
        }

        String fromCurrency = currencies.get(fromChoice);
        String toCurrency = currencies.get(toChoice);

        System.out.print("Ingrese la cantidad a convertir: ");
        double amount = scanner.nextDouble();

        double convertedAmount = fetchConversionRate(fromCurrency, toCurrency, amount);
        if (convertedAmount >= 0) {
            System.out.printf("%.2f %s son %.2f %s\n", amount, fromCurrency, convertedAmount, toCurrency);
            String timestamp = LocalDateTime.now().toString();
            conversionHistory.add(String.format("[%s] %.2f %s -> %.2f %s", timestamp, amount, fromCurrency, convertedAmount, toCurrency));
        }
        pause();
    }

    // Método para obtener la tasa de conversión desde la API
    private static double fetchConversionRate(String fromCurrency, String toCurrency, double amount) {
        try {
            String urlString = API_URL + fromCurrency;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parsear respuesta JSON
            String json = response.toString();
            String searchKey = "\"" + toCurrency + "\":";
            int index = json.indexOf(searchKey);

            if (index != -1) {
                int startIndex = index + searchKey.length();
                int endIndex = json.indexOf(",", startIndex);
                if (endIndex == -1) endIndex = json.indexOf("}", startIndex);
                double rate = Double.parseDouble(json.substring(startIndex, endIndex));
                return amount * rate;
            } else {
                System.out.println("No se pudo obtener la tasa de cambio.");
            }
        } catch (Exception e) {
            System.out.println("Error al conectarse con la API: " + e.getMessage());
        }
        return -1;
    }

    // Método para mostrar el historial de conversiones
    private static void displayHistory() {
        //clearScreen();
        System.out.println("--- Historial de Conversiones ---");
        if (conversionHistory.isEmpty()) {
            System.out.println("No hay conversiones registradas.");
        } else {
            conversionHistory.forEach(System.out::println);
        }
        pause();
    }

    // Método para cargar las divisas disponibles
    private static Map<Integer, String> loadCurrencies() {
        Map<Integer, String> currencies = new HashMap<>();
        currencies.put(1, "USD");
        currencies.put(2, "EUR");
        currencies.put(3, "GBP");
        currencies.put(4, "JPY");
        currencies.put(5, "AUD");
        currencies.put(6, "CAD");
        currencies.put(7, "CLP");
        currencies.put(8, "BRL");
        currencies.put(9, "ARS");
        currencies.put(10, "MXN");
        return currencies;
    }

    // Método para limpiar la pantalla
    /*private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception ex) {
            System.out.println("No se pudo limpiar la pantalla.");
        }
    }
*/

    // Método para pausar la ejecución
    private static void pause() {
        System.out.println("Presione Enter para continuar...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
