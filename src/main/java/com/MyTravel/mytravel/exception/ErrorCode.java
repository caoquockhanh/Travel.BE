package com.MyTravel.mytravel.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	// Default Spring MVC errors
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad Request"),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed"),
	NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "Not Acceptable"),
	UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type"),

	// Business errors
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
	ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access Denied"),
	REQUEST_TOO_LARGE(HttpStatus.BAD_REQUEST, "Request is too large"),

	// Application errors
	WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "Wrong password"),
	INVALID_PHONE(HttpStatus.BAD_REQUEST, "Phone is invalid"),

	PHONE_NOT_FOUND(HttpStatus.NOT_FOUND, "Phone not found"),
	TOUR_NOT_FOUND(HttpStatus.NOT_FOUND, "Tour not found"),
	BOOK_TOUR_NOT_FOUND(HttpStatus.NOT_FOUND, "Tour book not found"),

	USERNAME_ALREADY_EXIST(HttpStatus.CONFLICT, "Username is already exists"),
	PHONE_ALREADY_EXIST(HttpStatus.CONFLICT, "Phone is already exists"),
	EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT, "Email is already exists"),

	TOUR_ALREADY_EXIST(HttpStatus.CONFLICT, "Tour is already exists"),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),

	IMAGE_UPLOAD_FAILED(HttpStatus.CONFLICT, "Image upload failed"),

	CANNOT_UPDATE_IMAGE(HttpStatus.CONFLICT, "Cannot update images"),

	FAILED_TO_SEND_OTP(HttpStatus.NOT_FOUND, "Failed to send OTP"),

	EXPIRED_OTP(HttpStatus.BAD_REQUEST, "Expired OTP"),

	INVALID_OTP(HttpStatus.BAD_REQUEST, "Invalid OTP"),

	OTP_NOT_FOUND(HttpStatus.NOT_FOUND, "OTP not found"),
	;
	private final HttpStatus status;
	private final String message;
}
