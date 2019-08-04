package com.aca.iride.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.aca.iride.model.Ride;
import com.aca.iride.service.RideService;
import com.aca.iride.dao.AwsSnsSubscription;
import com.aca.iride.dao.IrideDao;
import com.aca.iride.model.EmailAddress;
import com.aca.iride.model.EmailMessage;
import com.aca.iride.model.Message;





@Path("/rides")
public class RideController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ride> getAllRides() {
		
		RideService service = new RideService();
		return service.getAllRides();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Ride addRide(Ride newRide){
		RideService service = new RideService();
		
		return service.addRide(newRide);
		
	}
	
	@DELETE
	@Path("/{rideId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ride> deleteByRideId(@PathParam("rideId") int rideId){
		RideService service = new RideService();
		return service.deleteByRideId(rideId);
	}
	
	@GET
	@Path("/{rideId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ride> getByRideId(@PathParam("rideId") int rideId){
		RideService service = new RideService();
		return service.getByRideId(rideId);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Ride updateRide(Ride updateRide){
		RideService service = new RideService();
		
		return service.updateRide(updateRide);
}
	
	@POST	
	@Path("/email")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response sendEmail(EmailMessage emailMessage) {		
		RideService service = new RideService();
		String result = service.sendEmail(emailMessage);
		
		Message message = new Message();
		message.setMessage(result);
		
		return Response.status(200).entity(message).build();				
	}
	
	@POST	
	@Path("/subscribe")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response subscribe(EmailAddress emailAddress) {		
		AwsSnsSubscription service = new AwsSnsSubscription();
		String result = service.subscribe(emailAddress);
		
		Message message = new Message();
		message.setMessage(result);
		
		return Response.status(200).entity(message).build();				
	}
}
