package in.mrfavor.mrfavorapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.myfavourcarpooling.easycarpooling.R;

import java.util.ArrayList;

public class NewLocationautocomplet extends AppCompatActivity  {

    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(28.6472799, 76.8130629), new LatLng(28.6472799, 76.8130629));
    //PlaceAutocompleteFragment autocompleteFragment;
    String locname;
    double lat,lng;
    Databasehelper myDb;
    ListView listView;
    String pick , drop;
    TextView pp,dd;
    String route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_locationautocomplet);
        Intent ii = getIntent();
        pick = ii.getStringExtra("pick");
        drop = ii.getStringExtra("drop");
        route=pick+"   to   "+drop;
        pp=(TextView)findViewById(R.id.pick);
        dd=(TextView)findViewById(R.id.drop);
        pp.setText(pick);
        dd.setText(drop);
        myDb = new Databasehelper(this);

        Intent getextra = getIntent();
        locname = getextra.getStringExtra("locname");
        lat = getextra.getDoubleExtra("lat",0);
        lng = getextra.getDoubleExtra("lng",0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.iclogo);


        /*autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);
        autocompleteFragment.setHint("Search a Location");

        autocompleteFragment.setText(locname);
        autocompleteFragment.setBoundsBias(BOUNDS_MOUNTAIN_VIEW);
*/
        Button done = (Button) findViewById(R.id.btn_Done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Drawroutsonmap.class);
                i.putExtra("pick11",pp.getText().toString());
                i.putExtra("drop11",dd.getText().toString());
//                    i.putExtra("locname", locname);
//                    i.putExtra("latvalue", lat);
//                    i.putExtra("lngvalue", lng);
//                    i.putExtra("fragment","droplocation");
                    startActivity(i);




                }
            }
       );

        ImageView fav = (ImageView) findViewById(R.id.ivFav);

        ArrayList<String> lname = new ArrayList<String>();

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(myDb.isExist(route)) {
                    Toast.makeText(getApplicationContext(),"Location is already added.",Toast.LENGTH_LONG).show();
                }
                else
                {
                    addFav();
                }
            }
        });

        Cursor res = myDb.getAllData();
        if(res.getCount() != 0) {
            // show message
            while (res.moveToNext()) {

                lname.add(res.getString(1));

            }


        }


        listView=(ListView)findViewById(R.id.listView);

        CustomList adapter = new
                CustomList(NewLocationautocomplet.this,lname);

        listView.setAdapter(adapter);;

    }


    public void delFav(Integer pos) {
        Cursor res = myDb.getAllData();
        if (res.getCount() != 0) {
            // show message
            Integer i;
            i = 0;
            while (res.moveToNext()) {
                if (i == pos) {
                    String dataid = res.getString(0);

                    Integer deletedRows =  myDb.deleteData(dataid);


                    if(deletedRows > 0)
                        Toast.makeText(getApplicationContext(),"Removed from fav.",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(),"Please try again.",Toast.LENGTH_LONG).show();
                }
                i++;
            }

        }
    }

    public void selectFav(Integer pos)
    {
        Cursor res = myDb.getAllData();
        if(res.getCount() != 0) {
            // show message
            Integer i;
            i = 0;
            while (res.moveToNext()) {
                if(i==pos) {
                    locname = res.getString(1);
                    lat = Double.valueOf(res.getString(2));
                    lng = Double.valueOf(res.getString(3));
                    //autocompleteFragment.setText(locname);
                }
                i++;
            }


        }

    }

    public void addFav()
    {
        if (route!=null) {
            boolean isInserted = myDb.insertData(route,"","");
            if(isInserted == true)
                Toast.makeText(getApplicationContext(),"Location added in fav.",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(),"Some error occured please try again.",Toast.LENGTH_LONG).show();
        }
    }





    /*@Override
    public void onPlaceSelected(Place place) {

        locname = place.getAddress().toString();
        lat = place.getLatLng().latitude;
        lng = place.getLatLng().longitude;
        Intent ii=new Intent(getApplicationContext(),Userboard.class);
        startActivity(ii);
    }

    @Override
    public void onError(Status status) {

    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //do whatever
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    ArrayList<String> lname = new ArrayList<String>();
    public CustomList(Activity context,
                      ArrayList<String> lname) {
        super(context, R.layout.fav_location_list,lname);
        this.context = context;
        this.lname = lname;

    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.fav_location_list, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.tvLocname);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.ivDel);
        txtTitle.setText(lname.get(position));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NewLocationautocomplet) context).delFav(position);
            }
        });

        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NewLocationautocomplet) context).selectFav(position);
            }
        });


        return rowView;
    }
}
