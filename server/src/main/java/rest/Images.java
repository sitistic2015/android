package rest;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by arno on 12/02/15.
 */
@Path("/images")
public class Images {

    @GET
    public Response getImages(String images) {

        return Response.status(200).entity("Les images sont : " + images).build();

    }
}
