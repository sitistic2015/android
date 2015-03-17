package rest;


import dao.GeoInterventionZoneDAO;
import entity.GeoInterventionZone;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by arno on 12/02/15.
 */
@Path("/drones")
public class Drones {

    @GET
    //@Path("")
    //  @Consumes({ MediaType.APPLICATION_JSON })
    public Response getDrones(String drones) {

        return Response.status(200).entity("Les drones sont : " + drones).build();

    }

    @POST
    @Path("/initializedrone")
    //  @Consumes({ MediaType.APPLICATION_JSON })
    public Response initializeDrone(GeoDrone drone) {
        GeoDroneDAO gDD = new GeoDroneDAO();
        gDD.connect();
        GeoDroneDAO res= gDD.create(drone);
        gIZD.disconnect();
        return Response.status(200).entity("Le drone est initialisé").build();

    }

    @GET
    @Path("{id}")
    //  @Consumes({ MediaType.APPLICATION_JSON })
    public Response getDroneById(@PathParam("id") int id) {
        GeoDroneDAO gDD = new GeoDroneDAO();
        gDD.connect();
        GeoDroneDAO res= gDD.create(drone);
        gIZD.disconnect();
        return Response.status(200).entity("Le drone id est : " + id).build();

    }

    @POST
    @Path("{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response postDronesById(@PathParam("id") int id) {

        return Response.status(200).entity("Le drone id est : " + id).build();

    }

}
