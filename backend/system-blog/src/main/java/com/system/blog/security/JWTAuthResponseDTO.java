package com.system.blog.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class JWTAuthResponseDTO {
	
	private String accessToken;
	
	private String tokenType = "Bearer";

	public JWTAuthResponseDTO(String accessToken) {
		super();
		this.accessToken = accessToken;
	}

	public JWTAuthResponseDTO(String accessToken, String tokenType) {
		super();
		this.accessToken = accessToken;
		this.tokenType = tokenType;
	}
	
	
	
	

}
