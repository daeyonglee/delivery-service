package org.delivery.api.domain.userorder.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.business.UserOrderBusiness;
import org.delivery.api.domain.userorder.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.model.UserOrderRequest;
import org.delivery.api.domain.userorder.model.UserOrderResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-order")
public class UserOrderApiController {

	private final UserOrderBusiness userOrderBusiness;

	@PostMapping("")
	public Api<UserOrderResponse> userOrder(
		@Valid @RequestBody Api<UserOrderRequest> userOrderRequest,
		@Parameter(hidden = true) @UserSession User user
	) {
		var request = userOrderRequest.getBody();
		var response = userOrderBusiness.userOrder(user, request);

		return Api.OK(response);
	}

	@GetMapping("/current")
	public Api<List<UserOrderDetailResponse>> current(
		@Parameter(hidden = true) @UserSession User user
	) {
		var response = userOrderBusiness.current(user);
		return Api.OK(response);
	}

	@GetMapping("/history")
	public Api<List<UserOrderDetailResponse>> history(
		@Parameter(hidden = true) @UserSession User user
	) {
		var response = userOrderBusiness.history(user);
		return Api.OK(response);
	}

	@GetMapping("/id/{orderId}")
	public Api<UserOrderDetailResponse> read(
		@PathVariable Long orderId,
		@Parameter(hidden = true) @UserSession User user
	) {
		var response = userOrderBusiness.read(user, orderId);
		return Api.OK(response);
	}
}
