package com.mycode.atlas.response;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data

public class ApiResponse {
	
	private String msg;
	private Object data;

}
