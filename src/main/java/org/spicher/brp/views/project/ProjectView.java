package org.spicher.brp.views.project;


import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.spicher.brp.data.entity.Project;
import org.spicher.brp.views.MainLayout;


import java.util.Arrays;
import java.util.List;

@PageTitle("Projekte")
//@Route(value = "project/:projectID?/:action?(edit)", layout = MainLayout.class)
@Route(value = "projects", layout = MainLayout.class)
@Uses(Icon.class)
public class ProjectView extends VerticalLayout {
    public ProjectView() {

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        add(new H1("spicher.org"));

        List<Project> projects = Arrays.asList(
                new Project(1,"VKM","Anita Hitz","A","L", true),
                new Project(1,"PA19","Anita Hitz","A","L", false)
                );

        Grid<Project> grid = new Grid<>(Project.class, false);

        grid.setItems(projects);
        grid.addColumn(Project::getId).setHeader("ID");
        grid.addColumn(Project::getProject).setHeader("Projekt");
        grid.addColumn(Project::getLead).setHeader("Verantwortlicher PL");
        grid.addColumn(Project::getPriority).setHeader("Priorität");
        grid.addColumn(Project::getValue).setHeader("Business Value");
        grid.addColumn(Project::isActive).setHeader("Aktiv");

        add(grid);

        add(new H1("end"));



    }

}