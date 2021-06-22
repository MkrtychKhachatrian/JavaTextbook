package khachatrian.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import khachatrian.exception.ObjectNotFoundException;
import khachatrian.model.Order;
import khachatrian.model.User;
import khachatrian.model.enums.OrderStatus;
import khachatrian.repository.OrderRepository;
import khachatrian.service.TextbookService;
import khachatrian.service.OrderService;
import khachatrian.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final TextbookService textbookService;

    public List<Order> getAllOrders () {
        return orderRepository.findAll();
    }
    public Order getOrderById (Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Order.class.getName(), id));
    }

    public void saveOrder (Long userId) {
        User temp;
        temp = userService.getUserById(userId);
        Order order = new Order(temp, OrderStatus.Pending);
        orderRepository.save(order);
    }
    public void saveOrder (Order order) {
        orderRepository.save(order);
    }
    public void deleteOrder (Long id) {
        if(!orderRepository.findById(id).isPresent())
            throw new ObjectNotFoundException(Order.class.getName(),id);
        orderRepository.deleteById(id);
    }
    public void addTextbookToOrder(Long idOrder, Long idTextbook) {
        Order order = orderRepository.findById(idOrder).orElseThrow(() -> new ObjectNotFoundException(Order.class.getName(), idOrder));
        order.getTextbooks().add(textbookService.getTextbookById(idTextbook));
        orderRepository.save(order);
    }
    public void deleteLookFromOrder(Long idOrder, Long idTextbook) {
        Order order = orderRepository.findById(idOrder).orElseThrow(() -> new ObjectNotFoundException(Order.class.getName(), idOrder));
        order.getTextbooks().remove(textbookService.getTextbookById(idTextbook));
        orderRepository.save(order);
    }
}

