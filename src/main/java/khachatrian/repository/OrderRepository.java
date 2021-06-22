package khachatrian.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import khachatrian.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> { }

