package com.team8.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.team8.controllers.UserCredentials;
import com.team8.controllers.UserDto;

@Component
public class KeycloakService {

	@Value("${keycloak.credentials.secret}")
	private String SECRETKEY;

	@Value("${keycloak.resource}")
	private String CLIENTID;

	@Value("${keycloak.auth-server-url}")
	private String AUTHURL;

	@Value("${keycloak.realm}")
	private String REALM;
	
	private String generateHashID(String username, int phnnumber, String email, String nic){
        MessageDigest messageDigest = null;
        String hash = "" + username + phnnumber + email + nic;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        messageDigest.update(hash.getBytes(), 0, hash.length());
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }
	
	private UsersResource getKeycloakUserResource() {

		Keycloak kc = KeycloakBuilder.builder().serverUrl(AUTHURL).realm("master").username("yasas").password("yasas")
				.clientId("admin-cli").resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
				.build();

		RealmResource realmResource = kc.realm(REALM);
		UsersResource userRessource = realmResource.users();

		return userRessource;
	}

	private RealmResource getRealmResource() {

		Keycloak kc = KeycloakBuilder.builder().serverUrl(AUTHURL).realm("master").username("yasas").password("yasas")
				.clientId("admin-cli").resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
				.build();

		RealmResource realmResource = kc.realm(REALM);

		return realmResource;

	}
	
	private String sendPost(List<NameValuePair> urlParameters) throws Exception {

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(AUTHURL + "/realms/" + REALM + "/protocol/openid-connect/token");

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();

	}
	
	public int createUserInKeyCloak(UserDto userDTO) {

		int statusId = 0;
		try {

			UsersResource userRessource = getKeycloakUserResource();

			UserRepresentation user = new UserRepresentation();
			user.setUsername(new KeycloakService().generateHashID(userDTO.getUsername(), userDTO.getPhonenumber(), userDTO.getEmail(), userDTO.getNic()));
			user.setEmail(userDTO.getEmail());
			user.setEnabled(true);

			// Create user
			Response result = userRessource.create(user);
			System.out.println("Keycloak create user response code>>>>" + result.getStatus());

			statusId = result.getStatus();

			if (statusId == 201) {

				String userId = result.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

				System.out.println("User created with userId:" + userId);

				// Define password credential
				CredentialRepresentation passwordCred = new CredentialRepresentation();
				passwordCred.setTemporary(false);
				passwordCred.setType(CredentialRepresentation.PASSWORD);
				passwordCred.setValue(userDTO.getPassword());

				// Set password credential
				userRessource.get(userId).resetPassword(passwordCred);

				// set role
				RealmResource realmResource = getRealmResource();
				RoleRepresentation savedRoleRepresentation = realmResource.roles().get("user").toRepresentation();
				realmResource.users().get(userId).roles().realmLevel().add(Arrays.asList(savedRoleRepresentation));

				System.out.println("Username==" + userDTO.getUsername() + " created in keycloak successfully");

			}

			else if (statusId == 409) {
				System.out.println("Username==" + userDTO.getUsername() + " already present in keycloak");

			} else {
				System.out.println("Username==" + userDTO.getUsername() + " could not be created in keycloak");

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return statusId;

	}
	
	public String getToken(UserCredentials userCredentials) {

		String responseToken = null;
		try {

			String username = userCredentials.getUsername();

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("grant_type", "password"));
			urlParameters.add(new BasicNameValuePair("client_id", CLIENTID));
			urlParameters.add(new BasicNameValuePair("username", username));
			urlParameters.add(new BasicNameValuePair("password", userCredentials.getPassword()));
			urlParameters.add(new BasicNameValuePair("client_secret", SECRETKEY));

			responseToken = sendPost(urlParameters);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseToken;

	}
	
	public String getByRefreshToken(String refreshToken) {

		String responseToken = null;
		try {

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
			urlParameters.add(new BasicNameValuePair("client_id", CLIENTID));
			urlParameters.add(new BasicNameValuePair("refresh_token", refreshToken));
			urlParameters.add(new BasicNameValuePair("client_secret", SECRETKEY));

			responseToken = sendPost(urlParameters);

		} catch (Exception e) {
			e.printStackTrace();

		}

		return responseToken;
	}
	
	public void logoutUser(String userId) {

		UsersResource userRessource = getKeycloakUserResource();

		userRessource.get(userId).logout();

	}

	public void resetPassword(String newPassword, String userId) {

		UsersResource userResource = getKeycloakUserResource();

		// Define password credential
		CredentialRepresentation passwordCred = new CredentialRepresentation();
		passwordCred.setTemporary(false);
		passwordCred.setType(CredentialRepresentation.PASSWORD);
		passwordCred.setValue(newPassword.toString().trim());

		// Set password credential
		userResource.get(userId).resetPassword(passwordCred);

	}
}
