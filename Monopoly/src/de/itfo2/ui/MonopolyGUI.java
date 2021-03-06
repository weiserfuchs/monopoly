package de.itfo2.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

import de.itfo2.fields.Grundstueck;
import de.itfo2.network.Connector;
import de.itfo2.objects.Spieler;
import de.itfo2.objects.Spielfeld;
import de.itfo2.objects.Verwalter;
import de.itfo2.util.DialogCreator;
import de.itfo2.util.GuiFeldMouseListener;
import de.itfo2.util.HypothekenListener;

public class MonopolyGUI implements MonopolyGUIInterface {

	private static MonopolyGUI	instance	= null;
	GUISpielfeld				spielfeld;

	private MonopolyGUI() {
	}

	public static MonopolyGUI getInstance() {
		if (instance == null) {
			instance = new MonopolyGUI();
		}
		return instance;
	}

	public void setSpielfeld(Spielfeld spielfeld) {
		this.spielfeld = new GUISpielfeld(spielfeld);
	}

	@Override
	public void rueckeVor(int anzahl) {
		Spieler curSpieler = Verwalter.getInstance().getCurSpieler();
		int aktuellerPlatz = curSpieler.getPlatz();
		List<Spieler> spielerliste = Connector.getInstance().getSpielerliste();

		spielfeld.setSpielerVisible(aktuellerPlatz,
				spielerliste.indexOf(curSpieler), false);
		spielfeld.setSpielerVisible(aktuellerPlatz + anzahl,
				spielerliste.indexOf(curSpieler), true);

		MenuPanel menuPanel = spielfeld.getFeld(aktuellerPlatz).getMenuPanel();
		if (menuPanel != null) {
			menuPanel.getbBuy().setVisible(false);
			menuPanel.getbBuy().setEnabled(false);
		}

		GUIFeld aktuellesFeld = spielfeld.getFeld(aktuellerPlatz + anzahl);

		if (aktuellesFeld.getMenuPanel() != null) {
			if(Verwalter.getInstance().getMeinSpieler().equals(curSpieler)){
				Grundstueck gr = (Grundstueck) aktuellesFeld.getFeld();
				if (gr.getBesitzer() == null) {
					aktuellesFeld.getMenuPanel().getbBuy().setVisible(true);
					aktuellesFeld.getMenuPanel().getbBuy().setEnabled(true);
				}else{
					spielfeld.getFeld(aktuellerPlatz).getMenuPanel().getbBuy()
					.setVisible(false);
					spielfeld.getFeld(aktuellerPlatz).getMenuPanel().getbBuy()
					.setEnabled(false);
				}
			}
		}
	}

	@Override
	public void rueckeAuf(int platz) {
		Spieler curSpieler = Verwalter.getInstance().getCurSpieler();

		int aktuellerPlatz = curSpieler.getPlatz();

		List<Spieler> spielerliste = Connector.getInstance().getSpielerliste();
		spielfeld.setSpielerVisible(aktuellerPlatz,
				spielerliste.indexOf(curSpieler), false);
		spielfeld.setSpielerVisible(platz, spielerliste.indexOf(curSpieler),
				true);

		MenuPanel menuPanel = spielfeld.getFeld(aktuellerPlatz).getMenuPanel();
		if (menuPanel != null) {
			menuPanel.getbBuy().setVisible(false);
			menuPanel.getbBuy().setEnabled(false);
		}

		if (spielfeld.getFeld(platz).getMenuPanel() != null) {
			Grundstueck gr = (Grundstueck) spielfeld.getFeld(platz).getFeld();
			if (gr.getBesitzer() == null) {
				spielfeld.getFeld(platz).getMenuPanel().getbBuy()
						.setVisible(true);
				spielfeld.getFeld(platz).getMenuPanel().getbBuy()
						.setEnabled(true);
			}else{
				spielfeld.getFeld(platz).getMenuPanel().getbBuy()
				.setVisible(false);
				spielfeld.getFeld(platz).getMenuPanel().getbBuy()
				.setEnabled(false);
			}
		}
	}

