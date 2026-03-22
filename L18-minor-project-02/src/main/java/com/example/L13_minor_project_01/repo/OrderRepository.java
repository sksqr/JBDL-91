package com.example.L13_minor_project_01.repo;

import com.example.L13_minor_project_01.entity.Order;
import com.example.L13_minor_project_01.entity.OrderStatus;
import com.example.L13_minor_project_01.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findFirstByUserIdAndStatusOrderByUpdatedAtDesc(Long userId, OrderStatus status);

    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.product"})
    Optional<Order> findWithDetailsById(Long id);

    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.product"})
    List<Order> findDistinctByStatusAndOrderItemsProductCompanyId(OrderStatus status, Long companyId);

    List<Order> findByStatusAndUpdatedAtBefore(OrderStatus status, java.time.LocalDateTime updatedAt);


    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.product"})
    Optional<Order> findWithDetailsByIdAndUser(Long id,  User user);
}
