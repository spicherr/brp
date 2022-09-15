package org.spicher.brp.views.feature;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import java.util.UUID;
import org.spicher.brp.data.entity.Features;
import org.spicher.brp.data.service.FeaturesService;
import org.spicher.brp.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Feature")
@Route(value = "feature/:featuresID?/:action?(edit)", layout = MainLayout.class)
public class FeatureView extends Div implements BeforeEnterObserver {

    private final String FEATURES_ID = "featuresID";
    private final String FEATURES_EDIT_ROUTE_TEMPLATE = "feature/%s/edit";

    private Grid<Features> grid = new Grid<>(Features.class, false);

    private TextField name;
    private TextField priority;
    private TextField value;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Features> binder;

    private Features features;

    private final FeaturesService featuresService;

    @Autowired
    public FeatureView(FeaturesService featuresService) {
        this.featuresService = featuresService;
        addClassNames("feature-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("name").setAutoWidth(true);
        grid.addColumn("priority").setAutoWidth(true);
        grid.addColumn("value").setAutoWidth(true);
        grid.setItems(query -> featuresService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(FEATURES_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(FeatureView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Features.class);

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
                if (this.features == null) {
                    this.features = new Features();
                }
                binder.writeBean(this.features);
                featuresService.update(this.features);
                clearForm();
                refreshGrid();
                Notification.show("Features details stored.");
                UI.getCurrent().navigate(FeatureView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the features details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<UUID> featuresId = event.getRouteParameters().get(FEATURES_ID).map(UUID::fromString);
        if (featuresId.isPresent()) {
            Optional<Features> featuresFromBackend = featuresService.get(featuresId.get());
            if (featuresFromBackend.isPresent()) {
                populateForm(featuresFromBackend.get());
            } else {
                Notification.show(String.format("The requested features was not found, ID = %s", featuresId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(FeatureView.class);
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
        Component[] fields = new Component[]{name, priority, value};

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

    private void populateForm(Features value) {
        this.features = value;
        binder.readBean(this.features);

    }
}
