package de.uniba.dsg.jaxrs.models.dto;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.*;

import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.resources.BeverageResources;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bottle")
@XmlType(propOrder = { "id", "name", "volume", "isAlcoholic", "volumePercent", "price", "supplier", "inStock", "href" })
public class BottleDTO{
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

	public BottleDTO() {
	}

	public BottleDTO(Bottle b) {
		this.id = b.getId();
		this.name = b.getName();
		this.volume = b.getVolume();
		this.isAlcoholic = b.isAlcoholic();
		this.volumePercent = b.getVolumePercent();
		this.price = b.getPrice();
		this.supplier = b.getSupplier();
		this.inStock = b.getInStock();
	}

	public BottleDTO(final Bottle b, final URI baseUri) {
		this.id = b.getId();
		this.name = b.getName();
		this.volume = b.getVolume();
		this.isAlcoholic = b.isAlcoholic();
		this.volumePercent = b.getVolumePercent();
		this.price = b.getPrice();
		this.supplier = b.getSupplier();
		this.inStock = b.getInStock();
		this.href = UriBuilder.fromUri(baseUri).path(BeverageResources.class).path(BeverageResources.class, "getBeverage")
		      .build(this.id);
	}

	public static List<BottleDTO> marshall(final List<Bottle> BottleList, final URI baseUri) {
		final ArrayList<BottleDTO> Bottles = new ArrayList<>();
		for (final Bottle m : BottleList) {
			Bottles.add(new BottleDTO(m, baseUri));
		}
		return Bottles;
	}

	public static BottleDTO marshall(final Bottle bottle, final URI baseUri) {
		final BottleDTO a = new BottleDTO(bottle);
		return a;
	}

	public void setId(int id) {
		this.id = id;
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

	public URI getHref() {
		return this.href;
	}

	public void setHref(final URI href) {
		this.href = href;
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
