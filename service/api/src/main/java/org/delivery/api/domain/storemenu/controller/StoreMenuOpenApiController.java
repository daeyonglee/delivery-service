package org.delivery.api.domain.storemenu.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import org.delivery.api.domain.storemenu.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.model.StoreMenuResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/store-menu")
public class StoreMenuOpenApiController {

	private final StoreMenuBusiness storeMenuBusiness;

	@PostMapping("/regstier")
	public Api<StoreMenuResponse> register(
		@Valid
		@RequestBody Api<StoreMenuRegisterRequest> request
	) {
		var req = request.getBody();
		var response = storeMenuBusiness.register(req);
		return Api.OK(response);
	}
}
