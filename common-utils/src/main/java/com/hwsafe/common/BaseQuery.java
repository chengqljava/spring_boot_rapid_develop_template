package com.hwsafe.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseQuery implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    private List<Order> orders;

    protected BaseQuery() {
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Direction direction, String property) {
        if (this.orders == null)
            this.orders = new ArrayList<Order>();
        this.orders.add(new Order(direction, property));
    }

    public static class Order implements Serializable {

        /**  */
        private static final long serialVersionUID = -9198827841627211006L;

        private final Direction direction;
        private final String property;

        public Order(Direction direction, String property) {
            this.direction = direction;
            this.property = property;
        }

        public Order(String property) {
            this(Direction.ASC, property);
        }

        public Direction getDirection() {
            return direction;
        }

        public String getProperty() {
            return property;
        }

    }

    public static enum Direction {
        ASC, DESC;
    }
}
