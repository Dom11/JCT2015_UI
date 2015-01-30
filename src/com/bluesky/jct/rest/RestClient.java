package com.bluesky.jct.rest;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.bluesky.jct.model.*;
import com.bluesky.jct.util.GsonMessageBodyHandler;


public class RestClient {
	
	private static final String REST_SERVICE_URL = "http://localhost:8080/RestService/rest";
	private static Client client;
	private static boolean successfulTransaction = false;
	
	static {
		client = ClientBuilder.newBuilder().register(GsonMessageBodyHandler.class).build();
	}
	

	public static Profile findProfile(int profileId) {

		Response response = client.target(REST_SERVICE_URL).path("/profiles/" + profileId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
			
			Profile profile = response.readEntity(new GenericType<Profile>() {});
			
			return profile;
		}
	}
	
	
	public static List<Profile> findAll() {

		Response response = client.target(REST_SERVICE_URL).path("/profiles/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
		
			List<Profile> list = response.readEntity(new GenericType<List<Profile>>() {});
			
			return list;
		}
	}
	
	
	public static List<ProfileView> findAllProfiles() {

		Response response = client.target(REST_SERVICE_URL).path("/profileview/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
		
			List<ProfileView> profileViewList = response.readEntity(new GenericType<List<ProfileView>>() {});
			
			return profileViewList;
		}
	}
	
	
	public static List<Environment> findAllEnvironment() {

		Response response = client.target(REST_SERVICE_URL).path("/environment/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
		
			List<Environment> environmentList = response.readEntity(new GenericType<List<Environment>>() {});
			
			return environmentList;
		}
	}
	
	
	public static List<Jbar> findAllJbar() {

		Response response = client.target(REST_SERVICE_URL).path("/jbar/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
		
			List<Jbar> jbarList = response.readEntity(new GenericType<List<Jbar>>() {});
			
			return jbarList;
		}
	}
	
	
	public static void deleteProfile(int profileId) {

		Response response = client.target(REST_SERVICE_URL).path("/profiles/" + profileId).request().delete();
		
		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
			successfulTransaction = true ;
		
			System.out.println("Id " + profileId + " deleted");
		}
	}

	
	/**
	 * Returns true if the Transaction was successful, false otherwise.
	 * 
	 * @return
	 */
	public static boolean isSuccessful() {
		return successfulTransaction;
	}
	
	
	public static void close() {
		client.close();
	}
}