	public void naechsterSpieler() {
		Spieler curSpieler = Verwalter.getInstance().getCurSpieler();
		MenuPanel menuPanel = spielfeld.getFeld(curSpieler.getPlatz())
				.getMenuPanel();

		if (menuPanel != null) {
			menuPanel.getbBuy().setVisible(false);
			menuPanel.getbBuy().setEnabled(false);
		}

		setNextButtonEnabled(false);
	}

	public void updateHypothekButtons() {
		for (int i = 0; i < spielfeld.getFelder().size(); i++) {
			GUIFeld guiFeld = spielfeld.getFelder().get(i);

			if (guiFeld.getMenuPanel() != null) {
				Grundstueck gr = (Grundstueck) guiFeld.getFeld();
				JButton hypothekButton = guiFeld.getMenuPanel().getbHypothek();

				if (gr.getBesitzer() != null) {
					if (gr.getBesitzer().equals(
							Verwalter.getInstance().getCurSpieler())) {
						if (gr.isBelastet()) {
							hypothekButton.setVisible(true);
							hypothekButton.setEnabled(true);
						} else {
							hypothekButton.setVisible(false);
							hypothekButton.setEnabled(false);
						}
					} else {
						hypothekButton.setVisible(false);
						hypothekButton.setEnabled(false);
					}
				} else {
					hypothekButton.setVisible(false);
					hypothekButton.setEnabled(false);
				}
			}
		}
	}

	@Override
	public void geheInsGefaengnis(Spieler spieler) {

	}

	@Override
	public void wuerfeln() {

	}

	@Override
	public void addSpieler(int pos, Spieler spieler) {
		spielfeld.addSpieler(pos, spieler);
	}

	public void updateFeld() {
		spielfeld.updateFeld();
	}

	public void setRollDiceButtonActionListener(ActionListener listener) {
		spielfeld.getMiddlePanel().getRollDiceButton()
				.addActionListener(listener);
	}

	public void setRollDiceButtonEnabled(boolean enabled) {
		spielfeld.getMiddlePanel().getRollDiceButton().setEnabled(enabled);
	}

	public void setStartButtonActionListener(ActionListener listener) {
		spielfeld.getMiddlePanel().getStartButton().addActionListener(listener);
	}

	public void setStartButtonEnabled(boolean enabled) {
		spielfeld.getMiddlePanel().getStartButton().setEnabled(enabled);
	}

	public void setNextButtonActionListener(ActionListener listener) {
		spielfeld.getMiddlePanel().getNextButton().addActionListener(listener);
	}

	public void setNextButtonEnabled(boolean enabled) {
		spielfeld.getMiddlePanel().getNextButton().setEnabled(enabled);
	}

	public void setLoginButtonActionListener(ActionListener listener) {
		spielfeld.getMiddlePanel().getLoginButton().addActionListener(listener);
	}

	public void setLoginButtonEnabled(boolean enabled) {
		spielfeld.getMiddlePanel().getLoginButton().setEnabled(enabled);
	}

	public void setGemeinschaftskartenButtonActionListener(
			ActionListener listener) {
		spielfeld.getMiddlePanel().getGemeinschaftskartenButton()
				.addActionListener(listener);
	}

	public void setGemeinschaftskartenButtonEnabled(boolean enabled) {
		spielfeld.getMiddlePanel().getGemeinschaftskartenButton()
				.setEnabled(enabled);
	}

	public void setEreigniskartenButtonActionListener(ActionListener listener) {
		spielfeld.getMiddlePanel().getEreigniskartenButton()
				.addActionListener(listener);
	}

	public void setEreigniskartenButtonEnabled(boolean enabled) {
		spielfeld.getMiddlePanel().getEreigniskartenButton()
				.setEnabled(enabled);
	}

