package com.bluesky.jct.rest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.bluesky.jct.model.*;
import com.bluesky.jct.util.ExceptionHandling;
import com.bluesky.jct.util.GsonMessageBodyHandler;


/**
 * REST client.
 * All interactions with DB need to go through this client class.
 * 
 * @author Dominik
 */
public class RestClient {
	
	private static final String REST_SERVICE_URL = "http://localhost:8080/RestService/rest";
	private static Client client;
	private static boolean successfulTransaction = false;
	private static String httpStatusCode;
	
	static {
		client = ClientBuilder.newBuilder().register(GsonMessageBodyHandler.class).build();
	}
	
	
	/**
	 * Checks whether connection to the REST Service is possible.
	 * 
	 * @return successfulTransaction (false if ok, true otherwise)
	 */
	public static boolean checkConnectionRestServer() {
		
		try {
			URL url = new URL("http://localhost:8080");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			if(con.getResponseCode() == 200) {
				successfulTransaction = false;
			} else {
				successfulTransaction = true;
			}
			con.disconnect();
		} catch (IOException e) {
			e.printStackTrace();			
		}
		return successfulTransaction;
	}

	
	/**
	 * Checks whether connection to the DB is possible.
	 * 
	 * @return successfulTransaction (false if ok, true otherwise)
	 */
	public static boolean checkConnectionDB() {
		
		try {
			URL url = new URL("http://localhost:8080/RestService/rest/domain/list");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			if(con.getResponseCode() == 200) {
				successfulTransaction = false;
			} else {
				successfulTransaction = true;
			}
			con.disconnect();
		} catch (IOException e) {
			e.printStackTrace();			
		}
		return successfulTransaction;
	}
	
	
	/**
	 * Provides the details of a single Profile from the view table
	 * (foreign keys will be replaced with the respective names).
	 * 
	 * @param profileId
	 * @return profileView
	 */
	public static ProfileView findProfileView(int profileId) {

		Response response = client.target(REST_SERVICE_URL).path("/profileView/" + profileId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			
			String headerText = "Find Profile";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
			
			ProfileView profileView = response.readEntity(new GenericType<ProfileView>() {});
			return profileView;
		}
	}
	
	
	/**
	 * Provides the details of all Profiles available from the view table
	 * (foreign keys will be replaced with the respective names).
	 * 
	 * @return profileViewList
	 */
	public static List<ProfileView> findAllProfiles() {

		Response response = client.target(REST_SERVICE_URL).path("/profileView/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {

			String headerText = "Find all Profiles";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
		
			List<ProfileView> profileViewList = response.readEntity(new GenericType<List<ProfileView>>() {});
			return profileViewList;
		}
	}	
	

