package de.uniba.dsg.jaxrs.models.dto;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.*;

import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.resources.BeverageResources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "crate")
public class CratePostDTO{
	private static final Logger logger = Logger.getLogger("Beverage Services");
	private int id;
	@XmlElement(required = true)
	private Bottle bottle;
	@XmlElement(required = true)
	private int noOfBottles;
	@XmlElement(required = true)
	private double price;
	@XmlElement(required = true)
	private int inStock;
	private URI href;

	public CratePostDTO() {
	}

	public Crate unmarshall() {
		Crate crate = new Crate();
		crate.setId(this.getId());
		crate.setBottle(this.bottle);
		crate.setInStock(this.inStock);
		crate.setNoOfBottles(this.noOfBottles);
		crate.setPrice(this.price);
		return crate;
	}

	public int getId() {
		return id;
	}

	public Bottle getBottle() {
		return bottle;
	}

	public void setBottle(Bottle bottle) {
		this.bottle = bottle;
	}

	public int getNoOfBottles() {
		return noOfBottles;
	}

	public void setNoOfBottles(int noOfBottles) {
		this.noOfBottles = noOfBottles;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}

	@Override
	public String toString() {
		return "Crate{" + "id=" + id + ", bottle=" + bottle + ", noOfBottles=" + noOfBottles + ", price=" + price
		      + ", inStock=" + inStock + '}';
	}
}
