package org.spicher.brp.views.persona;


import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.spicher.brp.data.entity.Persona;
import org.spicher.brp.data.entity.Role;
import org.spicher.brp.data.entity.Team;
import org.spicher.brp.data.service.PersonaRoleTeamService;
import org.spicher.brp.data.service.PersonaService;
import org.spicher.brp.data.service.RoleService;
import org.spicher.brp.data.service.TeamService;
import org.spicher.brp.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.spicher.brp.data.service.PersonaService.personAlreadyExists;
import static org.spicher.brp.FormElements.getTextField;

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
        TextField lastname = getTextField("Nachname");
        TextField firstname = getTextField("Vorname");


        RadioButtonGroup<Role> radioRole = getRoleRadioButtonGroup(jdbc);
        RadioButtonGroup<Team> radioTeam = getTeamRadioButtonGroup(jdbc);

        FormLayout newPerson = getFormForPerson(lastname, firstname, radioRole, radioTeam);

        add(newPerson);

        Button button = new Button("Click it");
        button.addClickListener(clickEvent ->
                addNewPerson(lastname.getValue(), firstname.getValue(), radioRole.getValue(), radioTeam.getValue(), jdbc)
        );
        add(button);

        getGrid(jdbc);

    }

    private void getGrid(JdbcTemplate jdbc) {
        List<Persona> personas = PersonaService.findAll(jdbc);

        Grid<Persona> grid = new Grid<>(Persona.class, false);
// Create a grid bound to the list
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);


        grid.addColumn(Persona::getId).setHeader("ID");
        grid.addColumn(Persona::getFirstName).setHeader("Vorname");
        grid.addColumn(Persona::getLastName).setHeader("Name");
        grid.addColumn(Persona::getRoleId).setHeader("Rolle");
        grid.addColumn(Persona::getTeamId).setHeader("Team");
        /*
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (bRemove, person) -> {
                    bRemove.addClickListener(e -> editPerson(person, jdbc));
                    bRemove.setIcon(new Icon(EDIT));
                })
        );
        */
        grid.setItems(personas);
        grid.getDataProvider().refreshAll();
        add(grid);
    }
    private static FormLayout getFormForPerson(TextField lastname, TextField firstname, RadioButtonGroup<Role> radioRole, RadioButtonGroup<Team> radioTeam) {
        FormLayout newPerson = new FormLayout();
        newPerson.add(lastname);
        newPerson.add(firstname);
        newPerson.add(radioRole);
        newPerson.add(radioTeam);
        return newPerson;
    }
    private static RadioButtonGroup<Team> getTeamRadioButtonGroup(JdbcTemplate jdbc) {
        RadioButtonGroup<Team> radioTeam = new RadioButtonGroup<>("Teamzuteilung");
        radioTeam.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioTeam.setHelperText("Select the Team");
        List<Team> teamList = TeamService.getAll(jdbc);
        radioTeam.setItems(teamList);
        radioTeam.setValue(teamList.get(0));
        radioTeam.setRenderer(
                new ComponentRenderer<>(team -> {
                    Text textTeam = new Text(String.valueOf(team.getId()) + " " + team.getName());
                    return new Div(textTeam);
                }
                )
        );
        return radioTeam;
    }

    private static RadioButtonGroup<Role> getRoleRadioButtonGroup(JdbcTemplate jdbc) {
        RadioButtonGroup<Role> radioRole = new RadioButtonGroup<>("Rollenzuteilung");
        radioRole.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioRole.setHelperText("Select the Role");
        List<Role> roleList = RoleService.getAllActive(jdbc);
        radioRole.setItems(roleList);
        radioRole.setValue(roleList.get(0));
        radioRole.setRenderer(
                new ComponentRenderer<>(role -> {
                    Text textRole = new Text(String.valueOf(role.getId()) + " " + role.getName());
                return new Div(textRole);
                }
            )
        );
        return radioRole;
    }


    private void addNewPerson(String lastName, String firstName, Role role, Team team, JdbcTemplate jdbc){
        if(!personAlreadyExists(firstName, lastName, jdbc)) {
            PersonaService.createNewPerson(firstName, lastName, this.jdbc);
            Notification.show(lastName + " " + firstName + " created");
        }else{
            Notification.show(lastName + " " + firstName + " already exists");
        }

        PersonaRoleTeamService.addRelation(lastName, firstName, role.getId(), team.getId(), jdbc);
        Notification.show("added Persona to Team "+ team.getId() +" with Role " + role.getId() );

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