package khachatrian.view;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import khachatrian.model.Textbook;
import khachatrian.model.enums.Subject;
import khachatrian.model.enums.Color;
import khachatrian.model.enums.Size;
import khachatrian.model.enums.Material;

import javax.validation.ValidationException;

public class TextbookForm extends FormLayout {

    private Textbook textbook;

    TextField name = new TextField("Name");
    NumberField price = new NumberField("Price");
    ComboBox<Size> size = new ComboBox<>("Size");
    ComboBox<Color> color = new ComboBox<>("Color");
    ComboBox<Subject> subject = new ComboBox<>("Subject");
    ComboBox<Material> material = new ComboBox<>("Material");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button close = new Button("Cancel");

    private Binder<Textbook> binder = new BeanValidationBinder<>(Textbook.class);

    public TextbookForm() {
        addClassName("textbook-form");
        binder.bindInstanceFields(this);
        size.setItems(Size.values());
        color.setItems(Color.values());
        subject.setItems(Subject.values());
        material.setItems(Material.values());
        add(
                name,
                price,
                size,
                color,
                subject,
                material,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, textbook)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(textbook);
            fireEvent(new SaveEvent(this, textbook));
        } catch (ValidationException | com.vaadin.flow.data.binder.ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setTextbook(Textbook Textbook) {
        this.textbook = Textbook;
        binder.readBean(Textbook);
    }

    public static abstract class LookFormEvent extends ComponentEvent<TextbookForm> {
        private Textbook textbook;

        protected LookFormEvent(TextbookForm source, Textbook Textbook) {
            super(source, false);
            this.textbook = Textbook;
        }

        public Textbook getTextbook() {
            return textbook;
        }
    }

    public static class SaveEvent extends LookFormEvent {
        SaveEvent(TextbookForm source, Textbook Textbook) {
            super(source, Textbook);
        }
    }

    public static class DeleteEvent extends LookFormEvent {
        DeleteEvent(TextbookForm source, Textbook Textbook) {
            super(source, Textbook);
        }

    }

    public static class CloseEvent extends LookFormEvent {
        CloseEvent(TextbookForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}

