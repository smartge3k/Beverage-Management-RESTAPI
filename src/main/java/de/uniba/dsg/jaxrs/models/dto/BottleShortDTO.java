package de.uniba.dsg.jaxrs.models.dto;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.*;

import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.resources.BeverageResources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "beverage")
@XmlType(propOrder = { "id", "href" })
public class BottleShortDTO{
	@XmlElement(required = true)
	private int id;
	private URI href;

	public BottleShortDTO() {
	}

	public BottleShortDTO(final Bottle b) {
		this.id = b.getId();
		// this.href =
		// UriBuilder.fromUri(baseUri).path(BeverageResources.class).path(BeverageResources.class,
		// "getCrate").build(b.getId());
	}

	public BottleShortDTO(final Bottle b, final URI baseUri) {
		this.id = b.getId();
		this.href = UriBuilder.fromUri(baseUri).path(BeverageResources.class).path(BeverageResources.class, "getCrate")
		      .build(b.getId());
	}

	public static BottleShortDTO marshall(final Bottle bottle, final URI baseUri) {
		final BottleShortDTO bottledto = new BottleShortDTO(bottle, baseUri);
		return bottledto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public URI getHref() {
		return this.href;
	}

	public void setHref(final URI href) {
		this.href = href;
	}

	@Override
	public String toString() {
		return "BottleShortDTO{" + "name='" + this.id + '\'' + ", href=" + this.href + '}';
	}
}
