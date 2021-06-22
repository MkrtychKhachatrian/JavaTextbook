package khachatrian;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import khachatrian.context.Application;
import khachatrian.controller.TextbookController;
import khachatrian.controller.OrderController;
import khachatrian.controller.UserController;
import khachatrian.exception.ObjectNotFoundException;
import khachatrian.model.Order;
import khachatrian.model.enums.OrderStatus;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class OrderTestIT {
    AnnotationConfigApplicationContext context;
    OrderController orderController;
    TextbookController textbookController;
    UserController userController;
    List<Order> expected = new LinkedList<>();
    Long savedID = null;

    @Before
    public void setUp () {
        context = new AnnotationConfigApplicationContext(Application.class);

        textbookController = context.getBean(TextbookController.class);
        orderController = context.getBean(OrderController.class);
        userController = context.getBean(UserController.class);

        expected = orderController.findAllOrders();
    }
    @Test
    public void findAllOrders_isFindCorrect_true () {
        //GIVEN
        List<Order> expected1 = new LinkedList<>();
        expected1.add(new Order(1L, userController.findUserById(1L), OrderStatus.Confirmed, Collections.emptyList()));
        expected1.add(new Order(2L, userController.findUserById(2L), OrderStatus.Cancelled, Collections.emptyList()));
        expected1.add(new Order(3L, userController.findUserById(3L), OrderStatus.Delivered, Collections.emptyList()));
        //WHEN
        List<Order> actual = orderController.findAllOrders();
        //THEN
        Assert.assertEquals(expected1,actual);
    }
    @Test
    public void findOrderByID_isFindCorrect_true () {
        //GIVEN
        //WHEN
        Order actual = orderController.findOrderById(1L);
        //THEN
        Assert.assertEquals(expected.get(0),actual);
    }
    @Test(expected = ObjectNotFoundException.class)
    public void findOrderByID_whenIdIsInvalid_true () {
        //GIVEN
        //WHEN
        orderController.findOrderById(100L);
        //THEN
    }
    @Test
    public void saveOrder_isSaveCorrect_true () {
        //GIVEN
        Order test = new Order(userController.findUserById(2L), OrderStatus.Pending);
        expected.add(test);
        //WHEN
        orderController.saveOrder(2L);
        List<Order> actual = orderController.findAllOrders();
        savedID = actual.get(actual.size()-1).getId();
        expected.get(expected.size()-1).setId(savedID);
        //THEN


        Assert.assertEquals(expected,actual);
    }
    @Test
    public void deleteOrder_isDeleteCorrect_true () {
        //GIVEN
        orderController.saveOrder(2L);
        List<Order> actual = orderController.findAllOrders();
        savedID = actual.get(actual.size()-1).getId();
        //WHEN
        orderController.deleteOrder(savedID);
        actual = orderController.findAllOrders();
        savedID = null;
        //THEN
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void addLook_isAddCorrect_true () {
        //GIVEN
        Order expected = orderController.findOrderById(1L);
        expected.getTextbooks().add(textbookController.findTextbookById(2L));

        //WHEN
        orderController.addTextbookToOrder(1L, 2L);
        //THEN
        Assert.assertEquals(expected,orderController.findOrderById(1L));
        orderController.deleteTextbookFromOrder(1L,2L);
    }


    @After
    public void tearDown () {
        if (savedID!=null)
            orderController.deleteOrder(savedID);
    }
}

