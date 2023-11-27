package org.delivery.storeadmin.domain.user.business;


import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.storeadmin.common.annotation.Business;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.delivery.storeadmin.domain.user.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.service.StoreUserService;

@RequiredArgsConstructor
@Business
public class StoreUserBusiness {

	private final StoreUserConverter storeUserConverter;
	private final StoreUserService storeUserService;
	private final StoreRepository storeRepository; // TODO change service logic

	public StoreUserResponse register(StoreUserRegisterRequest request) {
		var storeEntity = storeRepository.findFirstByNameAndStatusOrderByIdDesc(request.getStoreName(), StoreStatus.REGISTERED);

		var entity = storeUserConverter.toEntity(request, storeEntity.get());

		var newEntity = storeUserService.register(entity);

		var response = storeUserConverter.toResponse(newEntity, storeEntity.get());

		return response;
	}
}
