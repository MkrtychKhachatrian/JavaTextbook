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
import com.vaadin.flow.shared.Registration;
import khachatrian.model.RoleUser;
import khachatrian.model.User;

import javax.validation.ValidationException;
import java.util.List;

public class UserForm extends FormLayout {

    private User user;

    TextField name = new TextField("Name");
    TextField surname = new TextField("SurName");

    ComboBox<RoleUser> role = new ComboBox<>("RoleUser");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button close = new Button("Cancel");

    private Binder<User> binder = new BeanValidationBinder<>(User.class);

    public UserForm(List<RoleUser> role) {
        addClassName("User-form");
        binder.bindInstanceFields(this);

        this.role.setItems(role);
        this.role.setItemLabelGenerator(RoleUser::getName);
        add(
                name,
                surname,
                this.role,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, user)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(user);
            fireEvent(new SaveEvent(this, user));
        } catch (ValidationException | com.vaadin.flow.data.binder.ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setUser(User User) {
        this.user = User;
        binder.readBean(User);
    }

    public static abstract class UserFormEvent extends ComponentEvent<UserForm> {
        private User User;

        protected UserFormEvent(UserForm source, User User) {
            super(source, false);
            this.User = User;
        }

        public User getUser() {
            return User;
        }
    }

    public static class SaveEvent extends UserFormEvent {
        SaveEvent(UserForm source, User User) {
            super(source, User);
        }
    }

    public static class DeleteEvent extends UserFormEvent {
        DeleteEvent(UserForm source, User User) {
            super(source, User);
        }

    }

    public static class CloseEvent extends UserFormEvent {
        CloseEvent(UserForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}


