package com.kbasten.androidserver;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class Main {
	private static Log l;
	private static JTextField txtPort;

	private static Server s;

	private static Border brd = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

	private static DefaultListModel<String> clientList = new DefaultListModel<String>();

	private static void createAndShowGUI() {
		// create window
		GUI window = new GUI("Android Control Server", new Dimension(800, 600));

		JPanel leftFrame = new JPanel();
		leftFrame.setLayout(new FlowLayout());
		leftFrame.setPreferredSize(new Dimension(600, 600));

		JPanel rightFrame = new JPanel();
		rightFrame.setLayout(new FlowLayout());
		rightFrame.setPreferredSize(new Dimension(180, 600));

		// -- SETTINGS PANEL -- //
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		settingsPanel.setBorder(BorderFactory.createTitledBorder(brd, "Server"));
		settingsPanel.setPreferredSize(new Dimension(600, 60));

		JLabel portLabel = new JLabel("Port:");
		settingsPanel.add(portLabel);

		txtPort = new JTextField("27031", 4);
		settingsPanel.add(txtPort);

		JButton btnStartServer = new JButton("Start server");
		btnStartServer.addActionListener(new ButtonListener());
		settingsPanel.add(btnStartServer);

		// -- LOG WRAPPER -- //
		JPanel logWrap = new JPanel();
		logWrap.setBorder(BorderFactory.createTitledBorder(brd, "Log"));
		logWrap.setPreferredSize(new Dimension(600, 400));

		// log text area
		l = new Log();
		// JScrollPane logScroll = new JScrollPane(l);
		// logScroll.setPreferredSize(l.getPreferredSize());

		// log text input
		JTextField logInput = new JTextField();
		logInput.setPreferredSize(new Dimension(570, 20));

		// add log elements to log wrapper
		logWrap.add(l);
		logWrap.add(logInput);

		leftFrame.add(settingsPanel);
		leftFrame.add(logWrap);

		// -- CLIENT WRAPPER -- //
		JPanel clientWrap = new JPanel();
		clientWrap.setBorder(BorderFactory.createTitledBorder(brd, "Connected clients"));
		clientWrap.setPreferredSize(new Dimension(180, 300));

		// client list
		JList<String> cl = new JList<String>(clientList);

		cl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cl.setLayoutOrientation(JList.VERTICAL); // default

		// view for the client list
		JScrollPane clientScroller = new JScrollPane(cl);
		clientScroller.setPreferredSize(new Dimension(150, 220));

		// add view to client wrapper
		clientWrap.add(clientScroller);

		rightFrame.add(clientWrap);

		// add all elements to window
		window.add(leftFrame);
		window.add(rightFrame);
		// show window
		window.setVisible(true);

		// focus the log input
		logInput.requestFocus();
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	public static void invokeServerStart() {
		s = new Server(l, Integer.parseInt(txtPort.getText()));
		new Thread(new Runnable() {
			public void run() {
				try {
					s.startServer();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}).start();
	}

	public static void invokeServerStop() {
		if (s.isRunning()) {
			s.stopRunning();
		}
	}
}
