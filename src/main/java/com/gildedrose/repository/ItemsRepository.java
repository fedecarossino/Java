package com.gildedrose.repository;

import com.gildedrose.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ItemsRepository extends JpaRepository<Item, Integer>{

    List<Item> findAll();

    @Transactional
    Item save(Item item);

    @Transactional
    @Modifying
    @Query("UPDATE Item SET quality = :quality")
    int updateAllItemsQuality(@Param("quality") int quality);

}
