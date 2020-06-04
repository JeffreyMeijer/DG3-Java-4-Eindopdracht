package com.dg3.main;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;

import com.dg3.database.*;

import com.dg3.main.models.Model;

class Tests {
	Model model = new Model();
	Connection con = DBConnection.createConnection();
	Statement statement;
	@Test
	void facturenCountTest() {
		int rows = 0;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT count(*) from facturen");
			while(rs.next()) {
				rows = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(rows, model.getFacturenCount());
	}
	@Test
	void productenCountTest() {
		int rows = 0;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT count(*) from producten");
			while(rs.next()) {
				rows = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(rows,model.getProductenCount());
	}
	@Test
	void factuurProductenTest() {
		JSONObject obj = null;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT producten FROM facturen where id = 1");
			while(rs.next()) {
				obj = (JSONObject)new JSONParser().parse(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(obj, model.getFactuurProducts(1));
	}
	double totaalprijs = 0;
	@Test
	void totalAmountTest() {
		double prijs = 0;
		try {
			JSONObject producten = model.getFactuurProducts(1);
			producten.keySet().forEach(keyStr -> {
				String value = String.valueOf(producten.get(keyStr));
				this.totaalprijs += (model.getProductPrijsByName(keyStr) * Integer.parseInt(value));
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		prijs = this.totaalprijs;
		this.totaalprijs = 0;
		assertEquals(0, this.totaalprijs);
		assertEquals(model.getTotaalPrijs(1), prijs);
	}
	@Test
	void mailingTest() {
		double totaalPrijs;
		String to = "123974@student.drenthecollege.nl";
		String host = "localhost";
		final String from = "123974@student.drenthecollege.nl";
		JSONObject factuurProducten = model.getFactuurProducts(1);
		ResultSet Koper = model.getBuyer(1);
	    Properties properties = System.getProperties();
	    properties.put("mail.smtp.host", host); 
	    Session session = Session.getDefaultInstance(properties);
	    try {
	    	String voornaam = Koper.getString(1);
	    	String achternaam = Koper.getString(2);
	    	String adres = Koper.getString(3);
	    	String postcode = Koper.getString(4);
	    	String plaats = Koper.getString(5);
	    	MimeMessage message = new MimeMessage(session);  
	    	message.setFrom(new InternetAddress(from));  
	    	message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
	    	message.setSubject("Factuur"); 
	    	StringBuilder producten = new StringBuilder();
	    	producten.append("U heeft het volgende gekocht:\n");
	    	totaalPrijs = model.getTotaalPrijs(1);
	    	factuurProducten.keySet().forEach(key -> {
	    		Object value = factuurProducten.get(key);
	    		BigDecimal productPrijs = new BigDecimal(model.getProductPrijsByName(key));
	    		producten.append(String.format("<tr><td>%s</td><td>%s</td><td>%.2f</td></tr>", key,value,productPrijs));
	    	});
	    	message.setContent(String.format
	    			("<h1>Hallo %s %s, hierbij uw factuur van u recente aankoop</h1>"
	    					+ "<p>Adres: %s</p>"
	    					+ "<p>%s %s</p>"
	    					+ "<table>"
	    					+ "<tr>"
	    					+ "<th>Product</th><th>Aantal</th><th>Prijs</th>"
	    					+ "</tr>%s"
	    					+ "</table>"
	    					+ "Totaal Prijs: %.2f", voornaam,achternaam,adres,postcode,plaats,producten,totaalPrijs
	    					), "text/html");
	    	Transport.send(message);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
}
