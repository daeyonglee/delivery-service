package org.delivery.storeadmin.domain.storemenu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.storemenu.enums.StoreMenuStatus;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreMenuResponse {

	private Long id;

	private String name;

	private BigDecimal amount;

	private StoreMenuStatus status;

	private String thumbnailUrl;

	private int likeCount;

	private int sequence;
}
