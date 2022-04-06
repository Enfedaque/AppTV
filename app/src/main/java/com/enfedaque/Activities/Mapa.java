package com.enfedaque.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.enfedaque.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, LocationListener {

    private GoogleMap miMapa;
    private LocationManager locationManager;
    private LocationProvider locationProvider;
    LatLng cine1;
    LatLng cine2;
    LatLng cine3;
    LatLng cine4;
    LatLng cine5;
    LatLng cine6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fondoMapa);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    //MEnu superior àra volver al index o recargarlo
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    //Manu actionBar opciones
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Si toca la casa lo envio al inicio
        if(item.getItemId() == R.id.house){
            Intent miIntent=new Intent(this, index.class);
            startActivity(miIntent);
            return true;
        }else if(item.getItemId() == R.id.fondoMapa){
            Intent miIntent=new Intent(this, Mapa.class);
            startActivity(miIntent);
            return true;
        }
        return true;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        miMapa=googleMap;
        miMapa.setOnMarkerClickListener(this);
        miMapa.setOnMapClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , 1);
            return;
        }
        miMapa.setMyLocationEnabled(true);

        locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
        locationProvider=locationManager.getProvider(locationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(locationProvider.getName(), 4000, 200, this);

        crearMarkers();

        Objects.requireNonNull(miMapa.addMarker(new MarkerOptions()
                .position(cine1)
                .title("ZARAGOZA")))
                .setSnippet("Cine de la zona centrica de la localidad. ENTRADA GRATUITA");

        Objects.requireNonNull(miMapa.addMarker(new MarkerOptions()
                .position(cine2)
                .title("MADRID")))
                .setSnippet("Uno de los principales cines de España");

        Objects.requireNonNull(miMapa.addMarker(new MarkerOptions()
                .position(cine3)
                .title("BARCELONA")))
                .setSnippet("Mas antiguo de la provincia");

        Objects.requireNonNull(miMapa.addMarker(new MarkerOptions()
                .position(cine4)
                .title("SEVILLA")))
                .setSnippet("Cine al aire libre");

        Objects.requireNonNull(miMapa.addMarker(new MarkerOptions()
                .position(cine5)
                .title("SANTIAGO DE COMPOSTELA")))
                .setSnippet("Famoso por apariciones en RRSS");

        Objects.requireNonNull(miMapa.addMarker(new MarkerOptions()
                .position(cine6)
                .title("VALENCIA")))
                .setSnippet("Acogedor y romantico");
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        //Hago que la camara apunte hacia don de hago click
        miMapa.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {


    }

    private void crearMarkers() {
        cine1 = new LatLng(41.73463707764275, -1.0571548237694044);
        cine2 = new LatLng(40.38874829408632, -3.7842929925115243);
        cine3 = new LatLng(41.411562200476936, 2.1984557341740487);
        cine4 = new LatLng(37.39348801025005, -6.00996221441307);
        cine5 = new LatLng(42.76023393965538, -8.287488760748838);
        cine6 = new LatLng(39.47737854310233, -0.3492460469420232);
    }

}