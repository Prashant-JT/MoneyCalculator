package moneycalculator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import moneycalculator.model.Money;
import moneycalculator.model.ExchangeRate;
import moneycalculator.model.Currency;
import moneycalculator.model.CurrencyList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.Month;
import java.util.Scanner;

public class MoneyCalculator {
    private CurrencyList currencyList;
    private Money money;
    private Currency currencyTo;
    private ExchangeRate exchangeRate;

    public MoneyCalculator(){
        this.currencyList = new CurrencyList();
    }
    
    public static void main(String[] args) throws Exception {
        MoneyCalculator moneyCalculator = new MoneyCalculator();
        moneyCalculator.execute();
    }
    
    private void execute() throws Exception {
        input();
        process();
        output();
    }
    
    private void input(){
        System.out.println("Introduzca una cantidad: ");
        Scanner scanner = new Scanner(System.in);
        double amount = Double.parseDouble(scanner.next());
        
        while (true) {
            System.out.println("Introduzca código divisa origen");
            Currency currency = currencyList.get(scanner.next());
            money = new Money(amount, currency);
            if (currency != null) break;
            System.out.println("Divisa no conocida");
        }

        while (true) {
            System.out.println("Introduzca codigo divisa destino");
            currencyTo = currencyList.get(scanner.next());
            if (currencyTo != null) break;
            System.out.println("Divisa no conocida");
        }
    }
    
    private void process() throws Exception{
        exchangeRate = getExchangeRate(money.getCurrency(), currencyTo);
    }
    
    private void output(){
        System.out.println(money.getAmount() + " "
                + money.getCurrency().getSymbol() + " equivalen a: " 
                + exchangeRate.getRate() * money.getAmount() + " " 
                + currencyTo.getSymbol());
    }

    private static ExchangeRate getExchangeRate(Currency from, Currency to) throws IOException {
        URL url = new URL("http://free.currencyconverterapi.com/api/v5/convert?q=" + 
                from.getCode() + "_" + to.getCode() + "&compact=y&apiKey=7e627ef1f163c48b0e71");
        URLConnection connection = url.openConnection();
        try (BufferedReader reader = 
                new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
            String line = reader.readLine();
            JsonParser parser = new JsonParser(); 
            JsonObject gsonObj = parser.parse(line).getAsJsonObject();
            JsonPrimitive ratePrimitive = gsonObj.getAsJsonPrimitive("val");
            double rate = ratePrimitive.getAsDouble();
            return new ExchangeRate(from, to, LocalDate.of(2019, Month.MAY, 20), rate);
        }
    }
    
}