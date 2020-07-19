package de.uniba.dsg.jaxrs.models.dto;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.*;

import de.uniba.dsg.jaxrs.controllers.BeverageServices;
import de.uniba.dsg.jaxrs.model.Beverage;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.model.Order;
import de.uniba.dsg.jaxrs.model.OrderItem;
import de.uniba.dsg.jaxrs.model.OrderStatus;
import de.uniba.dsg.jaxrs.resources.OrderResources;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
@XmlType(propOrder = { "orderId", "bottles", "crates", "price", "status", "href" })
public class OrderDTO{
	private static final Logger logger = Logger.getLogger("OrderDTO");
	private int orderId;
	@XmlElement(required = true)
	private double price;
	@XmlElement(required = true)
	private OrderStatus status;
	@XmlElement(required = true)
	private HashMap<BottleDTO, Integer> bottles = new HashMap<BottleDTO, Integer>();
	@XmlElement(required = true)
	private HashMap<CrateDTO, Integer> crates = new HashMap<CrateDTO, Integer>();
	private URI href;

	public OrderDTO() {
	}

	public OrderDTO(int orderId, List<OrderItem> positions, double price, OrderStatus status) {
		this.orderId = orderId;
		// this.positions = positions;
		this.price = price;
		this.status = status;
	}

	public OrderDTO(final Order o, final URI baseUri) {
		this.orderId = o.getOrderId();
		// this.positions = o.getPositions();
		this.price = o.getPrice();
		this.status = o.getStatus();
		HashMap<BottleDTO, Integer> tempbottles = new HashMap<BottleDTO, Integer>();
		HashMap<CrateDTO, Integer> tempcrates = new HashMap<CrateDTO, Integer>();
		List<OrderItem> a = o.getPositions();
		for (int i = 0; i < a.size(); i++) {
			if (o.getPositions().get(i).getBeverage() instanceof Bottle) {
				Bottle temp = new Bottle();
				temp = (Bottle) o.getPositions().get(i).getBeverage();
				BottleDTO tempBottleDTO = new BottleDTO(temp);
				tempbottles.put(tempBottleDTO, o.getPositions().get(i).getQuantity());
			} else {
				Crate temp = new Crate();
				temp = (Crate) o.getPositions().get(i).getBeverage();
				CrateDTO tempCrate = new CrateDTO(temp);
				tempcrates.put(tempCrate, o.getPositions().get(i).getQuantity());
			}
		}
		this.bottles = tempbottles;
		this.crates = tempcrates;
		this.href = UriBuilder.fromUri(baseUri).path(OrderResources.class).path(OrderResources.class, "getOrder")
		      .build(this.getOrderId());
	}

	public Order unmarshall() {
		Order order = new Order();
		order.setPrice(this.getPrice());
		order.setStatus(this.getStatus());
		List<OrderItem> positions = new ArrayList<>();
		int random = 0;
		for (Map.Entry<BottleDTO, Integer> entry : this.getBottles().entrySet()) {
			random = random + 10;
			positions.add(new OrderItem(random, new Bottle(entry.getKey()), entry.getValue()));
		}
		for (Entry<CrateDTO, Integer> entry : this.getCrates().entrySet()) {
			random = random + 10;
			Crate newCrate = new Crate();
			newCrate.setId(entry.getKey().getId());
			newCrate.setInStock(entry.getKey().getInStock());
			newCrate.setNoOfBottles(entry.getKey().getNoOfBottles());
			newCrate.setPrice(entry.getKey().getPrice());
			newCrate.setBottle(entry.getKey().getBottle());
			logger.info("Final Crate is : " + newCrate);
			positions.add(new OrderItem(random, newCrate, entry.getValue()));
		}
		order.setPositions(positions);
		return order;
	}

	public static List<OrderDTO> marshall(final List<Order> orderlist, final URI baseUri) {
		final ArrayList<OrderDTO> orders = new ArrayList<>();
		for (final Order o : orderlist) {
			orders.add(new OrderDTO(o, baseUri));
		}
		return orders;
	}

	public int getOrderId() {
		return orderId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public HashMap<BottleDTO, Integer> getBottles() {
		return bottles;
	}

	public void setBottles(HashMap<BottleDTO, Integer> bottles) {
		this.bottles = bottles;
	}

	public HashMap<CrateDTO, Integer> getCrates() {
		return crates;
	}

	public void setCrates(HashMap<CrateDTO, Integer> crates) {
		this.crates = crates;
	}

	@Override
	public String toString() {
		return "OrderDTO{" + "id=" + this.orderId + ", popularity=" + this.bottles + " Crates are " + this.crates
		      + ", price='" + this.price + '\'' + ", status=" + this.status + '}';
	}
}
