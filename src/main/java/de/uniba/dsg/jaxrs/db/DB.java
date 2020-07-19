package de.uniba.dsg.jaxrs.db;

import de.uniba.dsg.jaxrs.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class DB{
	private static final Logger logger = Logger.getLogger("Beverage Services");
	private final List<Bottle> bottles;
	private final List<Crate> crates;
	private final List<Order> orders;

	public DB() {
		this.bottles = initBottles();
		this.crates = initCases();
		this.orders = initOrder();
	}

	private List<Bottle> initBottles() {
		return new ArrayList<>(
		      Arrays.asList(new Bottle(1, "Pils", 0.5, true, 4.8, 0.79, "Keesmann", 34), new Bottle(2, "Helles", 0.5, true, 4.9, 0.89, "Mahr", 17), new Bottle(3, "Boxbeutel", 0.75, true, 12.5, 5.79, "Divino", 11), new Bottle(4, "Tequila", 0.7, true, 40.0, 13.79, "Tequila Inc.", 5), new Bottle(5, "Gin", 0.5, true, 42.00, 11.79, "Hopfengarten", 3), new Bottle(6, "Export Edel", 0.5, true, 4.8, 0.59, "Oettinger", 66), new Bottle(7, "Premium Tafelwasser", 0.7, false, 0.0, 4.29, "Franken Brunnen", 12), new Bottle(8, "Wasser", 0.5, false, 0.0, 0.29, "Franken Brunnen", 57), new Bottle(9, "Spezi", 0.7, false, 0.0, 0.69, "Franken Brunnen", 42), new Bottle(10, "Grape Mix", 0.5, false, 0.0, 0.59, "Franken Brunnen", 12), new Bottle(11, "Still", 1.0, false, 0.0, 0.66, "Franken Brunnen", 34), new Bottle(12, "Cola", 1.5, false, 0.0, 1.79, "CCC", 69), new Bottle(13, "Cola Zero", 2.0, false, 0.0, 2.19, "CCC", 12), new Bottle(14, "Apple", 0.5, false, 0.0, 1.99, "Juice Factory", 25), new Bottle(15, "Orange", 0.5, false, 0.0, 1.99, "Juice Factory", 55), new Bottle(16, "Lime", 0.5, false, 0.0, 2.99, "Juice Factory", 8))
		);
	}

	private List<Crate> initCases() {
		return new ArrayList<>(
		      Arrays.asList(new Crate(1, this.bottles.get(0), 20, 14.99, 3), new Crate(2, this.bottles.get(1), 20, 15.99, 5), new Crate(3, this.bottles.get(2), 6, 30.00, 7), new Crate(4, this.bottles.get(7), 12, 1.99, 11), new Crate(5, this.bottles.get(8), 20, 11.99, 13), new Crate(6, this.bottles.get(11), 6, 10.99, 4), new Crate(7, this.bottles.get(12), 6, 11.99, 5), new Crate(8, this.bottles.get(13), 20, 35.00, 7), new Crate(9, this.bottles.get(14), 12, 20.00, 9))
		);
	}

	private List<Order> initOrder() {
		return new ArrayList<>(
		      Arrays.asList(new Order(1, new ArrayList<>(Arrays.asList(new OrderItem(10, this.bottles.get(3), 2), new OrderItem(20, this.crates.get(3), 1), new OrderItem(30, this.bottles.get(15), 1))), 32.56, OrderStatus.SUBMITTED), new Order(2, new ArrayList<>(Arrays.asList(new OrderItem(10, this.bottles.get(13), 2), new OrderItem(20, this.bottles.get(14), 2), new OrderItem(30, this.crates.get(0), 1))), 22.95, OrderStatus.PROCESSED), new Order(3, new ArrayList<>(Arrays.asList(new OrderItem(10, this.bottles.get(2), 1))), 5.79, OrderStatus.SUBMITTED))
		);
	}

	public List<Bottle> getBottles() {
		return this.bottles;
	}

	public Bottle getBeverageById(int bevid) {
		return this.bottles.stream().filter(b -> b.getId() == bevid).findFirst().orElse(null);
	}

	public void add(final Bottle newBottle) {
		newBottle.setId(this.bottles.stream().map(Bottle::getId).max(Comparator.naturalOrder()).orElse(0) + 1);
		this.bottles.add(newBottle);
	}

	public boolean deletebottle(int bevid) {
		final Bottle b = this.getBeverageById(bevid);
		return this.bottles.remove(b);
	}

	public Bottle updateBottle(Bottle bottle) {
		return this.bottles.set((bottle.getId() - 1), bottle);
	}

	public List<Bottle> getFilteredBottles(int maxfilter, int minfilter) {
		List<Bottle> filteredBottles = new ArrayList<>();
		for (Bottle a : this.bottles) {
			if (a.getPrice() < maxfilter && a.getPrice() > minfilter) {
				filteredBottles.add(a);
			}
		}
		return filteredBottles;
	}
	// Crates

	public List<Crate> getCrates() {
		return this.crates;
	}

	public Crate getCrateById(int crateId) {
		return this.crates.stream().filter(b -> b.getId() == crateId).findFirst().orElse(null);
	}

	public void addCrate(Crate crate) {
		crate.setId(this.crates.stream().map(Crate::getId).max(Comparator.naturalOrder()).orElse(0) + 1);
		this.crates.add(crate);
	}

	public Crate updateCrate(Crate crate) {
		return this.crates.set((crate.getId() - 1), crate);
	}
	// Orders

	public List<Order> getOrders() {
		return this.orders;
	}

	public Order getOrderbyId(int ordid) {
		return this.orders.stream().filter(b -> b.getOrderId() == ordid).findFirst().orElse(null);
	}

	public Order addOrder(Order order) {
		order.setOrderId(this.orders.stream().map(Order::getOrderId).max(Comparator.naturalOrder()).orElse(0) + 1);
		this.orders.add(order);
		logger.info("Order id in DB is : " + order.getOrderId());
		return order;
	}

	public Order updateOrder(Order order) {
		return this.orders.set((order.getOrderId() - 1), order);
	}

	public boolean deleteOrder(int ordid) {
		final Order o = this.getOrderbyId(ordid);
		return this.orders.remove(o);
	}

	public Order changeOrderStatus(Order order) {
		return this.orders.set((order.getOrderId() - 1), order);
	}
}
