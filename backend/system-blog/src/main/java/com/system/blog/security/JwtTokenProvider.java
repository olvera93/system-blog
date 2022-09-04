package com.system.blog.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.system.blog.crosscutting.errormanagement.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration}")
	private int jwtExpirationInMs;
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expiredDate = new Date(currentDate.getTime() + jwtExpirationInMs);
		
		String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expiredDate)
				.signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
		
		return token;
	}
	
	public String getUsernameJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	
	public boolean validToken(String token) {
		
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			throw new BlogAppException("Signing JWT is not valid");
		} catch (MalformedJwtException ex) {
			throw new BlogAppException("Token JWT is not valid");
		} catch (ExpiredJwtException ex) {
			throw new BlogAppException("Token JWT has expired");
		} catch (UnsupportedJwtException ex) {
			throw new BlogAppException("JWT is not compatible");
		} catch (IllegalArgumentException ex) {
			throw new BlogAppException("Claims JWT is empty");
		} 
	}
	
}
