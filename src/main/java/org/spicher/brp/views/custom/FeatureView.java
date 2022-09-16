package org.spicher.brp.views.custom;


import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.spicher.brp.data.entity.Feature;
import org.spicher.brp.views.MainLayout;

import java.util.Arrays;
import java.util.List;

@PageTitle("Features")
@Route(value = "feature/:featureID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class FeatureView extends VerticalLayout {
    public FeatureView() {

        List<Feature> features = Arrays.asList(
                new Feature(1,"VKM","XY","A","L", 0,2,5,2,0),
                new Feature(1,"PA19","XX","A","L", 0,2,5,2,0)
                );

        Grid<Feature> grid = new Grid<>(Feature.class, false);

        grid.setItems(features);
        grid.addColumn(Feature::getId).setHeader("ID");
        grid.addColumn(Feature::getFeature).setHeader("Feature");
        grid.addColumn(Feature::getProject).setHeader("Projekt");
        grid.addColumn(Feature::getPriority).setHeader("Priorität");
        grid.addColumn(Feature::getValue).setHeader("Business Value");
        grid.addColumn(Feature::getAt).setHeader("Architekt");
        grid.addColumn(Feature::getBa).setHeader("Business Analyst");
        grid.addColumn(Feature::getDev).setHeader("Entwicklung");
        grid.addColumn(Feature::getTm).setHeader("Testmanager");
        grid.addColumn(Feature::getDiv).setHeader("Diverse");

        add(grid);

        add(new H1("end"));



    }

}