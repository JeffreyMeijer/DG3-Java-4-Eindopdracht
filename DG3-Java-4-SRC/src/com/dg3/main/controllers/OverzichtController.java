package com.dg3.main.controllers;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.mail.*;  
import javax.mail.internet.*;
import javax.activation.*;

import com.dg3.main.models.Model;
import com.dg3.main.views.OverzichtView;

public class OverzichtController extends JPanel {
	private OverzichtView view;
	private Model model;
	public OverzichtController() {
		model = new Model();
		view = new OverzichtView();
		this.add(view);
		JTable table;
		String[] columns = {"Factuur", "Klant", "Factuurdatum", "Totaal", "Status"};
		try {
			Object[][] data = new Object[model.getFacturenCount()][0];
			for(int i = 0; i < model.getFacturenCount(); i++) {
				ResultSet results = model.getBuyer(model.getAccountID(i+1));
				String firstname = results.getString(2);
				String lastname = results.getString(3);
				String name = firstname + " " + lastname;
				data[i] = new Object[]{i+1, name, model.getFactuurDatum(i+1),model.getTotaalPrijs(i+1), model.getFactuurStatus(i+1)};
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
						Object[] options = {"Versturen", "Annuleren"};
						JTable target = (JTable)me.getSource();
						int row = target.getSelectedRow();
						String s = (String)JOptionPane.showInputDialog(null,null);
						if ((s != null) && (s.length() > 0)) {
							String to = s;
							String host = "smtp.gmail.com";
							final String user = "jeffreykiller6723@gmail.com";
							final String password = "anglito564";
							
						    Properties properties = System.getProperties();
						    properties.put("mail.smtp.host", host); 
						    properties.put("mail.smtp.auth", true);
						    properties.put("mail.smtp.starttls.enable", true);
						    properties.put("mail.smtp.port", 587);
						    
						    Session session = Session.getDefaultInstance(properties);  
						    try {
						         MimeMessage message = new MimeMessage(session);  
						         message.setFrom(new InternetAddress(user));  
						         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
						         message.setSubject("Ping");  
						         message.setText("Hello, this is example of sending email  ");  
						         
						         Transport.send(message, user, password);
						         System.out.println("Message sent!");
						         JOptionPane.showOptionDialog(null, table.getValueAt(row, 0), "Email verstuurd", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,null,options,options[0]); // Get factuurID
								  return;
						    } catch (Exception e) {
						    	e.printStackTrace();
						    }
						}
//						JOptionPane.showOptionDialog(null, table.getValueAt(row, 0), "Email", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,null,options,options[0]); // Get factuurID
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
