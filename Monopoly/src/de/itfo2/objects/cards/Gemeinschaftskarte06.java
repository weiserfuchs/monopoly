package de.itfo2.objects.cards;


import de.itfo2.objects.Verwalter;
import de.itfo2.ui.MonopolyGUI;

public class Gemeinschaftskarte06 extends Karte{

    final String text = "Aus Lagerverkaeufen erhaelst du 100 Euro";

    public Gemeinschaftskarte06() {

    }

    @Override
    public void effect() {
        Verwalter.getInstance().getCurSpieler().addGeld(100); 
        System.out.println(text);
        MonopolyGUI.getInstance().createPopupDialog(text);
        MonopolyGUI.getInstance().addLogMessage(Verwalter.getInstance().getCurSpieler().getName() +": "+ text);

    }
}
