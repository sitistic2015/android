package fr.istic.sit.coordsender;

import android.os.AsyncTask;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
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

    private static final String URL = "http://5.135.185.191:8080/georestservice/rest/";

    private final Coordinates coordinates;
    private final int request;
    private final Messenger replyTo;

    public RequestTask(int request, Coordinates coordinates, Messenger replyTo) {
        this.request = request;
        this.coordinates = coordinates;
        this.replyTo = replyTo;
    }

    @Override
    protected String doInBackground(String... uri) {
        switch(request) {
            case RequesterService.MSG_ZONE:
                try {
                    return postZone("geoposition/zoneObject", coordinates);
                } catch (IOException e) {
                    return e.getMessage();
                }
            case RequesterService.MSG_POINT:
                try {
                    return postPoint("geoposition/point/" + coordinates.getCoordinates().get(0).first + "/" + coordinates.getCoordinates().get(0).second);
                } catch (IOException e) {
                    return e.getMessage();
                }
            default:return null;
        }
    }

    private String formatZone(Coordinates coordinates) {
        String stringCoordinates = "{\"type\": \"Polygon\", \"coordinates\":[[";
        for(int i = 0; i < coordinates.getCoordinates().size() - 1; i++) {
            stringCoordinates += "[" + coordinates.getCoordinates().get(i).first + ", " + coordinates.getCoordinates().get(i).second + "],";
        }
        stringCoordinates += "[" + coordinates.getCoordinates().get(coordinates.getCoordinates().size() - 1).first + ", " + coordinates.getCoordinates().get(coordinates.getCoordinates().size() - 1).second + "]";
        stringCoordinates += "]]}";
        System.out.println(stringCoordinates);
        return stringCoordinates;
    }

    private String postZone(String args, Coordinates coordinates) throws IOException {
        HttpPost request = new HttpPost(URL + args);
        try {
            request.setHeader(new Header() {
                @Override
                public String getName() {
                    return "content-type";
                }

                @Override
                public String getValue() {
                    return "application/json";
                }

                @Override
                public HeaderElement[] getElements() throws ParseException {
                    return new HeaderElement[0];
                }
            });
            request.setEntity(new ByteArrayEntity(
                            formatZone(coordinates).getBytes("UTF-8")));
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
            throw new IOException(statusLine.getStatusCode()+"\n"+statusLine.getReasonPhrase());
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