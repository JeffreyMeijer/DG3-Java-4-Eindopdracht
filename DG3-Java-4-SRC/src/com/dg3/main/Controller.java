package com.dg3.main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
public class Controller extends JPanel {
	JButton clickButton;
	private View view;
	private Model model;
	
	public Controller() {
		model = new Model();
		view = new View();
		this.add(view);
		
		clickButton = new JButton("Test");
		clickButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				view.setDisplay("Button clicked!");
			}
		});
		this.add(clickButton);
	}
}
