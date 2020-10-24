package com.lindsey.giftit.items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findAllItemsByUserId(Long userId);

    ItemEntity findByTitle(String title);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM items WHERE link = :link", nativeQuery = true)
    void deleteByLink(@Param("link") String link);

}
