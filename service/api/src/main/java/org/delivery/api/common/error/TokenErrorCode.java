package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 2000 ~
 */
@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeIfs {

	INVALID_TOKEN(400, 2000, "invalid token"),
	EXPIRED_TOKEN(400, 2001, "expired token"),
	TOKEN_EXCEPTION(400, 2002, "unknown token"),
	AUTHORIZATION_TOKEN_NOT_FOUND(400, 2003, "authorization token not found")

	;

	private final Integer httpStatusCode;
	private final Integer errorCode;
	private final String description;
}
