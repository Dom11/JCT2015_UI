package com.bluesky.jct.rest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
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
	private static String httpStatusCode;
	
	static {
		client = ClientBuilder.newBuilder().register(GsonMessageBodyHandler.class).build();
	}
	
	
	public static boolean checkConnectionRestServer() {
		
		try {
			URL url = new URL("http://localhost:8080");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			if(con.getResponseCode() == 200) {
				successfulTransaction = true;
			} else {
				successfulTransaction = false;
			}
			
			con.disconnect();
		} catch (IOException e) {
			e.printStackTrace();			
		}
		return successfulTransaction;
	}

	
	public static boolean checkConnectionDB() {
		
		try {
			URL url = new URL("http://localhost:8080/RestService/rest/domain/list");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			if(con.getResponseCode() == 200) {
				successfulTransaction = true;
			} else {
				successfulTransaction = false;
			}
			
			con.disconnect();
		} catch (IOException e) {
			e.printStackTrace();			
		}
		return successfulTransaction;
	}
	

	public static Profile findProfile(int profileId) {

		Response response = client.target(REST_SERVICE_URL).path("/profile/" + profileId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
			
			Profile profile = response.readEntity(new GenericType<Profile>() {});
			
			return profile;
		}
	}
	
/**	
	public static List<Profile> findAll() {

		Response response = client.target(REST_SERVICE_URL).path("/profile/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
		
			List<Profile> list = response.readEntity(new GenericType<List<Profile>>() {});
			
			return list;
		}
	}
*/	
	
	public static ProfileView findProfileView(int profileId) {

		Response response = client.target(REST_SERVICE_URL).path("/profileView/" + profileId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
			
			ProfileView profileView = response.readEntity(new GenericType<ProfileView>() {});
			
			return profileView;
		}
	}
	
	
	public static List<ProfileView> findAllProfiles() {

		Response response = client.target(REST_SERVICE_URL).path("/profileView/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
		
			List<ProfileView> profileViewList = response.readEntity(new GenericType<List<ProfileView>>() {});
			
			return profileViewList;
		}
	}
	
	
	public static Domain findDomain(int domainId) {

		Response response = client.target(REST_SERVICE_URL).path("/domain/" + domainId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
			
			Domain domain = response.readEntity(new GenericType<Domain>() {});
			
			return domain;
		}
	}
	
	
	public static List<Domain> findAllDomain() {

		Response response = client.target(REST_SERVICE_URL).path("/domain/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
		
			List<Domain> domainList = response.readEntity(new GenericType<List<Domain>>() {});
			
			return domainList;
		}
	}
	
	
	public static Environment findEnvironment(int environmentId) {

		Response response = client.target(REST_SERVICE_URL).path("/environment/" + environmentId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
			
			Environment environment = response.readEntity(new GenericType<Environment>() {});
			
			return environment;
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
	
	
	public static Jbar findJbar(int jbarId) {

		Response response = client.target(REST_SERVICE_URL).path("/jbar/" + jbarId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
			
			Jbar jbar = response.readEntity(new GenericType<Jbar>() {});
			
			return jbar;
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
	
	
	public static Prefix findPrefix(int prefixId) {

		Response response = client.target(REST_SERVICE_URL).path("/prefix/" + prefixId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
			
			Prefix prefix = response.readEntity(new GenericType<Prefix>() {});
			
			return prefix;
		}
	}
	
	
	public static List<Prefix> findAllPrefix() {

		Response response = client.target(REST_SERVICE_URL).path("/prefix/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
		
			List<Prefix> prefixList = response.readEntity(new GenericType<List<Prefix>>() {});
			
			return prefixList;
		}
	}
	
	
	public static <E> List<E> findAll(Class<? extends E> clazz) {

		Response response = client.target(REST_SERVICE_URL).path("/" + clazz + "/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
		
			List<E> list = response.readEntity(new GenericType<List<E>>() {});
			
			return list;
		}
	}
	
	
	
	
	
	
	public static Host findHost(int hostId) {

		Response response = client.target(REST_SERVICE_URL).path("/host/" + hostId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
			
			Host host = response.readEntity(new GenericType<Host>() {});
			
			return host;
		}
	}
	
	
	public static List<Host> findAllHost() {

		Response response = client.target(REST_SERVICE_URL).path("/host/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
		
			List<Host> hostList = response.readEntity(new GenericType<List<Host>>() {});
			
			return hostList;
		}
	}
	
	
	public static Jira findJira(int jiraId) {

		Response response = client.target(REST_SERVICE_URL).path("/jira/" + jiraId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
			
			Jira jira = response.readEntity(new GenericType<Jira>() {});
			
			return jira;
		}
	}
	
	
	public static List<Jira> findAllJira() {

		Response response = client.target(REST_SERVICE_URL).path("/jira/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
		
			List<Jira> jiraList = response.readEntity(new GenericType<List<Jira>>() {});
			
			return jiraList;
		}
	}
	

	public static Domain createDomain(String domainName) {
		
		Domain domain = new Domain(domainName);
		Response response = client.target(REST_SERVICE_URL).path("/domain").request().post(Entity.json(domain));
		
		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		}
		// response must either be read or ignored (closed)
		response.close();
		
		return domain;
	}
	
	
	public static Profile createProfile(int environmentId, int hostId, int jbarId, int jiraId, int prefixId, int domainId, String profileDescription, String profileDnsName, String profileComponentName, Integer version) {
		
		Profile profile = new Profile(environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, profileDnsName, profileComponentName, version);
		Response response = client.target(REST_SERVICE_URL).path("/profile").request().post(Entity.json(profile));
		
		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		}
		// response must either be read or ignored (closed)
		response.close();
		
		return profile;
	}
	
	
	public static Profile editProfile(int profileId, int environmentId, int hostId, int jbarId, int jiraId, int prefixId, int domainId, String profileDescription, String profileDnsName, String profileComponentName, Integer version) {
		
		Profile profile = new Profile(environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, profileDnsName, profileComponentName, version);
		profile.setProfileId(profileId);	
	
		Response response = client.target(REST_SERVICE_URL).path("/profile").request().put(Entity.json(profile));

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new IllegalStateException();
		} else {
			// response must either be read or ignored (closed)
			response.close();
			
			return profile;
		}
	}

	
	public static boolean deleteProfile(int profileId) {

		Response response = client.target(REST_SERVICE_URL).path("/profile/" + profileId).request().delete();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			httpStatusCode = Integer.toString(response.getStatus());
			throw new IllegalStateException();
		} else {
			if (response.getStatus() == Status.OK.getStatusCode()) {
				client.target(REST_SERVICE_URL).path("/profiles/" + profileId).request().delete();
				httpStatusCode = Integer.toString(response.getStatus());
				successfulTransaction = true;
				
				return true;
			} else {
				httpStatusCode = Integer.toString(response.getStatus());
			}
		}
		// response must either be read or ignored (closed)
		response.close();
		
		return true;
	}
	
	
	/**
	 * Returns true if the Transaction was successful, false otherwise.
	 * 
	 * @return successfulTransaction
	 */
	public static boolean isSuccessful() {
		return successfulTransaction;
	}

	
	/**
	 * Returns the HTTP Status Code.
	 * 
	 * @return httpStatusCode
	 */
	public static String getHttpStatusCode() {
		return httpStatusCode;
	}
	
	
	public static void close() {
		client.close();
	}
}