package khachatrian.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import khachatrian.model.Textbook;
import khachatrian.service.TextbookService;


@Route(value = "textbooks", layout = MainLayout.class)
public class TextbookList extends VerticalLayout {

    private final TextbookService textbookService;
    private final Grid<Textbook> grid = new Grid<>(Textbook.class);
    private final TextbookForm form;

    public TextbookList(TextbookService textbookService) {

        this.textbookService = textbookService;
        addClassName("main-view");
        setSizeFull();
        configureGrid();

        form = new TextbookForm();

        form.addListener(TextbookForm.SaveEvent.class, this::saveTextbook);
        form.addListener(TextbookForm.DeleteEvent.class, this::deleteTextbook);
        form.addListener(TextbookForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);

        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();

        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        Button addContactButton = new Button("Add Textbook");
        addContactButton.addClickListener(click -> addTextbook());
        HorizontalLayout toolbar = new HorizontalLayout(addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addTextbook() {
        grid.asSingleSelect().clear();
        editLook(new Textbook());
    }

    private void saveTextbook(TextbookForm.SaveEvent event) {
        textbookService.saveTextbook(event.getTextbook());
        updateList();
        closeEditor();
    }



    private void deleteTextbook(TextbookForm.DeleteEvent event) {
        textbookService.deleteTextbookById(event.getTextbook().getId());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setTextbook(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(textbookService.getTextbooks());
    }

    private void configureGrid() {
        grid.addClassName("look-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Textbook::getId).setHeader("#");

        grid.addColumn(Textbook::getName).setHeader("Name");
        grid.addColumn(Textbook::getPrice).setHeader("Price($)");
        grid.addColumn(Textbook::getSize).setHeader("Size");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editLook(event.getValue()));
    }

    private void editLook(Textbook value) {
        if (value == null) {
            closeEditor();
        } else {
            form.setTextbook(value);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}

