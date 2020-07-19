package de.uniba.dsg.jaxrs.model;

import de.uniba.dsg.jaxrs.models.dto.BottleDTO;

public class Bottle implements Beverage{
	private int id;
	private String name;
	private double volume;
	private boolean isAlcoholic;
	private double volumePercent;
	private double price;
	private String supplier;
	private int inStock;

	public Bottle(BottleDTO b) {
		this.id = b.getId();
		this.name = b.getName();
		this.volume = b.getVolume();
		this.isAlcoholic = b.isAlcoholic();
		this.volumePercent = b.getVolumePercent();
		this.price = b.getPrice();
		this.supplier = b.getSupplier();
		this.inStock = b.getInStock();
	}

	public Bottle(int id, String name, double volume, boolean isAlcoholic, double volumePercent, double price,
	      String supplier, int inStock) {
		this.id = id;
		this.name = name;
		this.volume = volume;
		this.isAlcoholic = isAlcoholic;
		this.volumePercent = volumePercent;
		this.price = price;
		this.supplier = supplier;
		this.inStock = inStock;
	}

	public Bottle() {
		// TODO Auto-generated constructor stub
	}

	public void setId(int i) {
		this.id = i;
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
		return "Bottle{" + "id=" + id + ", name='" + name + '\'' + ", volume=" + volume + ", isAlcoholic=" + isAlcoholic
		      + ", volumePercent=" + volumePercent + ", price=" + price + ", supplier='" + supplier + '\'' + ", inStock="
		      + inStock + '}';
	}
}