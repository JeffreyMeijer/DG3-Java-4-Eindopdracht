package com.dg3.main.views;
import java.awt.FlowLayout;

import javax.swing.*;

import com.dg3.main.controllers.MainController;
import com.dg3.main.controllers.OverzichtController;
import com.dg3.main.controllers.ProductenController;
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
	
	public void productPage() {
		JDialog dialog = new JDialog();
		dialog.setTitle("Product lijst");
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setSize(640, 480);
		dialog.setLayout(new FlowLayout());
		dialog.add(new ProductenController());
		dialog.setVisible(true);
	}
}
