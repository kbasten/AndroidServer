package com.kbasten.androidserver;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.TextField;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class Main {
	private static Log l;

	private static Border brd = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	
	private static DefaultListModel<String> clientList = new DefaultListModel<String>();
	
    private static void createAndShowGUI() {
    	// create window
		JFrame window = new JFrame("Android Control Server");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(new Dimension(500, 500));
		window.setLocation(new Point(100, 100));
		window.setResizable(false);

		window.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		//-- LOG WRAPPER --//
		JPanel logWrap = new JPanel();
		logWrap.setBorder(BorderFactory.createTitledBorder(brd, "Log"));
		logWrap.setPreferredSize(new Dimension(300, 300));
		
		// log text area
		l = new Log();
//		JScrollPane logScroll = new JScrollPane(l);
//		logScroll.setPreferredSize(l.getPreferredSize());
		
		// log text input
		TextField logInput = new TextField();
		logInput.setPreferredSize(new Dimension(270, 20));
		
		// add log elements to log wrapper
		logWrap.add(l);
		logWrap.add(logInput);
		
		//-- CLIENT WRAPPER --//
		JPanel clientWrap = new JPanel();
		clientWrap.setBorder(BorderFactory.createTitledBorder(brd, "Connected clients"));
		clientWrap.setPreferredSize(new Dimension(180, 300));
		
		// client list
		
		clientList.addElement("fdsa");
		clientList.addElement("fdsgfd");
		
		JList<String> cl = new JList<String>(clientList);
		
		cl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cl.setLayoutOrientation(JList.VERTICAL); // default
		
		// view for the client list
		JScrollPane clientScroller = new JScrollPane(cl);
		clientScroller.setPreferredSize(new Dimension(150, 220));
		
		// add view to client wrapper
		clientWrap.add(clientScroller);
		
		// add all elements to window
		window.add(logWrap);
		window.add(clientWrap);
		// show window
		window.setVisible(true);
		
		// focus the log input
		logInput.requestFocus();
		
		// done building gui, start server
		final Server s = new Server(l, 27031);
		new Thread(new Runnable(){
			public void run(){
				try {
					s.startServer();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
		}).start();

    }
	
	public static void main(String[] args) throws IOException, InterruptedException {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}
