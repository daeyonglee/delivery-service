package org.delivery.storeadmin.domain.userorder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.storeadmin.domain.storemenu.model.StoreMenuResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderDetailResponse {

	private UserOrderResponse userOrderResponse;
	private List<StoreMenuResponse>  storeMenuResponsesList;
}
