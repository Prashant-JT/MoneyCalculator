package moneycalculator;

import moneycalculator.control.CalculateCommand;
import moneycalculator.persistance.CurrencyListLoader;
import moneycalculator.persistance.ExchangeRateLoader;
import moneycalculator.persistance.files.FileCurrencyListLoader;
import moneycalculator.persistance.rest.RestExchangeRateLoader;

public class Main {
   
    public static void main(String[] args) {
        CurrencyListLoader currencyLoader = new FileCurrencyListLoader("currencies.txt");
        ExchangeRateLoader ExchangeRateLoader = new RestExchangeRateLoader();
        
        MainFrame mainFrame = new MainFrame(currencyLoader.currencies());
        mainFrame.add(new CalculateCommand(mainFrame.getMoneyDialog(), mainFrame.getMoneyDisplay(), ExchangeRateLoader));
        
    }
    
}