package khachatrian.service;

import khachatrian.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders ();
    Order getOrderById (Long id);

    void saveOrder (Long userId);
    void saveOrder (Order order);
    void deleteOrder (Long id);

    void addTextbookToOrder(Long idOrder, Long idLook);
    void deleteLookFromOrder (Long idOrder, Long idLook);
}

