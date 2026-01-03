package com.mycode.atlas.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginRequest {
	
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	
	

}
