package com.example.ordermanagementsystemapi.api.data;

import com.example.ordermanagementsystemapi.model.Customer;
import com.example.ordermanagementsystemapi.model.Order;
import com.example.ordermanagementsystemapi.model.OrderLine;
import com.example.ordermanagementsystemapi.model.Product;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestData {
    public Customer customerA;
    public Customer customerB;

    public Product productA;
    public Product productB;

    public Order orderA;
    public Order orderB;
    public Order orderC;
    public Order orderD;

    public TestData() {
        customerA = new Customer(1L, "John Guy", "john.guy@mailservice123.com",
                "125789", "sdnbuijsnghui", new ArrayList<>());
        customerB = new Customer(2L, "Peter Dog", "peter.dog@mailservice123.com",
                "32457457", "sdfnhb0wisr", new ArrayList<>());

        productA = new Product(1L, "Very Special and Popular Product", "118430823516", 5d, new ArrayList<>());
        productB = new Product(2L, "Also Special but Less Popular Product", "539175031745", 2d, new ArrayList<>());

        orderA = new Order(1L, customerA, Date.valueOf("2021-06-26"), new ArrayList<>());
        orderB = new Order(2L, customerA, Date.valueOf("2021-07-01"), new ArrayList<>());
        orderC = new Order(3L, customerA, Date.valueOf("2021-07-13"), new ArrayList<>());
        orderD = new Order(4L, customerB, Date.valueOf("2021-07-13"), new ArrayList<>());

        orderA.setOrderLines(List.of(
                new OrderLine(1L, orderA, productA, 4),
                new OrderLine(2L, orderA, productB, 1)
        ));

        orderB.setOrderLines(List.of(
                new OrderLine(3L, orderB, productA, 10)
        ));

        orderC.setOrderLines(List.of(
                new OrderLine(4L, orderC, productA, 5)
        ));

        orderD.setOrderLines(List.of(
                new OrderLine(5L, orderD, productB, 1)
        ));

        productA.setOrderLines(
                Stream.of(orderA.getOrderLines(), orderB.getOrderLines(), orderC.getOrderLines())
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())
        );

        productB.setOrderLines(
                Stream.of(orderA.getOrderLines(), orderD.getOrderLines())
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())
        );
    }
}
