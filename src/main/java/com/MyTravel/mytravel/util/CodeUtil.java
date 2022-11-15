package com.MyTravel.mytravel.util;

import com.MyTravel.mytravel.model.ERole;
import com.MyTravel.mytravel.model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;

import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class CodeUtil {
	public static @Nullable String getBindExceptionMessage(
			BindException ex, String defaultMessage) {
		if (!ex.hasErrors() || ex.getAllErrors().isEmpty()) {
			return defaultMessage;
		}
		return ex.getAllErrors().get(0).getDefaultMessage();
	}

	public static String getConstraintExceptionMessage(
			ConstraintViolationException ex, String defaultMessage) {
		var firstViolation = ex.getConstraintViolations().stream().findFirst();
		if (firstViolation.isEmpty()) {
			return defaultMessage;
		}
		return firstViolation.get().getMessage();
	}

	public static boolean isNotValidPhone(String phone) {
		// International phone number length is between 4 and 15 (included country code)
		// https://stackoverflow.com/a/3350566/13779659
		var pattern = Pattern.compile("^(\\+|\\d)\\d{3,14}$");
		return !pattern.matcher(phone).matches();
	}

	public static <T> void updateRequiredField(@Nullable T value, Consumer<T> consumer) {
		if (Objects.nonNull(value)) {
			consumer.accept(value);
		}
	}

	public static <T> void updateOptionalField(
			@Nullable T value, Predicate<T> blankPredicate, Consumer<T> consumer) {
		if (Objects.nonNull(value)) {
			if (blankPredicate.test(value)) {
				consumer.accept(null);
			} else {
				consumer.accept(value);
			}
		}
	}

//	public static <T> ResponseEntity<T> buildPaginationResponse(PageHolder pageHolder, T data) {
//		return ResponseEntity.ok().headers(pageHolder.buildPaginationHeaders()).body(data);
//	}

	public static String createSearchLike(@Nullable String search) {
		return Objects.isNull(search) ? "" : String.join("", "%", search.toLowerCase(), "%");
	}
	public static String generateRandomString() {
		int SIZE = 10;
		char[] CHARACTERS = {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z' };
		Random random = new Random();
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < SIZE; i++)
			str.append(CHARACTERS[random.nextInt(CHARACTERS.length)]);
		return str.toString();
	}
	public static String getFileNameExtension(String fileName) {
		char FILE_EXTENSION_DELIMITER = '.';
		int index = fileName.lastIndexOf(FILE_EXTENSION_DELIMITER);
		return index == -1 ? "" : fileName.substring(index);
	}
}
