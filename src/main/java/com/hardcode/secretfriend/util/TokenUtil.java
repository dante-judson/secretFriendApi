package com.hardcode.secretfriend.util;

import org.springframework.security.jwt.JwtHelper;

public class TokenUtil {

	public static String getUsernameFromHeader(String header) {
		String jwt = header.substring(header.indexOf(" ")+1);
		String token = JwtHelper.decode(jwt).toString();
		String aux = token.substring(token.indexOf("\"user_name\":")+13);
		String username = aux.substring(0,aux.indexOf("\""));
		return username;
	}
	
}
