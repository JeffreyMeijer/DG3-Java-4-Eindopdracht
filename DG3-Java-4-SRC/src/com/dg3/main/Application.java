package com.dg3.main;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import com.dg3.main.controllers.MainController;

public class Application {
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setTitle("Factuur Systeem");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640,480);
		frame.setLayout(new FlowLayout());
		frame.add(new MainController());
		frame.setVisible(true);
	}
}
