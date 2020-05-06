package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Appointment {
	public Connection connect() {
		Connection con = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/appointmentdb", "root", "");

			// For testing purpose
			System.out.print("Successfully connected");

		} catch (Exception e) {

			System.out.print("Connection fail");
			e.printStackTrace();
			System.out.print(e);
		}

		return con;
	}

	public String viewAppointments() {

		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Creating html table
			output = "<table border='1'><tr><th>Appointment ID</th>" + "<th>patient Name</th><th>patient Id</th>"+ "<th>Doctor Name</th>"
					+ "<th>Doctor Mobile</th>" + "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from appointments";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String aId = rs.getString("aId");
				String pName = rs.getString("pName");
				String dId = rs.getString("pId");
				String dName = rs.getString("dName");
				String dMobile = rs.getString("dMobile");
				
				output += "<tr><td><input id='hidItemIDUpdate'" + "name='hidItemIDUpdate' type='hidden'" + "value='"
						+ aId + "'>" + aId + "</td>";
				// Add into the html table
				//output += "<tr><td>" + aId + "</td>";
				output += "<td>" + pName + "</td>";
				output += "<td>" + dId + "</td>";
				output += "<td>" + dName + "</td>";
				output += "<td>" + dMobile + "</td>";
				
				output += "<td><input name='btnUpdate' type='button'" + "value='Update'"
						+ "class='btnUpdate btn btn-secondary'></td>" + "<td><input name='btnRemove' type='button'"
						+ "value='Remove'" + "class='btnRemove btn btn-danger' data-itemid='" + aId + "'>"
						+ "</td></tr>";
			}

			con.close();
			// Completing the html table
			output += "</table>";

		} catch (Exception e) {
			output = "Error while reading the Appoinment Details.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String makeAppointments(String aId, String pName, String pId, String dName, String dMobile) {

		String output = "";
		try {

			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database";
			}

			try {
				// create a prepared statement
				String query = " INSERT INTO appointments (aId, pName, pId, dName, dMobile) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setString(1, aId);
				preparedStmt.setString(2, pName);
				preparedStmt.setString(3, pId);
				preparedStmt.setString(4, dName);
				preparedStmt.setString(5, dMobile);

				// execute the statement
				preparedStmt.execute();
				con.close();
				String newApp = viewAppointments();
				output = "{\"status\":\"success\", \"data\": \"" + newApp + "\"}";

			} catch (Exception e) {
				// TODO: handle exception
				output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
				System.err.println(e.getMessage());
			}

		}

		catch (Exception e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updateAppointments(String aId, String pName, String dId, String dName, String dMobile) {

		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE appointments SET pName=?,pId=?,dName=?,dMobile=? WHERE aId=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(5, aId);
			preparedStmt.setString(1, pName);
			preparedStmt.setString(2, dId);
			preparedStmt.setString(3, dName);
			preparedStmt.setString(4, dMobile);
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newApp = viewAppointments();
			output = "{\"status\":\"success\", \"data\": \"" + newApp + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while Updating.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteAppointments(String aId) {
		String output = "";
		try {

			Connection con = connect();
			if (con == null) {
				return "Error connection database for deleting.";
			}

			// create a prepared statement
			String query = "delete from appointments where aId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, aId);

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newApp = viewAppointments();
			output = "{\"status\":\"success\", \"data\": \"" + newApp + "\"}";

		} catch (Exception e) {

			output = "{\"status\":\"error\", \"data\":\"Error while Deleting the item.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}
}
