package com.bluesky.jct.rest;


import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.bluesky.jct.model.Profile;
import com.bluesky.jct.util.GsonMessageBodyHandler;

public class RestClient {
	

	private static final String REST_SERVICE_URL = "http://localhost:8080/RestService/rest/profiles";

	private static Client client;

	
	static {
		client = ClientBuilder.newBuilder().register(GsonMessageBodyHandler.class).build();
	}
	
	
	//@SuppressWarnings("unchecked")
	public static List<Profile> findAll() {

		Response response = client.target(REST_SERVICE_URL).path("/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
		
			List<Profile> list = response.readEntity(new GenericType<List<Profile>>() {});
			
			return list;
		}
	}


	public void close() {
		client.close();
	}
}