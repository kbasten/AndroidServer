package com.kbasten.androidserver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent a) {
		JButton btn = (JButton) a.getSource();

		if (btn.getText().equals("Start server")) { // redneck
			btn.setText("Stop server");
			Main.invokeServerStart();
		} else {
			btn.setText("Start server");
			Main.invokeServerStop();
		}
	}
}
