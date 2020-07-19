package de.uniba.dsg.jaxrs.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import de.uniba.dsg.jaxrs.models.dto.BottleDTO;

@Produces(MediaType.TEXT_PLAIN)
public class BeverageMessageWriter implements MessageBodyWriter<BottleDTO>{
	@Override
	public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations,
	      final MediaType mediaType) {
		return type == BottleDTO.class;
	}

	@Override
	public long getSize(final BottleDTO cat, final Class<?> type, final Type genericType, final Annotation[] annotations,
	      final MediaType mediaType) {
		// deprecated by JAX-RS 2.0 and ignored by Jersey runtime
		return -1;
	}

	@Override
	public void writeTo(final BottleDTO cat, final Class<?> type, final Type genericType, final Annotation[] annotations,
	      final MediaType mediaType, final MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream)
	      throws IOException, WebApplicationException {
		entityStream.write(cat.toString().getBytes());
		entityStream.flush();
	}
}
