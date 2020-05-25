package com.dg3.main.views;
import java.awt.FlowLayout;

import javax.swing.*;

import com.dg3.main.controllers.MainController;
import com.dg3.main.controllers.OverzichtController;
public class MainView extends View {
	public MainView() {
		display = new JLabel("Factuursysteem");
		this.add(display);
	}
	
	public void overzichtPage() {
		JDialog dialog = new JDialog();
		dialog.setTitle("Factuur Overzicht");
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setSize(640,480);
		dialog.setLayout(new FlowLayout());
		dialog.add(new OverzichtController());
		dialog.setVisible(true);
	}
}
