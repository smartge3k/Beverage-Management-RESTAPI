package de.uniba.dsg.jaxrs.models.dto;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.*;

import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.resources.BeverageResources;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "create")
@XmlType(propOrder = { "id", "bottle", "noOfBottles", "price", "inStock", "href" })
public class CrateDTO{
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

	public CrateDTO() {
	}

	public CrateDTO(Crate c) {
		this.id = c.getId();
		this.bottle = c.getBottle();
		this.noOfBottles = c.getNoOfBottles();
		this.price = c.getPrice();
		this.inStock = c.getInStock();
	}

	public CrateDTO(final Crate c, final URI baseUri) {
		this.id = c.getId();
		this.bottle = c.getBottle(); // BottleShortDTO.marshall(c.getBottle(),baseUri);
		this.noOfBottles = c.getNoOfBottles();
		this.price = c.getPrice();
		this.inStock = c.getInStock();
		this.href = UriBuilder.fromUri(baseUri).path(BeverageResources.class).path(BeverageResources.class, "getCrate")
		      .build(this.id);
	}

	public static List<CrateDTO> marshall(final List<Crate> CrateList, final URI baseUri) {
		final ArrayList<CrateDTO> Crates = new ArrayList<>();
		for (final Crate m : CrateList) {
			Crates.add(new CrateDTO(m, baseUri));
		}
		return Crates;
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
