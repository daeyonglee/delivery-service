package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.model.UserOrderRequest;
import org.delivery.api.domain.userorder.model.UserOrderResponse;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.delivery.db.store.StoreEntity;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

	private final UserOrderService userOrderService;
	private final UserOrderConverter userOrderConverter;
	private final StoreMenuService storeMenuService;
	private final UserOrderMenuConverter userOrderMenuConverter;
	private final UserOrderMenuService userOrderMenuService;
	private final StoreService storeService;
	private final StoreMenuConverter storeMenuConverter;
	private final StoreConverter storeConverter;

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

	public List<UserOrderDetailResponse> current(User user) {
		var userOrderEntityList = userOrderService.current(user.getId());
		var userOrderDetailResponseList = userOrderEntityList.stream()
			.map(it -> {
				var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
				var storeMenuEntityList = userOrderMenuEntityList.stream()
					.map(userOrderMenuEntity -> {
						return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
					})
					.collect(Collectors.toList());
				// TODO Refactoring
				var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

				return UserOrderDetailResponse.builder()
					.userOrderResponse(userOrderConverter.toResponse(it))
					.storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
					.storeResponse(storeConverter.toResponse(storeEntity))
					.build();
			}).collect(Collectors.toList());
		return userOrderDetailResponseList;
	}

	public List<UserOrderDetailResponse> history(User user) {
		var userOrderEntityList = userOrderService.history(user.getId());
		var userOrderDetailResponseList = userOrderEntityList.stream()
			.map(it -> {
				var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
				var storeMenuEntityList = userOrderMenuEntityList.stream()
					.map(userOrderMenuEntity -> {
						return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
					})
					.collect(Collectors.toList());
				// TODO Refactoring
				var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

				return UserOrderDetailResponse.builder()
					.userOrderResponse(userOrderConverter.toResponse(it))
					.storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
					.storeResponse(storeConverter.toResponse(storeEntity))
					.build();
			}).collect(Collectors.toList());
		return userOrderDetailResponseList;
	}

	public UserOrderDetailResponse read(User user, Long orderId) {

		var userOrderEntity = userOrderService.getUserOrderWithoutStatusWithThrow(orderId, user.getId());
		var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());
		var storeMenuEntityList = userOrderMenuEntityList.stream()
			.map(userOrderMenuEntity -> {
				return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
			})
			.collect(Collectors.toList());
		// TODO Refactoring
		var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

		return UserOrderDetailResponse.builder()
			.userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
			.storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
			.storeResponse(storeConverter.toResponse(storeEntity))
			.build();
	}
}
