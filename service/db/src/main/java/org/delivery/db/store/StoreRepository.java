package org.delivery.db.store;

import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

	// valid store one
	// select * from store where id = ? and status = ? order by id desc limit 1
	Optional<StoreEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreStatus status);

	// valid store list
	// select * from store where status = ? order by id desc
	List<StoreEntity> findAllByStatusOrderByIdDesc(StoreStatus status);

	// valid store list by category
	// select * from store where status = ? and category = ? order by star desc
	List<StoreEntity> findAllByStatusAndCategoryOrderByStarDesc(StoreStatus storeStatus, StoreCategory storeCategory);

	// select * from store where name = ? and status = ? order by id desc limit 1
	Optional<StoreEntity> findFirstByNameAndStatusOrderByIdDesc(String name, StoreStatus status);
}
