package org.spicher.brp.views.custom;


import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.spicher.brp.data.entity.Customer;
import org.spicher.brp.data.service.CustomerService;
import org.spicher.brp.views.MainLayout;


import java.util.Arrays;
import java.util.List;

@PageTitle("Customer")
@Route(value = "customer/:customerID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class CustomerView extends VerticalLayout {
  //  private CustomerService service;
    public CustomerView() {

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        add(new H1("spicher.org"));

//        List<Customer> customers = service.findAll();

        List<Customer> customers = Arrays.asList(
                new Customer(1,"Reto","Spicher"),
                new Customer(2,"Andrea","Lüthi"),
                new Customer(3,"Emily","Lüthi"));

        Grid<Customer> grid = new Grid<>(Customer.class, false);
// Create a grid bound to the list

        grid.setItems(customers);
        grid.addColumn(Customer::getId).setHeader("ID");
        grid.addColumn(Customer::getFirstName).setHeader("Vorname");
        grid.addColumn(Customer::getLastName).setHeader("Name");
        //VerticalLayout layout = new VerticalLayout(grid);
        add(grid);

        add(new H1("end"));



    }

}