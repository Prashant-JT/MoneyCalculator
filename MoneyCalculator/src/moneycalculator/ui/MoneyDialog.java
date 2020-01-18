package moneycalculator.ui;

import moneycalculator.model.Currency;
import moneycalculator.model.Money;

public interface MoneyDialog {

    Money getMoneyFrom();
    
    Currency getCurrencyTo();

}
