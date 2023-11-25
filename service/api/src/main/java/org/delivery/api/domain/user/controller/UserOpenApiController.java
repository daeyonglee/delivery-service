package org.delivery.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.model.UserLoginRequest;
import org.delivery.api.domain.user.model.UserRegisterRequest;
import org.delivery.api.domain.user.model.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/user")
public class UserOpenApiController {

	private final UserBusiness userBusiness;

	// register user
	@PostMapping("/register")
	public Api<UserResponse> register(@Valid @RequestBody Api<UserRegisterRequest> request) {
		var response = userBusiness.register(request.getBody());
		return Api.OK(response);
	}

	@PostMapping("/login")
	// login
	public Api<TokenResponse> login(@Valid @RequestBody Api<UserLoginRequest> request) {
		var response = userBusiness.login(request.getBody());
		return Api.OK(response);
	}
}
