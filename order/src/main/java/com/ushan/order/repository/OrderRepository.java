package com.ushan.order.repository;

import com.ushan.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "SELECT * FROM orders WHERE id=?1", nativeQuery = true)
    Order getOrderById(Integer orderId);

}
