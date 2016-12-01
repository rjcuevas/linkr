package com.linkr.authentication;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.linkr.url.dto.UrlDTO;
import com.linkr.user.dto.UserDTO;

public class JWTAuthentication {
	public final String secret = "linkr";				//JWT secret
	public final String issuer = "https://linkr.com/";	//additional info for JWT hashing
	
	
	/**
	 * Retrieve/Set access token for User details
	 * 
	 * @param userDTO
	 * @return token
	 */
	public String setToken(UserDTO userDTO){
		JWTSigner signer = new JWTSigner(secret);
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("issuer",issuer);
		claims.put("userID",userDTO.getUserID());
		claims.put("username",userDTO.getUsername());
		String token = signer.sign(claims);
		return token;
	}

	/**
	 * Retrieve/Set access token for URL details
	 * 
	 * @param urlDTO
	 * @return token
	 */
	public String setToken(UrlDTO urlDTO){
		JWTSigner signer = new JWTSigner(secret);
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("issuer",issuer);
		claims.put("userID",urlDTO.getUserID());
		claims.put("urlID",urlDTO.getUrlID());
		claims.put("username",urlDTO.getUsername());
		claims.put("urlAddress",urlDTO.getUrlAddress());
		claims.put("ispublic",urlDTO.getIsPublicURL());
		String token = signer.sign(claims);
		return token;
	}
	
	/**
	 * Retrieve Decoded value of JWT (Payloader)
	 * 
	 * @param jwt
	 * @return claims
	 */
	public Map<String,Object> getVerifiedToken(String jwt){
		Map<String,Object> claims = new HashMap<String, Object>();
		try {
		    JWTVerifier jwtVerifier = new JWTVerifier(secret);
			claims= jwtVerifier.verify(jwt);
		} catch (JWTVerifyException | InvalidKeyException | NoSuchAlgorithmException
				| IllegalStateException | SignatureException | IOException e) {
		    // Invalid Token
		}
		return claims;
	}

	/** 
	 * For testing purposes 
	 * **/ 
	/*public static void main(String[] args) {
		JWTAuthentication jwt = new JWTAuthentication();
		UserDTO userDTO = new UserDTO();
		UrlDTO urlDTO = new UrlDTO();
		userDTO.setUsername("rcuevas");
		urlDTO.setUrlAddress("http://google.com");
		urlDTO.setIsPublicURL(false);
		System.out.println("first : "+jwt.setToken(userDTO));
		System.out.println("firstVerify : "+jwt.getVerifiedToken(jwt.setToken(userDTO)));
		System.out.println("second : "+jwt.setToken(userDTO, urlDTO));
		System.out.println("secondVerify : "+jwt.getVerifiedToken(jwt.setToken(userDTO, urlDTO)));
	}*/
}
