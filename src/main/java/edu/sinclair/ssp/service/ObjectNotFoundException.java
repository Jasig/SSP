package edu.sinclair.ssp.service;

import java.util.UUID;

public class ObjectNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	private UUID objectId;
	private String name;
	
	public ObjectNotFoundException(UUID objectId, String name){
		super(message(objectId, name));
		this.objectId = objectId;
		this.name = name;
	}
	
	private static String message(UUID objectId, String name){
		return "Unable to access " + name + " with id " + objectId.toString();
	}

	public UUID getObjectId() {
		return objectId;
	}
	
	public String getName() {
		return name;
	}
}
