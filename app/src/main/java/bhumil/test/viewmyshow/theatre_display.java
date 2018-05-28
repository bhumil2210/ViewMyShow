package bhumil.test.viewmyshow;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

//import com.google.android.gms.location.LocationListener;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.location.places.Places.getPlaceDetectionClient;

/**
 * Created by bhumil on 26/5/18.
 */

public class theatre_display extends AppCompatActivity {

    private TextView coord;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    private double lat=19.305090,lng=72.853888;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    ProgressDialog p;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theatre_display);
        recyclerView = (RecyclerView)findViewById(R.id.list_of_cinema);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        p=new ProgressDialog(this);
        p.setMessage("Fetching Nearby Theatres...");
        p.show();
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.e("location = ", location.toString());
                lat = location.getLongitude();
                lng = location.getLongitude();
                nearbyplaces(lat,lng);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else{
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,locationListener);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                p.dismiss();
                nearbyplaces(lat,lng);
            }
        }, 10000);


    }

    public void nearbyplaces(double lat,double lng){

        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place" +
                "/nearbysearch/json?");
        stringBuilder.append("location="+lat+","+lng).append("&rankby=distance")
                .append("&keyword=Cinema|Theatre").append("&key="+getResources().getString(R.string.google_places_key));

        String url = String.valueOf(stringBuilder);
        Log.e("url=",url);

        listItems = new ArrayList<>();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray result = response.getJSONArray("results");
                            Log.e("Places",""+result);
                            for(int i=0;i<20;i++) {
                                JSONObject places = result.getJSONObject(i);
                                String place_name = places.getString("name");
                                String place_address = places.getString("vicinity");
                                ListItem listItem = new ListItem(place_name,place_address);
                                listItems.add(listItem);
                                Log.e("Cinema-", place_name);
                                Log.e("Address-", place_address);

                            }

                            adapter = new MyAdapter(listItems,getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("Error - volley",error.getMessage());

                    }
                });
        Appcontroller.getInstance().addToRequestQueue(jsObjRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,locationListener);
            }
        }
    }
}