package de.uniba.dsg.jaxrs.models.dto;

public class customHasmapDTO{
	private int id;
	private int quantity;

	public customHasmapDTO() {
	}

	public customHasmapDTO(int id, int quantity) {
		this.id = id;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String toString() {
		return "{" + this.id + this.quantity + "}";
	}
}
