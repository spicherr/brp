package org.spicher.brp.views.customer;


import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.spicher.brp.data.entity.Customer;
import org.spicher.brp.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.Arrays;
import java.util.List;

@PageTitle("Customer")
@Route(value = "customer", layout = MainLayout.class)
@Uses(Icon.class)
public class CustomerView extends VerticalLayout {
    private final boolean test = true;
    public CustomerView() {

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);
        add(new H1("spicher.org"));

        List<Customer> customers;
        if(test==true) {
            customers = findAll(test);
        }
        else {

            customers = Arrays.asList(
                    new Customer(1, "a", "b"),
                    new Customer(2, "aa", "bb"),
                    new Customer(3, "aaa", "bbb")

            );
        }
        Grid<Customer> grid = new Grid<>(Customer.class, false);
// Create a grid bound to the list

        grid.addColumn(Customer::getId).setHeader("ID");
        grid.addColumn(Customer::getFirstName).setHeader("Vorname");
        grid.addColumn(Customer::getLastName).setHeader("Name");

        grid.setItems(customers);
        add(grid);

        add(new H1("end"));



    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Customer> findAll(boolean test) {
        if(test) {
            String sql = "SELECT * FROM CUSTOMERS";
            return jdbcTemplate.query(
                    sql,
                    (rs, rowNum) ->
                            new Customer(
                                    rs.getInt("id"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name")
                            )
            );
        }
        else {
            return Arrays.asList(new Customer(99, "WRONG", "WRONG"));
        }
    }

}