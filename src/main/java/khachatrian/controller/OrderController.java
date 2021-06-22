package khachatrian.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import khachatrian.model.Order;
import khachatrian.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController {
    
    private final OrderService orderService;

    @GetMapping("/")
    public List<Order> findAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order findOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping("/user/{userId}")
    public List<Order> saveOrder(@PathVariable Long userId) {
        orderService.saveOrder(userId);
        return findAllOrders();
    }

    @DeleteMapping("/{id}")
    public List<Order> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return findAllOrders();
    }

    @PatchMapping("/addTextbook/")
    @ResponseBody
    public List<Order> addTextbookToOrder(@RequestParam Long idOrder, @RequestParam Long idTextbook) {
        orderService.addTextbookToOrder(idOrder,idTextbook);
        return orderService.getAllOrders();
    }

    @DeleteMapping("/deleteTextbook/")
    @ResponseBody
    public List<Order> deleteTextbookFromOrder(@RequestParam Long idOrder, @RequestParam Long idTextbook) {
        orderService.deleteLookFromOrder(idOrder,idTextbook);
        return orderService.getAllOrders();
    }
}

