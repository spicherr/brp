package org.spicher.brp.views.role;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.spicher.brp.data.entity.Persona;
import org.spicher.brp.data.entity.Role;
import org.spicher.brp.data.service.PersonaService;
import org.spicher.brp.data.service.RoleService;
import org.spicher.brp.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.vaadin.flow.component.icon.VaadinIcon.DEL;

@PageTitle("Rollen")
//@Route(value = "project/:projectID?/:action?(edit)", layout = MainLayout.class)
@Route(value = "role", layout = MainLayout.class)
@Uses(Icon.class)
@Component
public class RoleView extends VerticalLayout {
    @Autowired
    private static JdbcTemplate jdbc;
    public RoleView(JdbcTemplate jdbc){
        this.jdbc = jdbc;
        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);
        add(new H1("spicher.org"));

        TextField roleName = new TextField();
        roleName.setLabel("Rollenbezeichnung");
        Button bAdd = new Button("hinzufügen");
        bAdd.addClickListener(clickEvent ->
                addRole(roleName.getValue(), jdbc)
        );
        add(roleName);
        add(bAdd);
        getGrid(jdbc);

    }

    private void getGrid(JdbcTemplate jdbc) {
        List<Role> role = RoleService.getAllActive(jdbc);

        Grid<Role> grid = new Grid<>(Role.class, false);

// Create a grid bound to the list
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addColumn(Role::getId).setHeader("ID");
        grid.addColumn(Role::getName).setHeader("Name")
        ;
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (bRemove, specRole) -> {
                    bRemove.addClickListener(e -> inactivateRole(specRole, jdbc));
                    bRemove.setIcon(new Icon(DEL));
                })
        );
        grid.setItems(role);
        grid.getDataProvider().refreshAll();
        add(grid);
    }

    private void addRole(String roleName, JdbcTemplate jdbc) {
        RoleService.addRole(roleName, this.jdbc);
        Notification.show(roleName  + " wurde erstellt");
    }
    private static void inactivateRole(Role role, JdbcTemplate jdbc) {
        RoleService.inactivateRole(role.getId(), jdbc);
        Notification.show(role.getName()  + " wurde inaktviert");


    }
}
