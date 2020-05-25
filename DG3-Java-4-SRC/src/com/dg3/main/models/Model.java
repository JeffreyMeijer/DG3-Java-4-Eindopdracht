package com.dg3.main.models;
import java.sql.*;
import com.dg3.database.DBConnection;
public class Model {
	Connection con = DBConnection.createConnection();
	private double totaalprijs;
	public boolean isClosed() {
		try {
			return con.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	public JSONObject getFactuurProducts(int factuurID) {
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT producten FROM facturen where id = " + factuurID);
			while(rs.next()) {
				JSONObject obj = (JSONObject)new JSONParser().parse(rs.getString(1));
				return obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public Timestamp getFactuurDatum(int factuurID) {
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT timestamp FROM facturen where id = " + factuurID);
			while(rs.next()) {
				return rs.getTimestamp(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public int getFacturenCount() {
		Statement statement;
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
		return rows;
	}
	
	public int getProductenCount() {
		Statement statement;
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
		return rows;
	}
	
	public int getAccountID(int factuurID) {
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT account_id from facturen where id = " + factuurID);
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public ResultSet getBuyer(int factuurID) {
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT voornaam, achternaam, straat, postcode, plaats, land from facturen where id = " + factuurID);
			while(rs.next()) {
				return rs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet getProducten() {
		Statement statement;
		ResultSet rs = null;
		try {
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT * from producten");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public String getProductName(int productID) {
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT product FROM producten WHERE id = " + productID);
			while(rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "null";
	}
	public double getProductPrijs(int productID) {
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT prijs FROM producten WHERE id = " + productID);
			while(rs.next()) {
				return rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public double getProductPrijsByName(Object keyStr) {
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT prijs FROM producten WHERE product = '" + keyStr + "'");
			while(rs.next()) {
				return rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public double getTotaalPrijs(int factuurID) {
		double prijs = 0;
		try {
			JSONObject producten = getFactuurProducts(factuurID);
			producten.keySet().forEach(keyStr -> {
				String value = String.valueOf(producten.get(keyStr));
				this.totaalprijs += (getProductPrijsByName(keyStr) * Integer.parseInt(value));
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		prijs = this.totaalprijs;
		this.totaalprijs = 0;
		return prijs;
	}
	public String getFactuurStatus(int factuurID) {
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT status FROM facturen WHERE id = " + factuurID);
			while(rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Onbekend";
	}
}
