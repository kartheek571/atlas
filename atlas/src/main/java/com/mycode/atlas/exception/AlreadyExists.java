package com.mycode.atlas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class AlreadyExists  extends RuntimeException {
		public AlreadyExists(String msg)
		{
			super(msg);
		}
}
