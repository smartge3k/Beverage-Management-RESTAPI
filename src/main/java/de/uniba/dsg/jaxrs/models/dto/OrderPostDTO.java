package de.uniba.dsg.jaxrs.models.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.annotation.*;
import javax.ws.rs.core.Response;
import de.uniba.dsg.jaxrs.controllers.BeverageServices;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.model.Order;
import de.uniba.dsg.jaxrs.model.OrderItem;
import de.uniba.dsg.jaxrs.model.OrderStatus;
import de.uniba.dsg.jaxrs.models.error.ErrorMessage;
import de.uniba.dsg.jaxrs.models.error.ErrorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
@XmlType(propOrder = { "orderId", "bottles", "crates" })
public class OrderPostDTO{
	private static final Logger logger = Logger.getLogger("Order Services");
	private int orderId;
	// @XmlElement(required = true)
	// private double price;
	// @XmlElement(required = true)
	// OrderStatus status;
	public static final BeverageServices instance = new BeverageServices();
	@XmlElement(required = true)
	List<customHasmapDTO> bottles = new ArrayList<>();
	@XmlElement(required = true)
	List<customHasmapDTO> crates = new ArrayList<>();

	public OrderPostDTO() {
	}

	public OrderDTO ConvertIntoOrderDTO() {
		OrderDTO temp = new OrderDTO();
		HashMap<BottleDTO, Integer> tempbottles = new HashMap<BottleDTO, Integer>();
		HashMap<CrateDTO, Integer> tempcrates = new HashMap<CrateDTO, Integer>();
		double tempprice = 0.0;
		// Get List of Bottles User Has Sent for Order
		for (customHasmapDTO i : this.getBottles()) {
			Bottle finalbottle = new Bottle();
			logger.info("The ID is " + i.getId());
			finalbottle = BeverageServices.instance.getBeverageById(i.getId());
			if (finalbottle != null) {
				if (finalbottle.getInStock() >= i.getQuantity()) {
					tempbottles.put(new BottleDTO(finalbottle), i.getQuantity());
					tempprice = tempprice + (finalbottle.getPrice()) * i.getQuantity();
				} else {
					logger.info("Bottle With ID: " + i.getId() + " Does not have enough stock ");
					return null;
				}
				// Update the quantity of bottles in the Database
				finalbottle.setInStock((finalbottle.getInStock()) - i.getQuantity());
				BeverageServices.instance.updateBottle(i.getId(), finalbottle);
			} else {
				logger.info("Bottle With ID: " + i.getId() + " Does not Exist ");
				return null;
			}
		}
		// Get List of Crates User has Sent for Order
		for (customHasmapDTO i : this.getCrates()) {
			Crate crate = new Crate();
			crate = BeverageServices.instance.getCrateById(i.getId());
			if (crate != null) {
				if (crate.getInStock() >= i.getQuantity()) {
					tempcrates.put(new CrateDTO(crate), i.getQuantity());
					tempprice = tempprice + (crate.getPrice()) * i.getQuantity();
				} else {
					logger.info("Crate With ID: " + i.getId() + " does not have enough stock ");
					return null;
				}
				// Update the quantity of Crate in the Database
				crate.setInStock((crate.getInStock()) - i.getQuantity());
				BeverageServices.instance.updateCrate(i.getId(), crate);
			} else {
				logger.info("Crate With ID: " + i.getId() + " Does not Exist ");
				return null;
			}
		}
		// Finalize the Order
		temp.setBottles(tempbottles);
		temp.setCrates(tempcrates);
		temp.setPrice(tempprice);
		temp.setStatus(OrderStatus.SUBMITTED);
		return temp;
	}

	public int getOrderId() {
		return orderId;
	}

	public List<customHasmapDTO> getBottles() {
		return bottles;
	}

	public void setBottles(List<customHasmapDTO> bottles) {
		this.bottles = bottles;
	}

	public List<customHasmapDTO> getCrates() {
		return crates;
	}

	public void setCrates(List<customHasmapDTO> crates) {
		this.crates = crates;
	}

	@Override
	public String toString() {
		return "OrderPostDTO{" + "id=" + this.orderId + ", Bottles :" + this.getBottles().toString() + ", Crates "
		      + this.getCrates().toString() + '}';
	}
}
