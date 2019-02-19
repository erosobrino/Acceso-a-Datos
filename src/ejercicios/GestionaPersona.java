package ejercicios;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/personas")
public class GestionaPersona {

	Persona perso;

	static ArrayList<Persona> personas = new ArrayList<>();

	@GET
	@Path("galego")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ArrayList<Persona> galego() {
		return personas;
	}

	// Ejer 10 Descomentar xmlattribute en persona y al añadir en xml se pone
	// <persona id="num">
	// En xml aparece el id arriba, al lado de persona mientras que en json aparece
	// como siempre
	@GET
	@Path("xml/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Persona personaConIdAtributo(@PathParam("id") int id) {
		for (Persona persona : personas) {
			if (persona.getId() == id) {
				return persona;
			}
		}
		return null;
	}

	// Ejer 8
	@GET
	@Path("borra/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Persona eliminaPersona(@PathParam("id") int id) {
		for (Persona persona : personas) {
			if (persona.getId() == id) {
				personas.remove(persona);
				return persona;
			}
		}
		return null;
	}

	// Ejer 7
	@POST
	@Path("add")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response guardaVariasPersonas(ArrayList<Persona> personas) {
		try {
			for (Persona persona : personas) {
				this.personas.add(persona);
			}
			return Response.ok(personas).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity("Array nulo").type(MediaType.TEXT_PLAIN).build();
		}
	}

	// Ejer 5 6
	@POST
	@Path("formulario")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response formPersona(@FormParam("nombre") String nombre, @FormParam("sexo") String sexo,
			@FormParam("casado") boolean casado, @FormParam("id") int id) {

		try {
			perso = new Persona();
			perso.setNombre(nombre);
			perso.setSexo(sexo);
			perso.setCasado(casado);
			perso.setId(id);
			personas.add(perso);
			return Response.ok(perso).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity("Error al añadir a la persona").type(MediaType.TEXT_PLAIN)
					.build();
		}
	}

	// Ejer3 4 9
	@GET
//	@Path("ver/{nombre}")
	@Path("ver")
	@Produces({ MediaType.APPLICATION_JSON })
//	public Persona ver(@PathParam("nombre") String nombre) {
//	public Persona ver(@QueryParam("nombre") String nombre) {
	public Persona ver(@DefaultValue("ero") @QueryParam("nombre") String nombre) {
		for (int i = 0; i < personas.size(); i++) {
			if (personas.get(i).getNombre().toUpperCase().equals(nombre.toUpperCase())) {
				return personas.get(i);
			}
		}
		return null;
	}

	// Ejer2
	@GET
	@Path("/listar")
	@Produces({ MediaType.APPLICATION_XML })
	public ArrayList<Persona> listar() {
		return this.personas;
	}

	// Ejer1
	@POST
	@Path("guardar")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response guardar(Persona perso) {
		try {
			personas.add(perso);
			return Response.ok(perso).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity("Error al añadir a la persona").type(MediaType.TEXT_PLAIN)
					.build();
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ArrayList<Persona> getXML() {
		perso = new Persona();
		perso.setId(0);
		perso.setNombre("Ero");
		perso.setCasado(false);
		perso.setSexo("hombre");
		personas.add(perso);
		return this.personas;
	}

}
