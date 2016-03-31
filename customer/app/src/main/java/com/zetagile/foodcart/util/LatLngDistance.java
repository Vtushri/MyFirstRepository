package com.zetagile.foodcart.util;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class LatLngDistance {

    public void getDirectionUrl(LatLng ll, LatLng ll2) {

        String str_origin = "origins=" + ll.latitude + "," + ll.longitude;

        // Destination of route
        String str_dest = "destinations=" + ll2.latitude + "," + ll2.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/distancematrix/" + output + "?" + parameters;
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }

    private String downloadUrl(String strUrl) throws IOException {
        System.out.println("Before OK"+strUrl);
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Connection Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, JSONObject> {

        // Parsing the data in non-ui thread
        @Override
        protected JSONObject doInBackground(String... jsonData) {

            JSONObject jObject;
            JSONObject distance_duration = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJsonParser parser = new DirectionsJsonParser();

                // Starts parsing data
                distance_duration = parser.parse(jObject);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return distance_duration;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(JSONObject result) {
            if(result != null) {
                try {
                    System.out.println("Result OK");
                    String str_duration_secs = result.getJSONObject("duration").getString("value");
                    String str_distance_merters = result.getJSONObject("distance").getString("value");
                    long distance_merters = 0;
                    long duration_secs = 0;

                    if(str_distance_merters != null && !str_distance_merters.isEmpty())
                        distance_merters = Integer.parseInt(str_distance_merters);

                    if(str_duration_secs != null && !str_duration_secs.isEmpty())
                        duration_secs = Integer.parseInt(str_duration_secs);

                    onDistanceCalculated(distance_merters, duration_secs*1000);
                } catch (JSONException e) {
                    e.printStackTrace();
                    onDistanceCalculated(0 ,0);
                }
            } else {
                System.out.println("Result null");
                onDistanceCalculated(0, 0);

            }
//            time.setText("Distance:" + distance );
        }
    }

    private class DirectionsJsonParser {

        public JSONObject parse(JSONObject directionObject) {
            try {

                if (directionObject == null)
                    return null;

                if(!directionObject.getString("status").equals("OK")) {
                    return null;
                }

                JSONArray rows = directionObject.getJSONArray("rows");
                if(rows == null)
                    return null;

                JSONArray elements = rows.getJSONObject(0).getJSONArray("elements");

                if(elements != null)
                    return elements.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public abstract void onDistanceCalculated(long distance_meters, long time_milliseconds);
}
