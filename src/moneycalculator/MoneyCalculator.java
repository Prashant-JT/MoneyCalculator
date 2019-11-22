package moneycalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MoneyCalculator {
    private double amount;
    private Map<String, Currency> currencies = new HashMap<>();
    private Currency currencyFrom;
    private Currency currencyTo;
    private double exchangeRate;

    public MoneyCalculator(){
        currencies.put("USD", new Currency("USD", "Dólar americano", "$"));
        currencies.put("EUR", new Currency("EUR", "Euros", "€"));
        currencies.put("GBP", new Currency("GBP", "Libras Esterlinas", "£"));
    }
    
    public static void main(String[] args) throws IOException {
        MoneyCalculator moneyCalculator = new MoneyCalculator();
        moneyCalculator.execute();
    }
    
    private void execute() throws IOException {
        input();
        process();
        output();
    }
    
    private void input(){
        System.out.println("Introduce una cantidad: ");
        Scanner scanner = new Scanner(System.in);
        amount = Double.parseDouble(scanner.next());
        
        System.out.println("Introduzca divisa origen");
        currencyFrom = currencies.get(scanner.next().toUpperCase());   

        System.out.println("Introduzca divisa destino");
        currencyTo = currencies.get(scanner.next().toUpperCase());
    }
    
    private void process() throws IOException{
        this.exchangeRate = getExchangeRate(currencyFrom.getCode(), currencyTo.getCode());
    }
    
    private void output(){
        System.out.println(amount + " " + currencyFrom.getSymbol() + " equivalen a: " + amount*exchangeRate + " " + currencyTo.getSymbol());
    }
    
    private static double getExchangeRate(String from, String to) throws IOException{
        URL url = new URL("http://free.currencyconverterapi.com/api/v5/convert?q=" + from + "_" + to + "&compact=y&apiKey=1eb50c98f4a50de7d844");
        MoneyCalculator moneyCalculator = new MoneyCalculator();
        URLConnection connection = url.openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
            String line = reader.readLine();
            String line1 = line.substring(line.indexOf(to) + 12, line.indexOf("}"));
            return Double.parseDouble(line1);
        }
    }
    
}