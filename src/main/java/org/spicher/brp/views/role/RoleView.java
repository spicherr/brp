package org.spicher.brp.views.role;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.spicher.brp.data.service.RoleService;
import org.spicher.brp.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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

    }

    private void addRole(String roleName, JdbcTemplate jdbc) {
        RoleService.addRole(roleName, this.jdbc);
        Notification.show(roleName  + " wurde erstellt");
    }
}
