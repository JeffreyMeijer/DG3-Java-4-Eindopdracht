package com.dg3.main;
import javax.swing.*;
public class View extends JPanel {
	private JLabel display;
	public View() {
		display = new JLabel("Test");
		this.add(display);
	}
	
	public void setDisplay(String text) {
		display.setText(text);
	}
}
