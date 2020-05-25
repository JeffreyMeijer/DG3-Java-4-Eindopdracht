package com.dg3.main.controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.dg3.main.models.Model;
import com.dg3.main.views.MainView;
public class MainController extends JPanel {
	JButton productButton;
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
		productButton = new JButton("Producten");
		productButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				view.productPage();
			}
		});
		this.add(overzichtButton);
		this.add(productButton);
	}
}
