package com.afour.hackthon.wiki.commons;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.afour.hackthon.wiki.exceptions.WikiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

@Component
public class TokenUtils {

	@Value("${jwt.secret}")
    private String secret;
	
	public Map<String, String> parseToken(String token) {
		try{
			Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		
		Map<String,String> map = new HashMap<>();
		map.put("username", body.getSubject());
		map.put("id", String.valueOf(body.get("id")));
		map.put("email", String.valueOf(body.get("email")));
		return map;
		} catch (JwtException jwtex) {
			throw new WikiException(403, "Error occurred while parsing JWT: " + jwtex.getMessage(), jwtex);
		}
	}
	
	public String generateToken(Map<String, String> map) {
		Claims claims = Jwts.claims().setSubject(map.get("username"));
		claims.put("id", map.get("userId"));
		claims.put("email", map.get("email"));
		
		String base64EncodedKey = TextCodec.BASE64.encode(secret);
		
		return Jwts.builder().setIssuer("https://afourtech.com/").setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, base64EncodedKey).compact();

	}
}
