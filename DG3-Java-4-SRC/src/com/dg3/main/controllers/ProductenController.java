package com.dg3.main.controllers;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.dg3.main.models.Model;
import com.dg3.main.views.ProductenView;

public class ProductenController extends JPanel {
	private ProductenView view;
	private Model model;
	
	public ProductenController() {
		model = new Model();
		view = new ProductenView();
		this.add(view);
		JTable table;
		String[] columns = {"ID", "Product", "Prijs"};
		try {
			Object[][] data = new Object[model.getProductenCount()][0];
			for(int i = 0; i < model.getProductenCount(); i++) {
				String product = model.getProductName(i+1);
				StringBuilder prijs = new StringBuilder(Double.toString(model.getProductPrijs(i+1)));
				prijs.insert(0, "€");
				data[i] = new Object[]{i+1, product, prijs};
			}
			DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
			    @Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
			table = new JTable(data,columns);
			table.setModel(tableModel);
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					if(me.getClickCount() == 2) {
						JTable target = (JTable)me.getSource();
						int row = target.getSelectedRow();
						JOptionPane.showMessageDialog(null, table.getValueAt(row, 0)); // Get productID
					}
				}
			});
			table.setPreferredScrollableViewportSize(new Dimension(500,50));
			table.setFillsViewportHeight(true);
			
			JScrollPane scrollPane = new JScrollPane(table);
			this.add(scrollPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
