package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.delivery.storeadmin.domain.userorder.converter.UserOrderConverter;
import org.delivery.storeadmin.domain.userorder.model.UserOrderDetailResponse;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserOrderBusiness {

	private final UserOrderService userOrderService;
	private final UserOrderConverter userOrderConverter;
	private final SseConnectionPool sseConnectionPool;
	private final UserOrderMenuService userOrderMenuService;
	private final StoreMenuService storeMenuService;
	private final StoreMenuConverter storeMenuConverter;

	/**
	 * order
	 * find order history
	 * find store
	 * find connected session
	 */
	public void pushUserOrder(UserOrderMessage userOrderMessage) {
		var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
			.orElseThrow(() -> new RuntimeException("not found order history"));


		var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());

		var storeMenuResponseList = userOrderMenuList.stream()
			.map(userOrderMenuEntity -> {
				return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
			})
			.map(storeMenuEntity -> {
				return storeMenuConverter.toResponse(storeMenuEntity);
			})
			.collect(Collectors.toList());

		var userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

		var push = UserOrderDetailResponse.builder()
			.userOrderResponse(userOrderResponse)
			.storeMenuResponsesList(storeMenuResponseList)
			.build();


		var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

		// menu, amount,

		//
		userConnection.sendMessage(push);
	}
}
