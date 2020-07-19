package de.uniba.dsg.jaxrs.models.dto;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.*;

import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.resources.BeverageResources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bottle")
public class BottlePostDTO{
	private static final Logger logger = Logger.getLogger("Beverage Services");
	private int id;
	@XmlElement(required = true)
	private String name;
	@XmlElement(required = true)
	private double volume;
	@XmlElement(required = true)
	private boolean isAlcoholic;
	@XmlElement(required = true)
	private double volumePercent;
	@XmlElement(required = true)
	private double price;
	@XmlElement(required = true)
	private String supplier;
	@XmlElement(required = true)
	private int inStock;
	private URI href;

	public BottlePostDTO() {
	}

	public Bottle unmarshall() {
		logger.info("In the Unamrshall Add Bottle" + this.getName() + this.getInStock() + this.getPrice());
		return new Bottle(
		      0, this.name, this.volume, this.isAlcoholic, this.volumePercent, this.volumePercent, this.supplier,
		      this.inStock
		);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public boolean isAlcoholic() {
		return isAlcoholic;
	}

	public void setAlcoholic(boolean alcoholic) {
		isAlcoholic = alcoholic;
	}

	public double getVolumePercent() {
		return volumePercent;
	}

	public void setVolumePercent(double volumePercent) {
		this.volumePercent = volumePercent;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}

	@Override
	public String toString() {
		return "Bottle{" + "id=" + this.id + ", name='" + this.name + '\'' + ", volume=" + this.volume + ", isAlcoholic="
		      + this.isAlcoholic + ", volumePercent=" + this.volumePercent + ", price=" + this.price + ", supplier='"
		      + this.supplier + '\'' + ", inStock=" + this.inStock + '}';
	}
}
