package org.spicher.brp.views.persona;


import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.spicher.brp.data.entity.Persona;
import org.spicher.brp.data.service.PersonaService;
import org.spicher.brp.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@PageTitle("Persona")
@Route(value = "persona", layout = MainLayout.class)
@Uses(Icon.class)
@Component
public class PersonaView extends VerticalLayout {
    @Autowired
    private JdbcTemplate jdbcPersona;
    public PersonaView(JdbcTemplate jdbcPersona) {
        this.jdbcPersona = jdbcPersona;

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);
        add(new H1("spicher.org"));

        List<Persona> personas = PersonaService.findAll(jdbcPersona);

        Grid<Persona> grid = new Grid<>(Persona.class, false);
// Create a grid bound to the list

        grid.addColumn(Persona::getId).setHeader("ID");
        grid.addColumn(Persona::getFirstName).setHeader("Vorname");
        grid.addColumn(Persona::getLastName).setHeader("Name");
        grid.addColumn(Persona::getRoleId).setHeader("Rolle");
        grid.addColumn(Persona::getTeamId).setHeader("Team");

        grid.setItems(personas);
        add(grid);

        add(new H1("end"));



    }


}