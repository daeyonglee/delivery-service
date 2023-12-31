package org.delivery.api.domain.userorder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.domain.store.model.StoreResponse;
import org.delivery.api.domain.storemenu.model.StoreMenuResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderDetailResponse {

	private UserOrderResponse userOrderResponse;
	private StoreResponse storeResponse;
	private List<StoreMenuResponse> storeMenuResponseList;


}
