package com.dg3.main;
import java.awt.FlowLayout;

import javax.swing.JFrame;

public class Application {
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640,480);
		frame.setLayout(new FlowLayout());
		frame.add(new Controller());
		frame.setVisible(true);
	}
}
