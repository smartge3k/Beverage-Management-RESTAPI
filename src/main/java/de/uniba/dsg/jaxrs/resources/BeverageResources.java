package de.uniba.dsg.jaxrs.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import de.uniba.dsg.jaxrs.controllers.BeverageServices;
import de.uniba.dsg.jaxrs.controllers.OrderServices;
import de.uniba.dsg.jaxrs.model.Beverage;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.model.Order;
import de.uniba.dsg.jaxrs.model.api.PaginatedBottles;
import de.uniba.dsg.jaxrs.model.api.PaginatedCrates;
import de.uniba.dsg.jaxrs.models.dto.BottleDTO;
import de.uniba.dsg.jaxrs.models.dto.BottlePostDTO;
import de.uniba.dsg.jaxrs.models.dto.CrateDTO;
import de.uniba.dsg.jaxrs.models.dto.CratePostDTO;
import de.uniba.dsg.jaxrs.models.error.ErrorMessage;
import de.uniba.dsg.jaxrs.models.error.ErrorType;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

@Path("beverages")
public class BeverageResources{
	private static final Logger logger = Logger.getLogger("Beverage Services");

	@GET
	@Path("/bottles")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBeverages(@Context final UriInfo info,
	      @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
	      @QueryParam("page") @DefaultValue("1") final int page) {
		logger.info("Get all Bottles with Pagination ");
		if (pageLimit < 1 || page < 1) {
			final ErrorMessage errorMessage = new ErrorMessage(
			      ErrorType.INVALID_PARAMETER,
			      "PageLimit or page is less than 1. Read the documentation for a proper handling!"
			);
			return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
		}
		final PaginationHelper<Bottle> helper = new PaginationHelper<>(BeverageServices.instance.getBeverages());
		final PaginatedBottles response = new PaginatedBottles(
		      helper.getPagination(info, page, pageLimit), BottleDTO.marshall(helper.getPaginatedList(), info.getBaseUri()), info.getRequestUri()
		);
		return Response.ok(response).build();
	}

