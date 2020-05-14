package com.dg3.main.views;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class View extends JPanel {
	protected JLabel display;
	
	public void setDisplay(String text) {
		display.setText(text);
	}
	
}
