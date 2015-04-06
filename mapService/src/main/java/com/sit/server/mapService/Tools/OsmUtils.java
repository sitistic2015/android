package com.sit.server.mapService.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.jcraft.jsch.*;

/**
 * Class OsmUtils
 * @author vivien
 *
 */
public class OsmUtils {

	public static void createObjFromMap(Double bottomLeftLatitude,
			Double bottomLeftLongitude, Double topRightLatitude,
			Double topRightLongitude) throws IOException {
		// Path to osm2world executable
		String osm2worldPath = "/home/vivien/Software/OSM2World/";
		String osmMapFolder = "/media/vivien/shared_hdd/Workspace/Linux/test/";

		// Informations to download map
		String fileName = "map";
		String filePath = osmMapFolder + "/" + fileName;
		String osmApi = "http://api.openstreetmap.org/api/0.6/map?bbox="
				+ bottomLeftLatitude + "," + bottomLeftLongitude + ","
				+ topRightLatitude + "," + topRightLongitude;

		downloadFile(osmApi, filePath + ".osm");

		try {
			Process proc = Runtime
					.getRuntime()
					.exec("java -jar "
							+ osm2worldPath
							+ "OSM2World.jar --config example_config.properties -i "
							+ filePath + ".osm -o " + filePath + ".obj");
			proc.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void downloadFile(String url, String saveFilePath)
			throws IOException {
		final int BUFFER_SIZE = 4096;

		HttpURLConnection getMap;

		URL mapUrl = new URL(url);
		getMap = (HttpURLConnection) mapUrl.openConnection();
		getMap.setRequestMethod("GET");

		int responseCode = getMap.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {

			// opens input stream from the HTTP connection
			InputStream inputStream = getMap.getInputStream();

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			System.out.println("File downloaded");
		} else {
			System.out
					.println("No file to download. Server replied HTTP code: "
							+ responseCode);
		}
		getMap.disconnect();
	}

	public static void uploadMapFile(String fileToSend, String remoteFile, String host, String user, String password) throws JSchException, IOException {
		try {
			Process proc = Runtime
					.getRuntime()
					.exec("scp " + fileToSend + " " + user + "@" + host + ":" + remoteFile);
			proc.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
