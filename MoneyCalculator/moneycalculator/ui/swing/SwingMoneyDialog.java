package moneycalculator.ui.swing;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import moneycalculator.model.Currency;
import moneycalculator.model.Money;
import moneycalculator.ui.MoneyDialog;

public class SwingMoneyDialog extends JPanel implements MoneyDialog {
    private final Currency[] currencies;
    private String amount;
    private Currency currencyFrom;
    private Currency currencyTo;
    private JComboBox comboTo;

    public SwingMoneyDialog(Currency[] currencies) {
        this.currencies = currencies;
        this.add(amount());
        this.add(currencyFrom());
        this.add(currencyTo());
    }

    @Override
    public Money getMoneyFrom() {
        return new Money(Double.parseDouble(amount), currencyFrom);
    }
 
    @Override
    public Currency getCurrencyTo() {
        return currencyTo;
    }
    
    private Component amount() {
        JTextField textField = new JTextField();
        textField.setColumns(10);
        textField.getDocument().addDocumentListener(amountChanged());
        amount = textField.getText();
        return textField;
    }

    private Component currencyFrom() {
        JComboBox comboFrom = new JComboBox(currencies);
        comboFrom.addItemListener(currencyChangedFrom());
        currencyFrom = (Currency) comboFrom.getSelectedItem();
        return comboFrom;
    }

    private Component currencyTo() {
        comboTo = new JComboBox(currencies);
        comboTo.removeItem(currencyFrom);
        comboTo.addItemListener(currencyChangedTo());
        currencyTo = (Currency) comboTo.getSelectedItem();
        return comboTo;
    }
    
    private ItemListener currencyChangedFrom() {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    return;
                }
                comboTo.addItem(currencyFrom); 
                currencyFrom = (Currency) e.getItem();             
                comboTo.removeItem(currencyFrom);
            }
        };
    }

    private ItemListener currencyChangedTo() {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    return;
                }
                currencyTo = (Currency) e.getItem();
            }
        };
    }
    
    private DocumentListener amountChanged() {
        return new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                amountChanged(e.getDocument());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                amountChanged(e.getDocument());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                amountChanged(e.getDocument());
            }

            private void amountChanged(Document document) {
                try {
                    amount = document.getText(0, document.getLength());
                } catch (BadLocationException ex) {
                }
            }
        };
    }
    
}
