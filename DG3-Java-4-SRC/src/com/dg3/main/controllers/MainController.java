package com.dg3.main.controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.dg3.main.models.Model;
import com.dg3.main.views.MainView;
public class MainController extends JPanel {
	JButton clickButton;
	JButton overzichtButton;
	private MainView view;
	private Model model;
	
	public MainController() {
		model = new Model();
		view = new MainView();
		this.add(view);
		
		overzichtButton = new JButton("Overzicht");
		overzichtButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.overzichtPage();
			}
		});
		this.add(overzichtButton);
		
	}
}
