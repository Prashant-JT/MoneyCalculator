package moneycalculator.persistance.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import moneycalculator.model.Currency;
import moneycalculator.model.ExchangeRate;
import moneycalculator.persistance.ExchangeRateLoader;

public class RestExchangeRateLoader implements ExchangeRateLoader {

    @Override
    public ExchangeRate load(Currency from, Currency to) {
        try {
            return new ExchangeRate(from, to, read(from.getCode(), to.getCode()));
        } catch (IOException ex) {
            return null;
        }
    }

    private double read(String from, String to) throws IOException {
        String line = read(new URL("http://free.currencyconverterapi.com/api/v5/convert?q=" + 
                from + "_" + to + "&compact=y&apiKey=7e627ef1f163c48b0e71"));
        return Double.parseDouble(line.substring(line.indexOf(to)+5, line.indexOf("}")));
    }

    private String read(URL url) throws IOException {
        InputStream is = url.openStream();
        byte[] buffer = new byte[1024];
        int lenght = is.read(buffer);
        return new String(buffer, 0, lenght);
    }
    
}
