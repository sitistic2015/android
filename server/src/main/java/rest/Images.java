package rest;


import dao.GeoImageDAO;
import entity.GeoImage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by arno on 12/02/15.
 */
@Path("/images")
public class Images {

    @GET
    public Response getImages(String images) {

        return Response.status(200).entity("Les images sont : " + images).build();

    }

    @GET
    @Path("getallimages")
    @Produces({MediaType.APPLICATION_JSON})
    public List<GeoImage> getAllImages() {

        GeoImageDAO gID = new GeoImageDAO();
        gID.connect();
        List<GeoImage> res = gID.getAll();
        gID.disconnect();
        // TODO fix that
        return res;

    }

    @GET
    @Path("getfirstimage")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getFirstImage() {

        GeoImageDAO gID = new GeoImageDAO();
        gID.connect();
        String res = gID.getAll().get(0).getImageIn64();
        gID.disconnect();
        // TODO fix that
        return Response.status(200).entity(res).build();

    }

}
