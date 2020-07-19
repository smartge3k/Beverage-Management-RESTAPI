package de.uniba.dsg.jaxrs;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import de.uniba.dsg.jaxrs.provider.BeverageMessageWriter;
import de.uniba.dsg.jaxrs.resources.BeverageResources;
import de.uniba.dsg.jaxrs.resources.OrderResources;
import de.uniba.dsg.jaxrs.resources.SwaggerUI;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExamplesApi extends Application{
	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> resources = new HashSet<>();
		resources.add(BeverageResources.class);
		resources.add(OrderResources.class);
		resources.add(SwaggerUI.class);
		// resources.add(BeverageMessageWriter.class);
		return resources;
	}
}
