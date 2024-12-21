package com.ushan.inventory.repository;

import com.ushan.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    @Query(value = "SELECT * FROM Inventory WHERE id= ?1", nativeQuery = true)
    Inventory getItemById(Integer itemId);

}
