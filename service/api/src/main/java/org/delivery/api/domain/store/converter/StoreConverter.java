package org.delivery.api.domain.store.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.store.model.StoreRegisterRequest;
import org.delivery.api.domain.store.model.StoreResponse;
import org.delivery.db.store.StoreEntity;

import java.util.Optional;

@Converter
public class StoreConverter {

	public StoreEntity toEntity(StoreRegisterRequest storeRegisterRequest) {
		return Optional.ofNullable(storeRegisterRequest)
			.map(it -> {
				return StoreEntity.builder()
					.name(storeRegisterRequest.getName())
					.address(storeRegisterRequest.getAddress())
					.category(storeRegisterRequest.getStoreCategory())
					.minimumAmount(storeRegisterRequest.getMinimumAmount())
					.minimumDeliveryAmount(storeRegisterRequest.getMinimumDeliveryAmount())
					.thumbnailUrl(storeRegisterRequest.getThumbnailUrl())
					.phoneNumber(storeRegisterRequest.getPhoneNumber())
					.build();
			})
			.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));

	}

	public StoreResponse toResponse(StoreEntity entity) {
		return Optional.ofNullable(entity)
			.map(it -> {
				return StoreResponse.builder()
					.id(entity.getId())
					.name(entity.getName())
					.status(entity.getStatus())
					.category(entity.getCategory())
					.address(entity.getAddress())
					.minimumAmount(entity.getMinimumAmount())
					.minimumDeliveryAmount(entity.getMinimumDeliveryAmount())
					.thumbnailUrl(entity.getThumbnailUrl())
					.phoneNumber(entity.getPhoneNumber())
					.star(entity.getStar())
					.build();
			}).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));

	}
}