	/**
	 * Provides all details of a single Profile
	 * (foreign information will be shown with their id only).
	 * 
	 * @param profileId
	 * @return profile
	 */
	public static Profile findProfile(int profileId) {

		Response response = client.target(REST_SERVICE_URL).path("/profile/" + profileId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			
			String headerText = "Find Profile";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
			
			Profile profile = response.readEntity(new GenericType<Profile>() {});
			return profile;
		}
	}
	
	
	/**
	 * Provides details of all Profiles available
	 * (foreign information will be shown with their id only).
	 * 
	 * @return profileList
	 */
	public static List<Profile> findAllProfile() {

		Response response = client.target(REST_SERVICE_URL).path("/profile/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			
			String headerText = "Find all Profiles";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();

		} else {
		
			List<Profile> profileList = response.readEntity(new GenericType<List<Profile>>() {});
			return profileList;
		}
	}
	
	
	/**
	 * Through this method, a new Profile can be created.
	 * 
	 * @param environmentId
	 * @param hostId
	 * @param jbarId
	 * @param jiraId
	 * @param prefixId
	 * @param domainId
	 * @param profileDescription
	 * @param profileDnsName
	 * @param profileComponent
	 * @param jvmId
	 * @param profileStatus
	 * @param createdBy
	 * @param rpmGenerationDate
	 * @param packageSentDate
	 * @param version
	 * @return successfulTransaction (false if ok, true otherwise)
	 */
	public static boolean createProfile(int environmentId, int hostId, int jbarId, int jiraId, int prefixId, int domainId, 
			String profileDescription, String profileDnsName, String profileComponent, int jvmId, boolean profileStatus, 
			String createdBy, Date rpmGenerationDate, Date packageSentDate, Integer version) {
		
		Profile profile = new Profile(environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, profileDnsName, 
				profileComponent, jvmId, profileStatus, createdBy, rpmGenerationDate, packageSentDate, version);
		
		Response response = client.target(REST_SERVICE_URL).path("/profile").request().post(Entity.json(profile));
		
		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			
			String headerText = "Create Profile";
			String contentText = "The profile could not be created!\n"
					           + "Please check whether a profile with the same parameters already exists.";
			ExceptionHandling.handleError(headerText, contentText);
			successfulTransaction = true;
		} else {
			successfulTransaction = false;
		}
		// response must either be read or ignored (closed)
		response.close();
		return successfulTransaction;
	}
	
	
	/**
	 * Through this method, an existing Profile can be modified.
	 * 
	 * @param profileId
	 * @param environmentId
	 * @param hostId
	 * @param jbarId
	 * @param jiraId
	 * @param prefixId
	 * @param domainId
	 * @param profileDescription
	 * @param profileDnsName
	 * @param profileComponent
	 * @param jvmId
	 * @param profileStatus
	 * @param createdBy
	 * @param rpmGenerationDate
	 * @param packageSentDate
	 * @param version
	 * @return successfulTransaction (false if ok, true otherwise)
	 */
	public static boolean editProfile(int profileId, int environmentId, int hostId, int jbarId, int jiraId, int prefixId, int domainId, 
			String profileDescription, String profileDnsName, String profileComponent, int jvmId, boolean profileStatus, 
			String createdBy, Date rpmGenerationDate, Date packageSentDate, Integer version) {
		
		Profile profile = new Profile(environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, profileDnsName, 
				profileComponent, jvmId, profileStatus, createdBy, rpmGenerationDate, packageSentDate, version);
		profile.setProfileId(profileId);
	
		Response response = client.target(REST_SERVICE_URL).path("/profile").request().put(Entity.json(profile));

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			
			String headerText = "Edit Profile";
			String contentText = "The profile could not be edited!\n"
					           + "Please contact the System Administrator.";
			ExceptionHandling.handleError(headerText, contentText);
			successfulTransaction = true;
		} else {
			successfulTransaction = false;
		}
		// response must either be read or ignored (closed)
		response.close();
		return successfulTransaction;
	}
	
	
	/**
	 * This method allows to delete a specific Profile
	 * 
	 * @param profileId
	 * @return successfulTransaction (false if ok, true otherwise)
	 */
	public static boolean deleteProfile(int profileId) {

		Response response = client.target(REST_SERVICE_URL).path("/profile/" + profileId).request().delete();
	
		String headerText = "Delete Profile";
		
		if (response.getStatus() == Status.GONE.getStatusCode()) {
			httpStatusCode = Integer.toString(response.getStatus());
			String contentText = "Profile has been successfully deleted!"
    						   + "\nHTTP Status Code: " + httpStatusCode;
			ExceptionHandling.handleInformation(headerText, contentText);
			
			successfulTransaction = false;
			
		} else {
			httpStatusCode = Integer.toString(response.getStatus());
			String contentText = "The profile could not be deleted!"
							   + "Please contact system administrator." + "(HTTP Status Code: " + httpStatusCode + ")";
			ExceptionHandling.handleError(headerText, contentText);			

			successfulTransaction = true;
		}
		// response must either be read or ignored (closed)
		response.close();
		return successfulTransaction;
	}

	
	/**
	 * Provides all details of a single Domain
	 * 
	 * @param domainId
	 * @return domain
	 */
	public static Domain findDomain(int domainId) {

		Response response = client.target(REST_SERVICE_URL).path("/domain/" + domainId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			
			String headerText = "Find Domain";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
			
			Domain domain = response.readEntity(new GenericType<Domain>() {});
			return domain;
		}
	}
	
	
	/**
	 * Provides all details of all available Domains
	 * 
	 * @return domainList
	 */
	public static List<Domain> findAllDomain() {

		Response response = client.target(REST_SERVICE_URL).path("/domain/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {

			String headerText = "Find all Domains";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
		
			List<Domain> domainList = response.readEntity(new GenericType<List<Domain>>() {});
			return domainList;
		}
	}
	
	
	/**
	 * Provides all details of a single Environment
	 * 
	 * @param environmentId
	 * @return environment
	 */
	public static Environment findEnvironment(int environmentId) {

		Response response = client.target(REST_SERVICE_URL).path("/environment/" + environmentId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			
			String headerText = "Find Environment";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
			
			Environment environment = response.readEntity(new GenericType<Environment>() {});
			return environment;
		}
	}
	
	
	/**
	 * Provides all details of all available Environments
	 * 
	 * @return environmentList
	 */
	public static List<Environment> findAllEnvironment() {

		Response response = client.target(REST_SERVICE_URL).path("/environment/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			
			String headerText = "Find all Environments";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
		
			List<Environment> environmentList = response.readEntity(new GenericType<List<Environment>>() {});
			return environmentList;
		}
	}
	
	
	/**
	 * Provides all details of a single Jbar
	 * 
	 * @param jbarId
	 * @return jbar
	 */
	public static Jbar findJbar(int jbarId) {

		Response response = client.target(REST_SERVICE_URL).path("/jbar/" + jbarId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {

			String headerText = "Find Jbar";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
			
			Jbar jbar = response.readEntity(new GenericType<Jbar>() {});
			return jbar;
		}
	}
	
	
	/**
	 * Provides all details of all available Jbars
	 * 
	 * @return jbarList
	 */
	public static List<Jbar> findAllJbar() {

		Response response = client.target(REST_SERVICE_URL).path("/jbar/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			
			String headerText = "Find all Jbars";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
		
			List<Jbar> jbarList = response.readEntity(new GenericType<List<Jbar>>() {});
			return jbarList;
		}
	}
	
	
	/**
	 * Provides all details of a single Prefix
	 * 
	 * @param prefixId
	 * @return prefix
	 */
	public static Prefix findPrefix(int prefixId) {

		Response response = client.target(REST_SERVICE_URL).path("/prefix/" + prefixId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {

			String headerText = "Find Prefix";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
			
			Prefix prefix = response.readEntity(new GenericType<Prefix>() {});
			return prefix;
		}
	}
	
	
	/**
	 * Provides all details of all available Prefixs
	 * 
	 * @return prefixList
	 */
	public static List<Prefix> findAllPrefix() {

		Response response = client.target(REST_SERVICE_URL).path("/prefix/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			
			String headerText = "Find all Prefixs";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
		
			List<Prefix> prefixList = response.readEntity(new GenericType<List<Prefix>>() {});
			return prefixList;
		}
	}
		
	
	/**
	 * Provides all details of a single Host
	 * 
	 * @param hostId
	 * @return host
	 */
	public static Host findHost(int hostId) {

		Response response = client.target(REST_SERVICE_URL).path("/host/" + hostId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			
			String headerText = "Find Host";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
			
			Host host = response.readEntity(new GenericType<Host>() {});
			return host;
		}
	}
	
	
	/**
	 * Provides all details of all available Hosts
	 * 
	 * @return hostList
	 */
	public static List<Host> findAllHost() {

		Response response = client.target(REST_SERVICE_URL).path("/host/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {

			String headerText = "Find all Hosts";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
		
			List<Host> hostList = response.readEntity(new GenericType<List<Host>>() {});
			return hostList;
		}
	}
	
	
	/**
	 * Provides all details of a single Jira
	 * 
	 * @param jiraId
	 * @return jira
	 */
	public static Jira findJira(int jiraId) {

		Response response = client.target(REST_SERVICE_URL).path("/jira/" + jiraId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {

			String headerText = "Find Jira";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
			
			Jira jira = response.readEntity(new GenericType<Jira>() {});
			return jira;
		}
	}
	
	
	/**
	 * Provides all details of all available Jiras
	 * 
	 * @return jiraList
	 */
	public static List<Jira> findAllJira() {

		Response response = client.target(REST_SERVICE_URL).path("/jira/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			
			String headerText = "Find all Hosts";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
		
			List<Jira> jiraList = response.readEntity(new GenericType<List<Jira>>() {});
			return jiraList;
		}
	}
	
	
	/**
	 * Provides all details of a single JvmArgument
	 * 
	 * @param jvmId
	 * @return jvmArgument
	 */
	public static JvmArgument findJvmArgument(int jvmId) {

		Response response = client.target(REST_SERVICE_URL).path("/jvm/" + jvmId).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {

			String headerText = "Find JvmArgument";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
			
			JvmArgument jvmArgument = response.readEntity(new GenericType<JvmArgument>() {});
			return jvmArgument;
		}
	}
	
	
	/**
	 * Provides all details of all available JvmArguments
	 * 
	 * @return jvmList
	 */
	public static List<JvmArgument> findAllJvmArgument() {

		Response response = client.target(REST_SERVICE_URL).path("/jvm/list").request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			
			String headerText = "Find all JvmArguments";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
			response.close();
			throw new IllegalStateException();
			
		} else {
		
			List<JvmArgument> jvmList = response.readEntity(new GenericType<List<JvmArgument>>() {});
			return jvmList;
		}
	}
	
	
	/**
	 * Creates a new Jvm Argument
	 * 
	 * @param jvmArgumentText
	 * @return successfulTransaction (false if ok, true otherwise)
	 */
	public static boolean createJvmArgument(String jvmArgumentText) {
		
		JvmArgument jvmArgument = new JvmArgument(jvmArgumentText);
		Response response = client.target(REST_SERVICE_URL).path("/jvm").request().post(Entity.json(jvmArgument));
		
		if (response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			
			String headerText = "Create Profile";
			String contentText = "The profile could not be created!\n"
					           + "Please check whether a profile with the same parameters already exists.";
			ExceptionHandling.handleError(headerText, contentText);
			successfulTransaction = true;
		} else {
			successfulTransaction = false;
		}
		// response must either be read or ignored (closed)
		response.close();
		return successfulTransaction;
	}
	
		
	/**
	 * Returns transaction status as boolean.
	 * 
	 * @return successfulTransaction
	 */
	public static boolean isSuccessful() {
		return successfulTransaction;
	}

	
	/**
	 * Returns the HTTP Status Code as String.
	 * 
	 * @return httpStatusCode
	 */
	public static String getHttpStatusCode() {
		return httpStatusCode;
	}
	
	
	/**
	 * Closes the client connection 
	 */
	public static void close() {
		client.close();
	}
	
}