package de.uniba.dsg.jaxrs.model.api;

import java.net.URI;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.uniba.dsg.jaxrs.models.dto.BottleDTO;

@XmlRootElement
@XmlType(propOrder = { "pagination", "bottles", "href" })
public class PaginatedBottles{
	private Pagination pagination;
	private List<BottleDTO> bottles;
	private URI href;

	public PaginatedBottles() {
	}

	public PaginatedBottles(final Pagination pagination, final List<BottleDTO> bottles, final URI href) {
		this.pagination = pagination;
		this.bottles = bottles;
		this.href = href;
	}

	public Pagination getPagination() {
		return this.pagination;
	}

	public void setPagination(final Pagination pagination) {
		this.pagination = pagination;
	}

	public List<BottleDTO> getBottles() {
		return this.bottles;
	}

	public void setBottles(final List<BottleDTO> cats) {
		this.bottles = cats;
	}

	public URI getHref() {
		return this.href;
	}

	public void setHref(final URI href) {
		this.href = href;
	}
}
