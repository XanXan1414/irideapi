package com.aca.iride.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.aca.iride.model.Ride;
import com.aca.iride.dao.AwsSnsPublish;
import com.aca.iride.dao.MariaDbUtil;


public class IrideDao {
	
	private static String sqlGetAllRides = " SELECT rideId, name, mapURL, imageURL FROM ride2";
	
	private static String sqlInsertRide = "INSERT INTO ride2 (name, mapURL,imageURL)" +
			 "VALUES(?,?,?)";
	
	private static String sqlDeleteRide = "DELETE FROM ride2 WHERE rideId = ? ";
	
	private static String sqlGetByRideId = "SELECT rideId, name, mapURL, imageURL FROM ride2 WHERE rideId = ?";
	
	private static String sqlUpdateByRideId = "UPDATE ride2 SET Name = ?, mapURL = ? imageURL = ? WHERE rideId = ?";
	
	
public List<Ride> getAllRides() {
		
		List<Ride> rides = new ArrayList<Ride>();
		
		ResultSet result = null;
		Statement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();

		try {			
			statement = conn.createStatement();			
			result = statement.executeQuery(sqlGetAllRides);
			
			while(result.next()) {
				rides.add(makeRide(result));
			}			
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			try {				
				result.close();
				statement.close();
				conn.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		
		return rides;
	}

private Ride makeRide(ResultSet result) throws SQLException{
	Ride ride = new Ride();
	ride.setRideId(result.getInt("rideId"));
	ride.setName(result.getString("name"));
	ride.setMapURL(result.getString("mapURL"));
	ride.setImageURL(result.getString("imageURL"));
	return ride;
	
}

public Ride addRide(Ride newRide) {
	int	rowCount = 0;
	PreparedStatement statement = null;
	
	Connection conn = MariaDbUtil.getConnection();

	try {				
		statement = conn.prepareStatement(sqlInsertRide);
		statement.setString(1, newRide.getName());
		statement.setString(2, newRide.getMapURL());
		statement.setString(3, newRide.getImageURL());
		
		rowCount = statement.executeUpdate();				
			
		newRide.setRideId(getLastRideId(conn));		
		
	} catch (SQLException e) {			
		e.printStackTrace();
	} finally {
		try {				
			statement.close();
			conn.close();
		} catch (SQLException e) {				
			e.printStackTrace();
		}
	}
	
	return newRide;
}

private Integer getLastRideId(Connection conn) throws SQLException {
	Integer rideId = 0;
	Statement statement = conn.createStatement();			
	ResultSet result = statement.executeQuery(" SELECT LAST_INSERT_ID() ");
	
	while(result.next()) {
		rideId = result.getInt("LAST_INSERT_ID()");				
	}
	return rideId;
}


public List<Ride> deleteByRideId(int rideId) {
	List<Ride> rides = this.getByRideId(rideId);
	
	int	rowCount = 0;
	PreparedStatement statement = null;
	
	Connection conn = MariaDbUtil.getConnection();

	try {				
		statement = conn.prepareStatement(sqlDeleteRide);
		statement.setInt(1,rideId);
		rowCount = statement.executeUpdate();				
			
	} catch (SQLException e) {			
		e.printStackTrace();
	} finally {
		try {				
			statement.close();
			conn.close();
		} catch (SQLException e) {				
			e.printStackTrace();
		}
	}
	
	
	return rides;
}

public List<Ride> getByRideId(int rideId) {
	List<Ride> rides = new ArrayList<Ride>();
	ResultSet result = null;
	PreparedStatement statement = null;
	
	Connection conn = MariaDbUtil.getConnection();

	try {				
		statement = conn.prepareStatement(sqlGetByRideId);
		statement.setInt(1,rideId);
		result = statement.executeQuery();
		
		while(result.next()) {
			rides.add(makeRide(result));
			}
					
	} catch (SQLException e) {			
		e.printStackTrace();
	} finally {
		try {				
			statement.close();
			conn.close();
		} catch (SQLException e) {				
			e.printStackTrace();
		}
	}
	
	return rides;
}

public Ride updateRide(Ride updateRide) {
	int	rowCount = 0;
	PreparedStatement statement = null;
	
	Connection conn = MariaDbUtil.getConnection();

	try {				
		statement = conn.prepareStatement(sqlUpdateByRideId);
		statement.setString(1, updateRide.getName());
		statement.setString(2, updateRide.getMapURL());
		statement.setInt(3, updateRide.getRideId());
		statement.setString(4, updateRide.getImageURL());
		
		rowCount = statement.executeUpdate();				
			
		System.out.println("rows updated: " + rowCount);		
		
	} catch (SQLException e) {			
		e.printStackTrace();
	} finally {
		try {				
			statement.close();
			conn.close();
		} catch (SQLException e) {				
			e.printStackTrace();
		}
	}
	
	return updateRide;
}


public void publish(String message, String subject) {
	AwsSnsPublish publish = new AwsSnsPublish();
	publish.publishMessage(message, subject);
	
}

}