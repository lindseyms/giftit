package com.lindsey.giftit.items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
//    List<ItemEntity> findAllItemsByUsername(String username);

    List<ItemEntity> findAllItemsByUserId(Long userId);

}
