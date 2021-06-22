package khachatrian.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import khachatrian.model.User;
import khachatrian.service.RoleService;
import khachatrian.service.UserService;

@Route(value = "users", layout = MainLayout.class)
public class UserList extends VerticalLayout {

    private final UserService userService;
    private final RoleService roleService;
    private final Grid<User> grid = new Grid<>(User.class);
    private final UserForm form;

    public UserList(UserService userService, RoleService roleService) {

        this.userService = userService;
        this.roleService = roleService;
        addClassName("main-view");
        setSizeFull();
        configureGrid();

        form = new UserForm(roleService.getAllRole());

        form.addListener(UserForm.SaveEvent.class, this::saveUser);
        form.addListener(UserForm.DeleteEvent.class, this::deleteUser);
        form.addListener(UserForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);

        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();

        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        Button addContactButton = new Button("Add user");
        addContactButton.addClickListener(click -> addUser());
        HorizontalLayout toolbar = new HorizontalLayout(addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addUser() {
        grid.asSingleSelect().clear();
        editUser(new User());
    }

    private void saveUser(UserForm.SaveEvent event) {
        userService.saveUser(event.getUser());
        updateList();
        closeEditor();
    }



    private void deleteUser(UserForm.DeleteEvent event) {
        userService.deleteUser(event.getUser().getId());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(userService.getAllUser());
    }

    private void configureGrid() {
        grid.addClassName("user-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(User::getId).setHeader("#");

        grid.addColumn(User::getName).setHeader("Name");
        grid.addColumn(User::getSurname).setHeader("SurName");
        grid.addColumn(User::getRole).setHeader("RoleUser");


        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editUser(event.getValue()));
    }

    private void editUser(User value) {
        if (value == null) {
            closeEditor();
        } else {
            form.setUser(value);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}
