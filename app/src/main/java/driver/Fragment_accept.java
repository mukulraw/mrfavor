package driver;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import in.mrfavor.mrfavorapp.DataParser;
import in.mrfavor.mrfavorapp.DriverPanel;
import com.myfavourcarpooling.easycarpooling.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by krishna on 1/19/2018.
 */
//
//public class Fragment_accept extends Fragment implements OnMapReadyCallback {
//   // String baseurl = getResources().getString(R.string.baseurl);
//
//    public static final String url = "http://softcode.in/myfavour/acceptbooking.php";
//    String picloc,droploc,custname,contactnumber,rideID,userid;
//    double  piclat,piclng,droplat,droplng;
//    LatLng startLatlng,endLatlng;
//    MapView mMapView;
//
//    private GoogleMap mMap;
//    ArrayList<LatLng> MarkerPoints;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        //returning our layout file
//        //change R.layout.yourlayoutfilename for each of your fragments
//        View v = inflater.inflate(R.layout.fragment_driver_acceptbooking, container, false);
//
//        picloc = getArguments().getString("picpoint");
//        droploc = getArguments().getString("droppoint");
//        custname = getArguments().getString("custname");
//        contactnumber = getArguments().getString("contactnumber");
//
//        piclat = getArguments().getDouble("piclat");
//        piclng = getArguments().getDouble("piclng");
//
//        droplat = getArguments().getDouble("droplat");
//        droplng = getArguments().getDouble("droplng");
//
//        rideID = getArguments().getString("rideID");
//
//        startLatlng = new LatLng(piclat,piclng);
//        endLatlng = new LatLng(droplat,droplng);
//
//        SharedPreferences pref = getContext().getSharedPreferences("Logininfo", Context.MODE_PRIVATE);
//
//        userid = pref.getString("useremail",null);
//
//
//        TextView tvcustname = (TextView) v.findViewById(R.id.tvUsername);
//        TextView startride = (TextView) v.findViewById(R.id.tvStartride);
//        TextView endride = (TextView) v.findViewById(R.id.tvEndride);
//
//        tvcustname.setText(custname+" (" + contactnumber + ")");
//        startride.setText(picloc);
//        endride.setText(droploc);
//
//        Button confrim = (Button) v.findViewById(R.id.btn_confirm);
//
//
//        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/tcb.ttf");
//
//        confrim.setTypeface(custom_font);
//
//
//        confrim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String[] fields = {"userID","rideID"};
//                String[] fielddata = {userid,rideID};
//
//
//                Postrequest postrequest = new Postrequest(Fragment_accept.this,getContext(),fields,fielddata);
//                postrequest.execute(url);
//
//
//            }
//        });
//
//
//
//
//        mMapView = (MapView) v.findViewById(R.id.map);
//
//        mMapView.onCreate(savedInstanceState);
//
//        mMapView.onResume(); // needed to get the map to display immediately
//        mMapView.getMapAsync(this);
//
//        return  v;
//    }
//
//
//    public void afterpostrequest()
//    {
//        LatLng startlatlng = new LatLng(piclat,piclng);
//        LatLng endlatlng = new LatLng(droplat,droplng);
//
//        ((DriverPanel)getContext()).currentbooking(picloc,droploc,custname,contactnumber,startlatlng,endlatlng,rideID);
//    }
//
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        //you can set the title for your toolbar here for different fragments different titles
//        getActivity().setTitle("Accept Booking");
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        mMap = googleMap;
//        mMap.getUiSettings().setMapToolbarEnabled(false);
//
//
//       String url = getUrl(startLatlng, endLatlng);
//        FetchUrl FetchUrl = new FetchUrl();
//        // Start downloading json data from Google Directions API
//        Log.d("urlforfetch",url);
//      FetchUrl.execute(url);
//
//        MarkerOptions marker1 = new MarkerOptions().position(startLatlng).title(picloc);
//        marker1.icon(BitmapDescriptorFactory.fromResource(R.drawable.startmarker));
//
//        MarkerOptions marker2 = new MarkerOptions().position(endLatlng).title(droploc);
//        marker2.icon(BitmapDescriptorFactory.fromResource(R.drawable.endmarker));
//
//        mMap.addMarker(marker1);
//        mMap.addMarker(marker2);
//
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//
//        builder.include(startLatlng);
//        builder.include(endLatlng);
//        LatLngBounds bounds = builder.build();
//
//        int padding = 100; // offset from edges of the map in pixels
//        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
//        mMap.animateCamera(cu);
//
//    }
//
//
//    private String getUrl(LatLng origin, LatLng dest) {
//
//        // Origin of route
//        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
//
//        // Destination of route
//        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
//
//
//        // Sensor enabled
//        String sensor = "sensor=false";
//
//        // Building the parameters to the web service
//        String parameters = str_origin + "&" + str_dest + "&" + sensor;
//
//        // Output format
//        String output = "json";
//
//        // Building the url to the web service
//        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
//
//
//        return url;
//    }
//
//
//
//    private String downloadUrl(String strUrl) throws IOException {
//        String data = "";
//        InputStream iStream = null;
//        HttpURLConnection urlConnection = null;
//        try {
//            URL url = new URL(strUrl);
//
//            // Creating an http connection to communicate with url
//            urlConnection = (HttpURLConnection) url.openConnection();
//
//            // Connecting to url
//            urlConnection.connect();
//
//            // Reading data from url
//            iStream = urlConnection.getInputStream();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
//
//            StringBuffer sb = new StringBuffer();
//
//            String line = "";
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//
//            data = sb.toString();
//            Log.d("downloadUrl", data.toString());
//            br.close();
//
//        } catch (Exception e) {
//            Log.d("Exception", e.toString());
//        } finally {
//            iStream.close();
//            urlConnection.disconnect();
//        }
//        return data;
//    }
//
//    // Fetches data from url passed
//    private class FetchUrl extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... url) {
//
//            // For storing data from web service
//            String data = "";
//
//            try {
//                // Fetching the data from web service
//                data = downloadUrl(url[0]);
//                Log.d("Background Task data", data.toString());
//            } catch (Exception e) {
//                Log.d("Background Task", e.toString());
//            }
//            return data;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//          ParserTask parserTask = new ParserTask();
//
//            // Invokes the thread for parsing the JSON data
//
//
//            parserTask.execute(result);
//
//        }
//    }
//
//
//
//    /**
//     * A class to parse the Google Places in JSON format
//     */
//    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
//        String distandtime;
//        double totaldistance;
//        // Parsing the data in non-ui thread
//        @Override
//        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
//
//            JSONObject jObject;
//            List<List<HashMap<String, String>>> routes = null;
//
//
//            double distancekm = getdistance(jsonData[0]);
//
//
//
//            try {
//                jObject = new JSONObject(jsonData[0]);
//
//                DataParser parser = new DataParser();
//
//
//                // Starts parsing data
//                routes = parser.parse(jObject);
//
//
//            } catch (Exception e) {
//                Log.d("ParserTask",e.toString());
//                e.printStackTrace();
//            }
//            return routes;
//        }
//
//        private double getdistance(String jsonDatum) {
//            double dist = 0.0;
//            String duration = "";
//            JSONObject jsonObject = new JSONObject();
//            try {
//
//                jsonObject = new JSONObject(jsonDatum.toString());
//
//                JSONArray array = jsonObject.getJSONArray("routes");
//
//                JSONObject routes = array.getJSONObject(0);
//
//                JSONArray legs = routes.getJSONArray("legs");
//
//                JSONObject steps = legs.getJSONObject(0);
//
//                JSONObject distancekm = steps.getJSONObject("distance");
//                JSONObject durationobj = steps.getJSONObject("duration");
//
//                duration = durationobj.getString("text");
//
//
//                dist = Double.parseDouble(distancekm.getString("text").replaceAll("[^\\.0123456789]","") );
//                totaldistance = dist;
//                distandtime = duration;
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            return dist;
//        }
//
//
//
//        // Executes in UI thread, after the parsing process
//        @Override
//        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
//            ArrayList<LatLng> points;
//            PolylineOptions lineOptions = null;
//
//            // Traversing through all the routes
//            for (int i = 0; i < result.size(); i++) {
//                points = new ArrayList<>();
//                lineOptions = new PolylineOptions();
//
//                // Fetching i-th route
//                List<HashMap<String, String>> path = result.get(i);
//
//                // Fetching all the points in i-th route
//                for (int j = 0; j < path.size(); j++) {
//                    HashMap<String, String> point = path.get(j);
//
//
//                    double lat = Double.parseDouble(point.get("lat"));
//                    double lng = Double.parseDouble(point.get("lng"));
//                    LatLng position = new LatLng(lat, lng);
//
//                    points.add(position);
//                }
//
//
//
//                // Adding all the points in the route to LineOptions
//                lineOptions.addAll(points);
//                lineOptions.width(8);
//                lineOptions.color(Color.BLUE);
//
//               // distance.setText("Total Distance: "+totaldistance+" KM (Approx) Takes "+distandtime+" (Approx)");
//                double amt = totaldistance*7+50;
//              //  fair.setText("Total Fair: "+amt+" Rupees");
//                Log.d("onPostExecute","onPostExecute lineoptions decoded");
//
//            }
//
//            // Drawing polyline in the Google Map for the i-th route
//            if(lineOptions != null) {
//                mMap.addPolyline(lineOptions);
//            }
//            else {
//                Log.d("onPostExecute","without Polylines drawn");
//            }
//        }
//    }
//
//}
