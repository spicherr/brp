package org.spicher.brp.views.project;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.spicher.brp.data.entity.Persona;
import org.spicher.brp.data.entity.Project;
import org.spicher.brp.data.service.ProjectService;
import org.spicher.brp.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

import static org.spicher.brp.FormElements.*;

@PageTitle("Projekte")
//@Route(value = "project/:projectID?/:action?(edit)", layout = MainLayout.class)
@Route(value = "projects", layout = MainLayout.class)
@Uses(Icon.class)
@Component
public class ProjectView extends VerticalLayout {

    @Autowired
    private static JdbcTemplate jdbcTemplate;
    public ProjectView(JdbcTemplate jdbc) {
        this.jdbcTemplate = jdbc;

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        add(new H1("spicher.org"));


        getForm(jdbc);

        getGrid(jdbc);

        add(new H1("end"));



    }
    private void getForm(JdbcTemplate jdbc){
        FormLayout newProject = new FormLayout();
        IntegerField id = getIntegerField("Kontierungsobjekt");
        TextField name = getTextField("Projektbezeichnung");

        RadioButtonGroup lead = getListOfPl(jdbc);
        RadioButtonGroup prio = getRadioPrioField();
        RadioButtonGroup bVal = getRadioBValField();

        Button bSave = new Button("speichern");
        bSave.addClickListener(clickEvent ->
                addNewProject(id.getValue(), name.getValue(), (Persona) lead.getValue(), String.valueOf(prio.getValue()),
                        String.valueOf(bVal.getValue()),jdbc)
        );

        Button bReset = new Button("Reset", event -> {
            id.setValue(null);
            name.setValue("");
            lead.setValue("");
            prio.setValue("");
            bVal.setValue("");
        });

        newProject.add(id);
        newProject.add(name);
        newProject.add(lead);
        newProject.add(prio);
        newProject.add(bVal);
        newProject.add(bSave);
        newProject.add(bReset);
        add(newProject);


    }

    private void addNewProject(int id, String name, Persona pl, String prio, String bVal, JdbcTemplate jdbc) {
        if(!ProjectService.projectExists(id, jdbc)) {
            ProjectService.createProject(id, name, pl.getId(), prio, bVal, jdbc);

            Notification.show("Projekt wurde angelegt");
        }
        else{
            Notification.show("Projekt existiert bereits");
        }
        Notification.show(id+", "+ name+", "+ pl.getFirstName()+", "+ prio+", "+bVal);
    }

    private void getGrid(JdbcTemplate jdbc) {
        List<Project> projects = ProjectService.findAll(jdbc);

        Grid<Project> grid = new Grid<>(Project.class, false);


        grid.addColumn(Project::getId).setHeader("ID");
        grid.addColumn(Project::getName).setHeader("Projekt");
        grid.addColumn(Project::getLead).setHeader("Verantwortlicher PL");
        grid.addColumn(Project::getPriority).setHeader("Priorität");
        grid.addColumn(Project::getBusinessValue).setHeader("Business Value");
        grid.setItems(projects);
        grid.getDataProvider().refreshAll();
        add(grid);
    }


}