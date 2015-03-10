package fr.istic.sit.coordsender;

import android.os.AsyncTask;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by jules on 09/03/15.
 */
class RequestTask extends AsyncTask<String, String, String> {

    private static final String URL = "148.60.14.34:8088";

    private final Coordinates coords;
    private final int request;
    private final Messenger replyTo;

    public RequestTask(int request, Coordinates coords, Messenger replyTo) {
        this.request = request;
        this.coords = coords;
        this.replyTo = replyTo;
    }

    @Override
    protected String doInBackground(String... uri) {
        switch(request) {
            case RequesterService.MSG_ZONE:
                try {
                    return postZone("geo/zone", coords);
                } catch (IOException e) {
                    return e.getMessage();
                }
            case RequesterService.MSG_POINT:
                try {
                    return postPoint("geo/geoposition/" + coords.getCoordinates().get(0).first + "/" + coords.getCoordinates().get(0).second);
                } catch (IOException e) {
                    return e.getMessage();
                }
            default:return null;
        }
    }

    private String formatZone(Coordinates coordinates) {
        String stringCoords = "{\"type\": \"Polygon\", \"coordinates\":[";
        for(int i = 0; i < coordinates.getCoordinates().size() - 1; i++) {
            stringCoords += "[" + coordinates.getCoordinates().get(i).first + ", " + coordinates.getCoordinates().get(i).second + "],";
        }
        stringCoords += "[" + coordinates.getCoordinates().get(coordinates.getCoordinates().size() - 1).first + ", " + coordinates.getCoordinates().get(coordinates.getCoordinates().size() - 1).second + "],";
        stringCoords += "]]}";
        return stringCoords;
    }

    private String postZone(String args, Coordinates coords) throws IOException {
        HttpPost request = new HttpPost(URL + args);
        try {
            request.setEntity(new ByteArrayEntity(
                    formatZone(coords).getBytes("UTF8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return post(request);
    }

    private String postPoint(String args) throws IOException {
        HttpPost request = new HttpPost(URL + args);
        return post(request);
    }
    private String post(HttpPost request) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(request);
        StatusLine statusLine = response.getStatusLine();
        if(statusLine.getStatusCode() == HttpStatus.SC_OK){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);
            String responseString = out.toString();
            out.close();
            return responseString;
        } else{
            //Closes the connection.
            response.getEntity().getContent().close();
            throw new IOException(statusLine.getReasonPhrase());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Message _msg = Message.obtain(null, request, result);
        try {
            replyTo.send(_msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}