package de.uniba.dsg.jaxrs.controllers;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Order;
import de.uniba.dsg.jaxrs.model.OrderItem;
import de.uniba.dsg.jaxrs.model.OrderStatus;
import de.uniba.dsg.jaxrs.models.dto.OrderDTO;
import de.uniba.dsg.jaxrs.db.DB;

public class OrderServices{
	private static final Logger logger = Logger.getLogger("Beverage Services");
	public static final OrderServices instance = new OrderServices();
	private final DB database;

	public OrderServices() {
		this.database = new DB();
	}

	public List<Order> getOrders() {
		return this.database.getOrders();
	}

	public Order getOrderById(final int ordid) {
		return this.database.getOrderbyId(ordid);
	}

	public Order addOrder(Order order) {
		return this.database.addOrder(order);
	}

	public Order updateOrder(int ordid, Order updatedOrder) {
		final Order order = this.getOrderById(ordid);
		if (order == null || updatedOrder == null) {
			return null;
		}
		logger.info("Before Update " + order);
		Optional.ofNullable(updatedOrder.getPositions()).ifPresent(d -> order.setPositions(d));
		Optional.ofNullable(updatedOrder.getPrice()).ifPresent(d -> order.setPrice(d));
		logger.info("Updated Order is  " + order);
		Order o = this.database.updateOrder(order);
		return o;
	}

	public boolean deleteOrder(int ordid) {
		return this.database.deleteOrder(ordid);
	}

	public Order updateStatus(Order order) {
		order.setStatus(OrderStatus.PROCESSED);
		final Order o = this.database.changeOrderStatus(order);
		return o;
	}
}
