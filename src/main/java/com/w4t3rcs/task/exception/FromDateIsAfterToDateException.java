package com.w4t3rcs.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FromDateIsAfterToDateException extends RuntimeException {
}
