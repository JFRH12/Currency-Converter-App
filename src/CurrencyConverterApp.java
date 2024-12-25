import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrencyConverterApp {
    private static final String API_KEY = "031505551e6c410197a24f04";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";
    private static final ArrayList<String> conversionHistory = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Bienvenido a la aplicación de conversión de divisas");
            System.out.print("Ingrese la moneda base (ejemplo: USD, EUR): ");
            String baseCurrency = scanner.nextLine().toUpperCase();

            System.out.print("Ingrese la moneda a convertir (ejemplo: USD, EUR): ");
            String targetCurrency = scanner.nextLine().toUpperCase();

            System.out.print("Ingrese la cantidad a convertir: ");
            double amount;
            try {
                amount = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Cantidad inválida. Inténtalo de nuevo.");
                continue;
            }

            double conversionRate = getExchangeRate(baseCurrency, targetCurrency);
            if (conversionRate != -1) {
                double convertedAmount = amount * conversionRate;
                System.out.printf("Resultado: %.2f %s son %.2f %s\n", amount, baseCurrency, convertedAmount, targetCurrency);

                // Agregar al historial
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String timestamp = now.format(formatter);

                String record = String.format("[%s] %.2f %s a %.2f %s", timestamp, amount, baseCurrency, convertedAmount, targetCurrency);
                conversionHistory.add(record);
            }

            // Mostrar historial
            System.out.println("\nHistorial de conversiones:");
            for (String record : conversionHistory) {
                System.out.println(record);
            }

            System.out.print("\n¿Desea realizar otra conversión? (s/n): ");
            String choice = scanner.nextLine().toLowerCase();
            if (!choice.equals("s")) {
                System.out.println("¡Gracias por usar la aplicación!");
                break;
            }
        }
        scanner.close();
    }

    private static double getExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            URL url = new URL(API_URL + baseCurrency);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                Scanner responseScanner = new Scanner(conn.getInputStream());
                StringBuilder response = new StringBuilder();
                while (responseScanner.hasNext()) {
                    response.append(responseScanner.nextLine());
                }
                responseScanner.close();

                String jsonResponse = response.toString();
                HashMap<String, Object> data = new com.google.gson.Gson().fromJson(jsonResponse, HashMap.class);
                HashMap<String, Double> rates = (HashMap<String, Double>) data.get("conversion_rates");

                if (rates.containsKey(targetCurrency)) {
                    return rates.get(targetCurrency);
                } else {
                    System.out.println("Moneda objetivo no encontrada.");
                    return -1;
                }
            } else {
                System.out.println("Error al conectar con la API. Código: " + conn.getResponseCode());
                return -1;
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
            return -1;
        }
    }
}
