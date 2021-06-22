package khachatrian.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import khachatrian.model.Order;
import khachatrian.service.TextbookService;
import khachatrian.service.OrderService;
import khachatrian.service.UserService;

@Route(value = "orders", layout = MainLayout.class)
public class OrderList extends VerticalLayout {

    private final OrderService orderService;
    private final UserService userService;
    private final TextbookService textbookService;
    private final Grid<Order> grid = new Grid<>(Order.class);
    private final OrderForm form;

    public OrderList(OrderService OrderService, UserService userService1, TextbookService textbookService1) {

        this.orderService = OrderService;
        this.userService = userService1;
        this.textbookService = textbookService1;
        addClassName("main-view");
        setSizeFull();
        configureGrid();

        form = new OrderForm(userService.getAllUser(), textbookService.getTextbooks());

        form.addListener(OrderForm.SaveEvent.class, this::saveOrder);
        form.addListener(OrderForm.DeleteEvent.class, this::deleteOrder);
        form.addListener(OrderForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);

        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();

        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        Button addContactButton = new Button("Add Order");
        addContactButton.addClickListener(click -> addOrder());
        HorizontalLayout toolbar = new HorizontalLayout(addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addOrder() {
        grid.asSingleSelect().clear();
        editOrder(new Order());
    }

    private void saveOrder(OrderForm.SaveEvent event) {
        orderService.saveOrder(event.getOrder());
        updateList();
        closeEditor();
    }



    private void deleteOrder(OrderForm.DeleteEvent event) {
        orderService.deleteOrder(event.getOrder().getId());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setOrder(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(orderService.getAllOrders());
    }

    private void configureGrid() {
        grid.addClassName("Order-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Order::getId).setHeader("#");

        grid.addColumn(Order::getId).setHeader("Id");
        grid.addColumn(Order::getUser).setHeader("User");
        grid.addColumn(Order::getTextbooks).setHeader("Textbooks");


        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editOrder(event.getValue()));
    }

    private void editOrder(Order value) {
        if (value == null) {
            closeEditor();
        } else {
            form.setOrder(value);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}
