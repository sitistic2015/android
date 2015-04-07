package com.sit.server.mapService.Controller;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.sit.server.mapService.Tools.OsmUtils;

@Path("/map")
public class mapController {

	@GET
	@Path("/{bLeftLatitude}/{bLeftLongitude}/{tRightLatitude}/{tRightLongitude}")
	public void generate3dMap(@PathParam("bLeftLatitude") Double bLeftLatitude,
								@PathParam("bLeftLongitude") Double bLeftLongitude,
								@PathParam("tRightLatitude") Double tRightLatitude,
								@PathParam("tRightLongitude") Double tRightLongitude) throws IOException, JSchException, SftpException {
		
		OsmUtils.createObjFromMap(bLeftLatitude, bLeftLongitude, tRightLatitude, tRightLongitude);
		OsmUtils.uploadMapFile("/media/vivien/shared_hdd/Workspace/Linux/test/map.obj", "/home/baptiste/testssh/", "148.60.38.80", "baptiste", "baptiste");
		OsmUtils.uploadMapFile("/media/vivien/shared_hdd/Workspace/Linux/test/map.obj.mtl", "/home/baptiste/testssh/", "148.60.38.80", "baptiste", "baptiste");
	}
}
