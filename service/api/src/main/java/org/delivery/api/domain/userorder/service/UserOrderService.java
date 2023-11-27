package org.delivery.api.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserOrderService {

	private final UserOrderRepository userOrderRepository;


	public UserOrderEntity getUserOrderWithoutStatusWithThrow(
		Long id,
		Long userId
	) {
		return userOrderRepository.findALlByIdAndUserId(id, userId)
			.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
	}

	public UserOrderEntity getUserOrderWithThrow(
		Long id,
		Long userId
	) {
		return userOrderRepository.findAllByIdAndStatusAndUserId(id, UserOrderStatus.REGISTERED, userId)
			.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
	}

	public List<UserOrderEntity> getUserOrderList(Long userId, List<UserOrderStatus> statusList) {
		return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(userId, statusList);
	}

	public UserOrderEntity order(
		UserOrderEntity userOrderEntity
	) {
		return Optional.ofNullable(userOrderEntity)
			.map(it -> {
				it.setStatus(UserOrderStatus.ORDER);
				it.setOrderedAt(LocalDateTime.now());
				return userOrderRepository.save(it);
			})
			.orElseThrow(
				() -> new ApiException(ErrorCode.NULL_POINT)
			);
	}

	public UserOrderEntity setStatus(UserOrderEntity userOrderEntity, UserOrderStatus status) {
		userOrderEntity.setStatus(status);
		return userOrderRepository.save(userOrderEntity);
	}


	public UserOrderEntity accept(UserOrderEntity userOrderEntity) {
		userOrderEntity.setAcceptedAt(LocalDateTime.now());
		return setStatus(userOrderEntity, UserOrderStatus.ACCEPT);
	}

	public UserOrderEntity cooking(UserOrderEntity userOrderEntity) {
		userOrderEntity.setCookingStartedAt(LocalDateTime.now());
		return setStatus(userOrderEntity, UserOrderStatus.COOKING);
	}

	public UserOrderEntity delivery(UserOrderEntity userOrderEntity) {
		userOrderEntity.setDeliveryStartedAt(LocalDateTime.now());
		return setStatus(userOrderEntity, UserOrderStatus.DELIVERY);
	}

	public UserOrderEntity receive(UserOrderEntity userOrderEntity) {
		userOrderEntity.setReceivedAt(LocalDateTime.now());
		return setStatus(userOrderEntity, UserOrderStatus.RECEIVE);
	}

	public List<UserOrderEntity> current(Long userId) {
		return getUserOrderList(
			userId,
			List.of(
				UserOrderStatus.ORDER,
				UserOrderStatus.COOKING,
				UserOrderStatus.DELIVERY,
				UserOrderStatus.ACCEPT
			)
		);
	}

	public List<UserOrderEntity> history(Long userId) {
		return getUserOrderList(
			userId,
			List.of(
				UserOrderStatus.RECEIVE
			)
		);
	}




}