	@POST
	@Path("/bottles")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createBottle(final BottlePostDTO newBottle, @Context final UriInfo uriInfo) {
		logger.info("Create Bottle " + newBottle);
		if (newBottle == null) {
			return Response.status(Response.Status.BAD_REQUEST)
			      .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
		}
		final Bottle bottle = newBottle.unmarshall();
		Bottle newlyBottle = BeverageServices.instance.addBottle(bottle);
		URI a = null;
		try {
			a = new URI("http://localhost:9999/v1/beverages");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.created(a).build();
		// return
		// Response.created(UriBuilder.fromUri(uriInfo.getBaseUri()).path(BeverageServices.class).path(BeverageServices.class,
		// "getBeverage").build(newlyBottle.getId())).build();
	}

	@GET
	@Path("/bottles/{bevid}")
	public Response getBeverage(@Context final UriInfo uriInfo, @PathParam("bevid") final int bevid) {
		logger.info("Get Beverage with Id: " + bevid);
		final Bottle m = BeverageServices.instance.getBeverageById(bevid);
		if (m == null) {
			logger.info("Beverage with Id: " + bevid + "not Found!");
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(new BottleDTO(m, uriInfo.getBaseUri())).build();
	}

	@DELETE
	@Path("/bottles/{bevid}")
	public Response deletebottle(@PathParam("bevid") final int bevid) {
		logger.info("Delete Bottle with id " + bevid);
		Bottle bottle = BeverageServices.instance.getBeverageById(bevid);
		// final boolean deleted = BeverageServices.instance.deletebottle(bevid);
		if (bottle == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			List<Crate> crates = BeverageServices.instance.getCrates();
			List<Order> orders = OrderServices.instance.getOrders();
			boolean isDeleted = BeverageServices.instance.isBottleDeletable(bottle, crates, orders);
			if (isDeleted) {
				BeverageServices.instance.deletebottle(bevid);
				return Response.ok().build();
			} else {
				logger.info("Canot Be Deleted!");
				return Response.status(Response.Status.BAD_REQUEST).entity(
				      new ErrorMessage(ErrorType.CANNOT_DELETE, "Bottle Exists in the Current Orders or Crates")
				).build();
				// here add own error
			}
		}
	}

	@PUT
	@Path("/bottles/{bevid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateBottle(@Context final UriInfo uriInfo, @PathParam("bevid") final int bevid,
	      final BottlePostDTO updatedBottle) {
		logger.info("Update Bottle:  " + updatedBottle);
		if (updatedBottle == null) {
			return Response.status(Response.Status.BAD_REQUEST)
			      .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
		}
		final Bottle cat = BeverageServices.instance.getBeverageById(bevid);
		if (cat == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		final Bottle resultCat = BeverageServices.instance.updateBottle(bevid, updatedBottle.unmarshall());
		return Response.ok().entity(new BottleDTO(resultCat, uriInfo.getBaseUri())).build();
	}

	@GET
	@Path("/bottles/search/maxfilter/{maxfilter}/minfilter/{minfilter}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchForBottles(@Context final UriInfo info,
	      @QueryParam("maxfilter") @DefaultValue("10") final int maxfilter,
	      @QueryParam("minfilter") @DefaultValue("1") final int minfilter) {
		logger.info("SEARCH: Bottles with max and min filter" + maxfilter + " " + minfilter);
		if (maxfilter < minfilter || minfilter < 0) {
			final ErrorMessage errorMessage = new ErrorMessage(
			      ErrorType.INVALID_PARAMETER, "Pleaes provide the correct fitler details"
			);
			return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
		}
		return Response.ok(
		      new GenericEntity<List<BottleDTO>>(
		            BottleDTO.marshall(BeverageServices.instance.getFilteredBottles(maxfilter, minfilter), info.getBaseUri())
		      ){
		      }
		).build();
	}

	@GET
	@Path("/crates")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCrates(@Context final UriInfo info,
	      @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
	      @QueryParam("page") @DefaultValue("1") final int page) {
		logger.info("Get all Crates with Pagination ");
		if (pageLimit < 1 || page < 1) {
			final ErrorMessage errorMessage = new ErrorMessage(
			      ErrorType.INVALID_PARAMETER,
			      "PageLimit or page is less than 1. Read the documentation for a proper handling!"
			);
			return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
		}
		final PaginationHelper<Crate> helper = new PaginationHelper<>(BeverageServices.instance.getCrates());
		final PaginatedCrates response = new PaginatedCrates(
		      helper.getPagination(info, page, pageLimit), CrateDTO.marshall(helper.getPaginatedList(), info.getBaseUri()), info.getRequestUri()
		);
		return Response.ok(response).build();
	}

	@POST
	@Path("/crates")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCrate(final CratePostDTO newCrate, @Context final UriInfo uriInfo) {
		logger.info("Create cat " + newCrate);
		if (newCrate == null) {
			return Response.status(Response.Status.BAD_REQUEST)
			      .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
		}
		final Crate crate = newCrate.unmarshall();
		// Takes the id of the bottles for which crates needed to be created
		Bottle temp = BeverageServices.instance.getBeverageById(newCrate.getBottle().getId());
		crate.setBottle(temp);
		BeverageServices.instance.addCrate(crate);
		return Response
		      .created(UriBuilder.fromUri(uriInfo.getBaseUri()).path(BeverageResources.class).path(BeverageResources.class, "getCrate").build(crate.getId())).build();
	}

	@GET
	@Path("/crates/{crateid}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response getCrate(@Context final UriInfo uriInfo, @PathParam("crateid") final int crateid) {
		logger.info("Get Crate with Id: " + crateid);
		final Crate m = BeverageServices.instance.getCrateById(crateid);
		if (m == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(new CrateDTO(m, uriInfo.getBaseUri())).build();
	}

	@PUT
	@Path("/crates/{crateId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateCrate(@Context final UriInfo uriInfo, @PathParam("crateId") final int crateId,
	      final CratePostDTO updatedCrate) {
		logger.info("Update Crate:  " + updatedCrate);
		if (updatedCrate == null) {
			return Response.status(Response.Status.BAD_REQUEST)
			      .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
		}
		final Crate crate = BeverageServices.instance.getCrateById(crateId);
		if (crate == null) {
			logger.info("The crate with ID " + crateId + " Does not Exist");
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		final Crate restulCrate = BeverageServices.instance.updateCrate(crateId, updatedCrate.unmarshall());
		return Response.ok().entity(new CrateDTO(restulCrate, uriInfo.getBaseUri())).build();
	}

	@DELETE
	@Path("/crates/{crateId}")
	public Response deleteCrates(@PathParam("crateId") final int crateId) {
		logger.info("Delete Crate with id " + crateId);
		Crate crate = BeverageServices.instance.getCrateById(crateId);
		if (crate == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			List<Order> orders = OrderServices.instance.getOrders();
			boolean isDeleted = BeverageServices.instance.isCrateDeletable(crate, orders);
			if (isDeleted) {
				BeverageServices.instance.deletebottle(crateId);
				return Response.ok().build();
			} else {
				logger.info("Canot Be Deleted!");
				return Response.status(Response.Status.BAD_REQUEST)
				      .entity(new ErrorMessage(ErrorType.CANNOT_DELETE, "Crate Exists in the Current Orders")).build();
			}
		}
	}
}
