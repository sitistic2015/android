package rest;


import dao.GeoDroneDAO;
import dao.GeoInterventionZoneDAO;
import entity.GeoDrone;
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
        GeoDrone res = gDD.create(drone);
        gDD.disconnect();
        return Response.status(200).entity("Le drone "+ res.getId() + " est initialisé à la position : "+ res.getCoordinates().getLatitude() +"/"+res.getCoordinates().getLongitude() ).build();

    }

    @GET
    @Path("{id}")
    //  @Consumes({ MediaType.APPLICATION_JSON })
    public GeoDrone getDroneById(@PathParam("id") long id) {
        GeoDroneDAO gDD = new GeoDroneDAO();
        gDD.connect();
        GeoDrone res= gDD.getById(id);
        gDD.disconnect();
        return res;

    }

    @POST
    @Path("{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response postDronesById(@PathParam("id") int id) {

        return Response.status(200).entity("Le drone id est : " + id).build();

    }

}
