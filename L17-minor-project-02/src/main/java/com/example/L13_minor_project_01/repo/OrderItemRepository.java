package com.example.L13_minor_project_01.repo;

import com.example.L13_minor_project_01.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findByOrderIdAndProductId(Long orderId, Long productId);
}
