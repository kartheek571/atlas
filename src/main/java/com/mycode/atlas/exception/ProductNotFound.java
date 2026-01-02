package com.mycode.atlas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class ProductNotFound  extends RuntimeException {
	
	public ProductNotFound(String msg)
	{
		super(msg);
	}

}
