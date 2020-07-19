package de.uniba.dsg.jaxrs.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;

import de.uniba.dsg.jaxrs.controllers.BeverageServices;
import de.uniba.dsg.jaxrs.controllers.OrderServices;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.model.Order;
import de.uniba.dsg.jaxrs.model.OrderStatus;
import de.uniba.dsg.jaxrs.models.dto.BottleDTO;
import de.uniba.dsg.jaxrs.models.dto.CrateDTO;
import de.uniba.dsg.jaxrs.models.dto.CratePostDTO;
import de.uniba.dsg.jaxrs.models.dto.OrderDTO;
import de.uniba.dsg.jaxrs.models.dto.OrderPostDTO;
import de.uniba.dsg.jaxrs.models.dto.customHasmapDTO;
import de.uniba.dsg.jaxrs.models.error.ErrorMessage;
import de.uniba.dsg.jaxrs.models.error.ErrorType;

@Path("orders")
public class OrderResources{
	private static final Logger logger = Logger.getLogger("Order Services");

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getOrders(@Context final UriInfo uriInfo) {
		logger.info("Get all Orders");
		final GenericEntity<List<OrderDTO>> entity = new GenericEntity<List<OrderDTO>>(
		      OrderDTO.marshall(OrderServices.instance.getOrders(), uriInfo.getBaseUri())
		){
		};
		final Response build = Response.ok(entity).build();
		return build;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOrder(final OrderPostDTO newOrder, @Context final UriInfo uriInfo) {
		logger.info("Create Order: " + newOrder);
		if (newOrder == null) {
			return Response.status(Response.Status.BAD_REQUEST)
			      .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
		}
		OrderDTO temp = new OrderDTO();
		temp = newOrder.ConvertIntoOrderDTO();
		if (temp == null) {
			return Response.status(Response.Status.BAD_REQUEST)
			      .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Invalid Parameters")).build();
		}
		final Order order = temp.unmarshall();
		logger.info("Final Order is  " + order.toString());
		Order submitted = OrderServices.instance.addOrder(order);
		URI a = null;
		try {
			a = new URI("http://localhost:9999/v1/orders");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.created(a).build();
		// return
		// Response.created(UriBuilder.fromUri(uriInfo.getBaseUri()).path(OrderServices.class).path(OrderServices.class,
		// "getOrder").build(submitted.getOrderId())).build();
	}

	@PUT
	@Path("{ordid}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response ChangeOrder(@Context final UriInfo uriInfo, @PathParam("ordid") final int ordid,
	      final OrderPostDTO Updatedorder) {
		logger.info("Change Order with Id: " + ordid);
		final Order o = OrderServices.instance.getOrderById(ordid);
		if (o == null) {
			logger.info("Cannot find an order " + ordid);
			return Response.status(Response.Status.NOT_FOUND).build();
		} else if (o.getStatus() == OrderStatus.PROCESSED) {
			return Response.status(Response.Status.BAD_REQUEST).entity(
			      new ErrorMessage(ErrorType.ORDER_ALREADY_PROCESSED, "Order Has Already Been Proccessed")
			).build();
		}
		final Order order = OrderServices.instance.updateOrder(ordid, (Updatedorder.ConvertIntoOrderDTO()).unmarshall());
		return Response.ok(new OrderDTO(o, uriInfo.getBaseUri())).build();
	}

	@DELETE
	@Path("{ordid}")
	public Response cancelOrder(@PathParam("ordid") final int ordid) {
		logger.info("Delete Order with id " + ordid);
		Order order = OrderServices.instance.getOrderById(ordid);
		if (order.getStatus() == OrderStatus.PROCESSED) {
			return Response.status(Response.Status.BAD_REQUEST).entity(
			      new ErrorMessage(ErrorType.ORDER_ALREADY_PROCESSED, "Order Has Already Been Proccessed")
			).build();
		}
		final boolean deleted = OrderServices.instance.deleteOrder(ordid);
		if (!deleted) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			return Response.ok().build();
		}
	}

	@GET
	@Path("{ordid}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response getOrder(@Context final UriInfo uriInfo, @PathParam("ordid") final int ordid) {
		logger.info("Get Order with Id: " + ordid);
		final Order o = OrderServices.instance.getOrderById(ordid);
		if (o == null) {
			logger.info("Cannot find an order " + ordid);
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(new OrderDTO(o, uriInfo.getBaseUri())).build();
	}

	@GET
	@Path("/status/{ordid}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response getOrderStatus(@Context final UriInfo uriInfo, @PathParam("ordid") final int ordid) {
		logger.info("Get Order Status with Id: " + ordid);
		final Order o = OrderServices.instance.getOrderById(ordid);
		if (o == null) {
			logger.info("Cannot find an order " + ordid);
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(o.getStatus()).build();
	}

	@PUT
	@Path("/status/{ordid}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response ChangeOrderStatus(@Context final UriInfo uriInfo, @PathParam("ordid") final int ordid) {
		logger.info("Get Order Status with Id: " + ordid);
		final Order o = OrderServices.instance.getOrderById(ordid);
		if (o == null) {
			logger.info("Cannot find an order " + ordid);
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		final Order order = OrderServices.instance.updateStatus(o);
		return Response.ok(new OrderDTO(order, uriInfo.getBaseUri())).build();
	}
}
