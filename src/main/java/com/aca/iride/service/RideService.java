package com.aca.iride.service;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.aca.iride.dao.IrideDao;
import com.aca.iride.model.Ride;
import com.aca.iride.model.EmailAddress;
import com.aca.iride.model.EmailMessage;
import com.aca.iride.dao.AwsSnsPublish;
import com.aca.iride.dao.AwsSnsSubscription;



public class RideService {
	
	public List<Ride> getAllRides() {
		IrideDao dao = new IrideDao();
		return dao.getAllRides();
	}

	

	public Ride addRide(Ride newRide) {
		//validateGenre(newMovie.getGenre());
				//validateReleaseYear(newMovie.getReleaseYear());
				//validateTitle(newMovie.getTitle());
				IrideDao dao = new IrideDao();
				Ride ride = dao.addRide(newRide);
				//sendNewMovieNotification(movie);
				return ride;
	}



	public List<Ride> deleteByRideId(int rideId) {
		
		IrideDao dao = new IrideDao();
		/*
		if(dao.getByKey(key).size() != 1){
			
			Error error = new Error (101,"invalid delete by key request, movie "+ key +" does not exist");
			
			Response response = Response.status(400)
					.entity(error)
					.build();
			throw new WebApplicationException(response);
		}
		*/
		
		return dao.deleteByRideId(rideId);
	}
	
	public List<Ride> getByRideId(int rideId) {
		IrideDao dao = new IrideDao();
		return dao.getByRideId(rideId);
	}



	public Ride updateRide(Ride updateRide) {
		IrideDao dao = new IrideDao();
		return dao.updateRide(updateRide);
	}
	
	public String sendEmail(EmailMessage emailMessage) {
		AwsSnsPublish service = new AwsSnsPublish();
		String messageId = service.sendEmail(emailMessage);		
		return messageId;
	}



	public String subscribe(EmailAddress emailAddress) {
		AwsSnsSubscription service = new AwsSnsSubscription();
		
		return service.subscribe(emailAddress);
	}
}