	public void setBuyButtonActionListener(ActionListener listener) {
		for (int i = 0; i < spielfeld.getFelder().size(); i++) {
			if (spielfeld.getFelder().get(i).getMenuPanel() != null)
				spielfeld.getFelder().get(i).getMenuPanel().getbBuy()
						.addActionListener(listener);
		}
	}

	public void setBuildHouseButtonActionListener(ActionListener listener) {
		for (int i = 0; i < spielfeld.getFelder().size(); i++) {
			if (spielfeld.getFelder().get(i).getMenuPanel() != null)
				spielfeld.getFelder().get(i).getMenuPanel().getbBuild()
						.addActionListener(listener);
		}
	}

	public void setHypothekButtonActionListener() {
		for (int i = 0; i < spielfeld.getFelder().size(); i++) {
			if (spielfeld.getFelder().get(i).getMenuPanel() != null)
				spielfeld
						.getFelder()
						.get(i)
						.getMenuPanel()
						.getbHypothek()
						.addActionListener(
								new HypothekenListener(spielfeld.getFelder()
										.get(i)));
		}
	}

	public void setGrundstueckMouseListener() {
		for (int i = 0; i < spielfeld.getFelder().size(); i++) {
			if (spielfeld.getFelder().get(i).getFeld() instanceof Grundstueck)
				spielfeld
						.getFelder()
						.get(i)
						.addMouseListener(
								new GuiFeldMouseListener(spielfeld.getFelder()
										.get(i)));
		}
	}

	public void setGUIFeldHypothek(int pos, boolean visible) {
		spielfeld.getFelder().get(pos).getLabelHypothek().setVisible(visible);
	}

	public void kaufeFeld() {
		spielfeld.getFelder()
				.get(Verwalter.getInstance().getCurSpieler().getPlatz())
				.faerbeFeld(Color.BLUE);
	}

	public void baueHaus() {
		spielfeld.getFelder()
				.get(Verwalter.getInstance().getCurSpieler().getPlatz())
				.addHouse();
	}

	public StatusPanel getStatusPanel(int pos) {
		return spielfeld.getMiddlePanel().getStatuspanel(pos);
	}

	public void createPopupDialog(String text) {
		DialogCreator.createOKDialog(text, spielfeld);
	}

	public int createPopupChoiceDialog(String text) {
		return DialogCreator.createChoiceDialog(text, spielfeld);
	}

	public void addLogMessage(String text) {
		spielfeld.getMiddlePanel().getChatpanel()
				.addUsermessage("Spiel: ", text);
	}

	public void createLoginDialog() {
		final JDialog dialog = new JDialog(spielfeld, "Login");
		dialog.setSize(new Dimension(300, 200));
		final JTextField fieldName = new JTextField();
		fieldName.setSize(200, 30);

		JButton buttonLogin = new JButton("Login");
		buttonLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Verwalter.getInstance().login(fieldName.getText());
				dialog.dispose();
			}
		});

		dialog.setLayout(new BorderLayout());
		dialog.add(fieldName, BorderLayout.NORTH);
		dialog.add(buttonLogin, BorderLayout.SOUTH);
		dialog.pack();
		dialog.setVisible(true);
	}

	public void sperren(Spieler meinSpieler) {
		if(spielfeld.getFeld(meinSpieler.getPlatz()) != null){
			if(spielfeld.getFeld(meinSpieler.getPlatz()).getMenuPanel() != null){
				if(spielfeld.getFeld(meinSpieler.getPlatz()).getMenuPanel().getbBuy() != null){
					spielfeld.getFeld(meinSpieler.getPlatz()).getMenuPanel().getbBuy().setVisible(false);
					spielfeld.getFeld(meinSpieler.getPlatz()).getMenuPanel().getbBuy().setEnabled(false);
				}
			}
		}
		setNextButtonEnabled(false);
	}

	public void entsperren(Spieler meinSpieler) {
		setRollDiceButtonEnabled(true);
	}

	public void setTitle(String title) {
		spielfeld.setTitle(title);
	}
}