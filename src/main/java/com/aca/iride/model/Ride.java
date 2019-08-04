package com.aca.iride.model;



public class Ride {
	
	private int rideId;
	private String name;
	private String mapURL;
	private String imageURL;
	
	public Ride(int rideId, String name, String mapURL){
		this.rideId = rideId;
		this.name = name;
		this.mapURL = mapURL;
	}
	
	public Ride(){};

	public int getRideId() {
		return rideId;
	}


	public String getName() {
		return name;
	}


	public String getMapURL() {
		return mapURL;
	}

	public void setRideId(int rideId) {
		this.rideId = rideId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMapURL(String mapURL) {
		this.mapURL = mapURL;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	
}
	
