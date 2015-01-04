package com.bluesky.jct.rest;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.bluesky.jct.util.GsonMessageBodyHandler;

public class RestClient {
	

	private static final String REST_SERVICE_URL = "http://localhost:8080/JCT2015_RestAPI/profiles";

	private static Client client;

	static {
		client = ClientBuilder.newBuilder().register(GsonMessageBodyHandler.class).build();

	}
	
	public static String getAll() {

		Response response = client.target(REST_SERVICE_URL).path("/name").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
			return response.toString();
		}
	}


	

	public void close() {
		client.close();
	}

}