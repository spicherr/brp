package org.spicher.brp.data.service;

import org.spicher.brp.data.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CustomerService  {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Customer> findAll() {

        boolean tst = false;
        if(tst) {
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



/*

    public void update(Customer customer) {
        jdbcTemplate.update(
                "UPDATE customers SET first_name=?, last_name=? WHERE id=?",
                customer.getFirstName(), customer.getLastName(), customer.getId());
    }
    */



}
