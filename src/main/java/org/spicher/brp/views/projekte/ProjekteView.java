package org.spicher.brp.views.projekte;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import java.util.UUID;
import org.spicher.brp.data.entity.Projects;
import org.spicher.brp.data.service.ProjectsService;
import org.spicher.brp.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Projekte")
@Route(value = "projects/:projectsID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class ProjekteView extends Div implements BeforeEnterObserver {

    private final String PROJECTS_ID = "projectsID";
    private final String PROJECTS_EDIT_ROUTE_TEMPLATE = "projects/%s/edit";

    private Grid<Projects> grid = new Grid<>(Projects.class, false);

    private TextField name;
    private TextField priority;
    private TextField value;
    private TextField lead;
    private Checkbox activ;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Projects> binder;

    private Projects projects;

    private final ProjectsService projectsService;

    @Autowired
    public ProjekteView(ProjectsService projectsService) {
        this.projectsService = projectsService;
        addClassNames("projekte-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("name").setAutoWidth(true);
        grid.addColumn("priority").setAutoWidth(true);
        grid.addColumn("value").setAutoWidth(true);
        grid.addColumn("lead").setAutoWidth(true);
        LitRenderer<Projects> activRenderer = LitRenderer.<Projects>of(
                "<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>")
                .withProperty("icon", activ -> activ.isActiv() ? "check" : "minus").withProperty("color",
                        activ -> activ.isActiv()
                                ? "var(--lumo-primary-text-color)"
                                : "var(--lumo-disabled-text-color)");

        grid.addColumn(activRenderer).setHeader("Activ").setAutoWidth(true);

        grid.setItems(query -> projectsService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(PROJECTS_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ProjekteView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Projects.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(priority).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("priority");
        binder.forField(value).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("value");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.projects == null) {
                    this.projects = new Projects();
                }
                binder.writeBean(this.projects);
                projectsService.update(this.projects);
                clearForm();
                refreshGrid();
                Notification.show("Projects details stored.");
                UI.getCurrent().navigate(ProjekteView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the projects details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<UUID> projectsId = event.getRouteParameters().get(PROJECTS_ID).map(UUID::fromString);
        if (projectsId.isPresent()) {
            Optional<Projects> projectsFromBackend = projectsService.get(projectsId.get());
            if (projectsFromBackend.isPresent()) {
                populateForm(projectsFromBackend.get());
            } else {
                Notification.show(String.format("The requested projects was not found, ID = %s", projectsId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(ProjekteView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        name = new TextField("Name");
        priority = new TextField("Priority");
        value = new TextField("Value");
        lead = new TextField("Lead");
        activ = new Checkbox("Activ");
        Component[] fields = new Component[]{name, priority, value, lead, activ};

        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Projects value) {
        this.projects = value;
        binder.readBean(this.projects);

    }
}
