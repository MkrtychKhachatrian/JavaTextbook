package khachatrian.view;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.shared.Registration;
import khachatrian.model.Textbook;
import khachatrian.model.Order;
import khachatrian.model.User;

import javax.validation.ValidationException;
import java.util.List;

public class OrderForm extends FormLayout {

    private Order order;

    TextField id = new TextField("Id");
    ComboBox<User> users = new ComboBox<>("User");
    ComboBox<List<Textbook>> textbooks = new ComboBox<>("Textbooks");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button close = new Button("Cancel");

    private Binder<Order> binder = new BeanValidationBinder<>(Order.class);

    public OrderForm(List<User> users1, List<Textbook> textbook1) {
        addClassName("Order-form");
        binder.forMemberField(id).withNullRepresentation("").withConverter(new StringToLongConverter("Provide a valid number!"));
        binder.bindInstanceFields(this);

        users.setItems(users1);
        users.setItemLabelGenerator(User::getName);
        textbooks.setItems(textbook1);
        //looks.setItemLabelGenerator(Textbook::getName);
        add(
                id,
                users,
                textbooks,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, order)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(order);
            fireEvent(new SaveEvent(this, order));
        } catch (ValidationException | com.vaadin.flow.data.binder.ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setOrder(Order Order) {
        this.order = Order;
        binder.readBean(Order);
    }

    public static abstract class OrderFormEvent extends ComponentEvent<OrderForm> {
        private Order Order;

        protected OrderFormEvent(OrderForm source, Order Order) {
            super(source, false);
            this.Order = Order;
        }

        public Order getOrder() {
            return Order;
        }
    }

    public static class SaveEvent extends OrderFormEvent {
        SaveEvent(OrderForm source, Order Order) {
            super(source, Order);
        }
    }

    public static class DeleteEvent extends OrderFormEvent {
        DeleteEvent(OrderForm source, Order Order) {
            super(source, Order);
        }

    }

    public static class CloseEvent extends OrderFormEvent {
        CloseEvent(OrderForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
