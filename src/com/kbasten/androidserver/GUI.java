package com.kbasten.androidserver;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JFrame;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;

	public GUI(String title, Dimension size) {
		super(title);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(size);
		setLocation(new Point(100, 100));
		setResizable(false);

		setLayout(new FlowLayout(FlowLayout.LEADING));
	}
}
