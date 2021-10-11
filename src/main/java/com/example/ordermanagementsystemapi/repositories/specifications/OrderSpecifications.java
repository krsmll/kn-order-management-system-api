package com.example.ordermanagementsystemapi.repositories.specifications;

import com.example.ordermanagementsystemapi.model.Order;
import com.example.ordermanagementsystemapi.model.OrderLine;
import com.example.ordermanagementsystemapi.model.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.Date;

public class OrderSpecifications {
    public static Specification<Order> ordersByProduct(Product product) {
        return (root, query, cb) -> {
            Join<Order, OrderLine> orderLines = root.join("orderLines");
            query.orderBy(cb.desc(root.get("id")));

            return cb.equal(orderLines.get("product").get("id"), product.getId());
        };
    }

    public static Specification<Order> ordersByDate(Date date) {
        return (root, query, cb) -> cb.equal(root.get("submissionDate"), date);
    }
}
