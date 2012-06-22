package com.kbasten.androidserver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

public class Log extends JTextArea {
	private static String LINE_BREAK = "\n";
	
	public Log(){
		setPreferredSize(new Dimension(270, 220));
		setBorder(BorderFactory.createLoweredBevelBorder());
		setFont(new Font("Courier New", Font.PLAIN, 12));
		setBackground(Color.white);
		setEditable(false);
	}

	public void msg(String msg){
		append(getTimestamp() + msg + LINE_BREAK);
	}
	
	private static String getTimestamp() {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date d = new Date();
		return "[" + df.format(d) + "] ";
	}
}