package org.delivery.storeadmin.domain.userorder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.userorder.enums.UserOrderStatus;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderResponse {

	private Long id;

	private Long userId;

	private Long storeId;

	private UserOrderStatus status;

	private BigDecimal amount;

	private LocalDateTime orderedAt;

	private LocalDateTime acceptedAt;

	private LocalDateTime cookingStartedAt;

	private LocalDateTime deliveryStartedAt;

	private LocalDateTime receivedAt;
}
