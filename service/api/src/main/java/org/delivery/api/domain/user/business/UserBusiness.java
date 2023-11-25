package org.delivery.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.model.UserLoginRequest;
import org.delivery.api.domain.user.model.UserRegisterRequest;
import org.delivery.api.domain.user.model.UserResponse;
import org.delivery.api.domain.user.service.UserService;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Business
public class UserBusiness {

	private final UserService userService;
	private final UserConverter userConverter;
	private final TokenBusiness tokenBusiness;

	/**
	 * 사용자에 대한 가입처리 로직
	 * 1. request -> entity
	 * 2. entity -> save
	 * 3. save Entity -> response
	 * 4. response return
	 */
	public UserResponse register(UserRegisterRequest request) {
		var entity = userConverter.toEntity(request);
		var newEntity = userService.register(entity);
		var response = userConverter.toResponse(newEntity);
		return response;
		/*return Optional.ofNullable(request)
			.map(userConverter::toEntity)
			.map(userService::register)
			.map(userConverter::toResponse)
			.orElseThrow(
				() -> new ApiException(ErrorCode.NULL_POINT, "request null")
			);*/
	}

	/**
	 * 1. check user with email, pasword
	 * 2. user entity login confirm
	 * 3. create token
	 * 4. return token response
	 */
	public TokenResponse login(UserLoginRequest request) {
		var userEntity = userService.login(request.getEmail(), request.getPassword());
		// if user not found, throw
		var tokenResponse = tokenBusiness.issueToken(userEntity);
		return tokenResponse;
	}

	public UserResponse me(User user) {
		var userEntity = userService.getUserWithThrow(user.getId());
		var response = userConverter.toResponse(userEntity);
		return response;
	}
}
