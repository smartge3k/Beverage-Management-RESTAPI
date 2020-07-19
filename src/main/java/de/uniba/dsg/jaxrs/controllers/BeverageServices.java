package de.uniba.dsg.jaxrs.controllers;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlElement;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Beverage;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.model.Order;
import de.uniba.dsg.jaxrs.model.OrderItem;

public class BeverageServices{
	private static final Logger logger = Logger.getLogger("Beverage Services");
	public static final BeverageServices instance = new BeverageServices();
	private final DB database;

	public BeverageServices() {
		this.database = new DB();
	}

	public List<Bottle> getBeverages() {
		return this.database.getBottles();
	}

	public Bottle getBeverageById(int bevid) {
		return this.database.getBeverageById(bevid);
	}

	public Bottle addBottle(final Bottle newBottle) {
		if (newBottle == null) {
			return null;
		}
		this.database.add(newBottle);
		return newBottle;
	}

	public boolean deletebottle(int bevid) {
		return this.database.deletebottle(bevid);
	}

	// Crates Implementation
	public List<Crate> getCrates() {
		logger.info("At the Resources");
		return this.database.getCrates();
	}

	public Crate getCrateById(int CrateId) {
		return this.database.getCrateById(CrateId);
	}

	public void addCrate(Crate crate) {
		this.database.addCrate(crate);
	}

	public Bottle updateBottle(int bevid, final Bottle updatedBottle) {
		final Bottle bottle = this.getBeverageById(bevid);
		if (bottle == null || updatedBottle == null) {
			return null;
		}
		logger.info("Before Update " + bottle);
		if (updatedBottle.getName() != "string") {
			bottle.setName(updatedBottle.getName());
		}
		if (updatedBottle.getSupplier() != "string") {
			bottle.setSupplier(updatedBottle.getSupplier());
		}
		Optional.ofNullable(updatedBottle.getVolume()).ifPresent(d -> bottle.setVolume(d));
		Optional.ofNullable(updatedBottle.isAlcoholic()).ifPresent(d -> bottle.setAlcoholic(d));
		Optional.ofNullable(updatedBottle.getVolumePercent()).ifPresent(d -> bottle.setVolumePercent(d));
		Optional.ofNullable(updatedBottle.getPrice()).ifPresent(d -> bottle.setPrice(d));
		;
		Optional.ofNullable(updatedBottle.getInStock()).ifPresent(d -> bottle.setInStock(d));
		logger.info("Update Bottle is  " + bottle);
		Bottle a = this.database.updateBottle(bottle);
		return a;
	}

	public List<Bottle> getFilteredBottles(int maxfilter, int minfilter) {
		// TODO Auto-generated method stub
		return this.database.getFilteredBottles(maxfilter, minfilter);
	}

	public Crate updateCrate(int crateId, Crate updatedCrate) {
		final Crate crate = this.getCrateById(crateId);
		if (crate == null || updatedCrate == null) {
			logger.info("Crate with id: " + crateId + " Does not Exist");
			return null;
		}
		logger.info("Before Update " + crate);
		Bottle insideCrate = this.getBeverageById(updatedCrate.getBottle().getId());
		crate.setBottle(insideCrate);
		Optional.ofNullable(updatedCrate.getNoOfBottles()).ifPresent(d -> crate.setNoOfBottles(d));
		Optional.ofNullable(updatedCrate.getPrice()).ifPresent(d -> crate.setPrice(d));
		Optional.ofNullable(updatedCrate.getInStock()).ifPresent(d -> crate.setInStock(d));
		logger.info("Updated Crate is  " + crate);
		Crate a = this.database.updateCrate(crate);
		return crate;
	}

	public boolean isBottleDeletable(Bottle bottle, List<Crate> crates, List<Order> orders) {
		boolean delateable = true;
		for (Crate crate : crates) {
			if (crate.getBottle().getId() == bottle.getId())
				delateable = false;
		}
		for (Order order : orders) {
			for (OrderItem bvg : order.getPositions()) {
				if (bvg.getBeverage() instanceof Bottle) {
					Bottle checkBottle = (Bottle) bvg.getBeverage();
					if (checkBottle.getId() == bottle.getId()) {
						delateable = false;
					}
				}
			}
		}
		return delateable;
	}

	public boolean isCrateDeletable(Crate crate, List<Order> orders) {
		boolean deleteable = true;
		for (Order order : orders) {
			for (OrderItem bvg : order.getPositions()) {
				if (bvg.getBeverage() instanceof Crate) {
					Crate checkCrate = (Crate) bvg.getBeverage();
					if (checkCrate.getId() == crate.getId()) {
						deleteable = false;
					}
				}
			}
		}
		return deleteable;
	}
}
