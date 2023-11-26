package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.model.UserOrderRequest;
import org.delivery.api.domain.userorder.model.UserOrderResponse;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

	private final UserOrderService userOrderService;
	private final UserOrderConverter userOrderConverter;
	private final StoreMenuService storeMenuService;
	private final UserOrderMenuConverter userOrderMenuConverter;
	private final UserOrderMenuService userOrderMenuService;

	public UserOrderResponse userOrder(User user, UserOrderRequest request) {
		var storeMenuEntityList = request.getStoreMenuIdList().stream()
			.map(storeMenuService::getStoreMenuWithThrow)
			.collect(Collectors.toList());

		var userOrderEntity = userOrderConverter.toEntity(user, storeMenuEntityList);

		var newUserOrderEntity = userOrderService.order(userOrderEntity);

		var userOrderMenuEntityList = storeMenuEntityList.stream()
			.map(it -> {
				return userOrderMenuConverter.toEntity(
					newUserOrderEntity,
					it
				);
			})
			.collect(Collectors.toList());

		userOrderMenuEntityList.forEach(userOrderMenuService::order);

		return userOrderConverter.toResponse(newUserOrderEntity);
	}
}
