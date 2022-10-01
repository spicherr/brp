package org.spicher.brp.views.persona;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.spicher.brp.data.entity.Persona;
import org.spicher.brp.data.service.PersonaService;
import org.spicher.brp.data.service.RoleService;
import org.spicher.brp.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.vaadin.flow.component.icon.VaadinIcon.EDIT;
import static com.vaadin.flow.component.icon.VaadinIcon.TRASH;
import static org.spicher.brp.data.service.PersonaService.editPerson;

@PageTitle("Persona")
@Route(value = "persona", layout = MainLayout.class)
@Uses(Icon.class)
@Component
public class PersonaView extends VerticalLayout {
    @Autowired
    private JdbcTemplate jdbc;
    public PersonaView(JdbcTemplate jdbc) {
        this.jdbc = jdbc;

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);
        add(new H1("spicher.org"));
        TextField lastname = new TextField();
        lastname.setLabel("Nachname");
        lastname.setRequiredIndicatorVisible(true);
        TextField firstname = new TextField();
        firstname.setLabel("Vorname");
        firstname.setRequiredIndicatorVisible(true);


        ComboBox<String> comboBox = new ComboBox<>("Browser");
        comboBox.setAllowCustomValue(true);
        comboBox.setItems("Chrome", "Edge", "Firefox", "Safari");
        comboBox.setHelperText("Select or type a browser");
        ComboBox<String> comboBoxRole = new ComboBox<>("Rolle");
        comboBoxRole.setAllowCustomValue(false);
        comboBoxRole.setItems(RoleService.getAll(jdbc));
        comboBoxRole.setHelperText("Select the Role");



        FormLayout newPerson = new FormLayout();
        newPerson.add(lastname);
        newPerson.add(firstname);
        //newPerson.add(comboBox);
        //newPerson.add(comboBoxRole);

        add(newPerson);

        Button button = new Button("Click it");

        button.addClickListener(clickEvent ->
                addNewPerson(lastname.getValue(), firstname.getValue(), jdbc)
        );
        add(button);
        List<Persona> personas = PersonaService.findAll(jdbc);

        Grid<Persona> grid = new Grid<>(Persona.class, false);
// Create a grid bound to the list
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);


        grid.addColumn(Persona::getId).setHeader("ID");
        grid.addColumn(Persona::getFirstName).setHeader("Vorname");
        grid.addColumn(Persona::getLastName).setHeader("Name");
        grid.addColumn(Persona::getRoleId).setHeader("Rolle");
        grid.addColumn(Persona::getTeamId).setHeader("Team");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (bRemove, person) -> {
                    bRemove.addClickListener(e -> editPerson(person, jdbc));
                    bRemove.setIcon(new Icon(EDIT));
                })
        );
        /*
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (bRemove, person) -> {
                    bRemove.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    bRemove.addClickListener(e -> PersonaService.remove(person));
                    bRemove.setIcon(new Icon(TRASH));
                })
        );
        */
        grid.setItems(personas);
        add(grid);

    }



    private void addNewPerson(String lastName, String firstName, JdbcTemplate jdbc){
        PersonaService.createNewPerson(firstName,lastName, this.jdbc);
        Notification.show("Button Clicked!" + lastName +" "+firstName);

    }
    public void remove(Persona person, JdbcTemplate jdbc) {
        PersonaService.removePerson(person, this.jdbc);
        Notification.show("Button REMOVE was clicked for Person "+ person.getId());
    }

    public void editPerson(Persona person, JdbcTemplate jdbc) {
        PersonaService.editPerson(person, this.jdbc);
        Notification.show("Button EDIT was clicked for Person "+ person.getId());
    }

}