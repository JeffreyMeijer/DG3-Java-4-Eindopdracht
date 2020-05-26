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
				ResultSet results = model.getBuyer(i+1);
				String firstname = results.getString(1);
				String lastname = results.getString(2);
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
						Object[] options = {"Ok"};
						JTable target = (JTable)me.getSource();
						int row = target.getSelectedRow();
						String s = (String)JOptionPane.showInputDialog(null,"Naar welke email wilt u dit factuur sturen?");
						if ((s != null) && (s.length() > 0)) {
							String to = s;
							String host = "localhost";
							final String from = "123974@student.drenthecollege.nl";
							
						    Properties properties = System.getProperties();
						    properties.put("mail.smtp.host", host); 
						    
						    Session session = Session.getDefaultInstance(properties);  
						    try {
						         MimeMessage message = new MimeMessage(session);  
						         message.setFrom(new InternetAddress(from));  
						         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
						         message.setSubject("Factuur");  
						         message.setText("Hallo, hierbij uw factuur van u recente aankoop");  
						         
						         Transport.send(message);
						         JOptionPane.showOptionDialog(null, "Factuur verstuurd naar " + to, "Email verstuurd", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,null,options,options[0]); // Get factuurID
								  return;
						    } catch (Exception e) {
						    	e.printStackTrace();
						    }
						}
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
