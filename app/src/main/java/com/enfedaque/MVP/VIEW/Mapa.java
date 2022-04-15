package com.enfedaque.MVP.VIEW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.enfedaque.R;
import com.enfedaque.OTROS_PAQUETES.UTILS.DirectionsUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, LocationListener {


    private GoogleMap miMapa;
    private LocationManager locationManager;
    private LocationProvider locationProvider;

    com.google.maps.model.LatLng cine1;
    com.google.maps.model.LatLng cine2;
    com.google.maps.model.LatLng cine3;
    com.google.maps.model.LatLng cine4;
    com.google.maps.model.LatLng cine5;
    com.google.maps.model.LatLng cine6;


    com.google.maps.model.LatLng puntoPartida;

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
            Intent miIntent=new Intent(this, indexView.class);
            startActivity(miIntent);
            return true;
        }else if(item.getItemId() == R.id.verFav){
            Intent miIntent=new Intent(this, verFavoritosView.class);
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


        AlertDialog.Builder dialogo=new AlertDialog.Builder(this);
        dialogo.setMessage("Seleccione SU PUNTO DE PARTIDA en el mapa con un click");
        dialogo.show();
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

        if (puntoPartida!=null){
            AlertDialog.Builder dialogo=new AlertDialog.Builder(this);
            dialogo.setMessage("Desde ' PUNTO DE PARTIDA ' hasta ' " + marker.getTitle() + ". \n " +
                    "¿Desea realizar la ruta?");
            dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    DirectionsApiRequest request = DirectionsApi.newRequest(getGeoContext())
                            .mode(TravelMode.DRIVING)
                            .origin(puntoPartida)
                            .destination(cine2)
                            .departureTime(DateTime.now())
                            .alternatives(true);

                    RouteTask routeTask = new RouteTask();
                    routeTask.execute(request);
                    DirectionsResult result=null;
                    try {
                        result = routeTask.get();

                        if (result == null) {
                            Toast.makeText(getApplicationContext(),"Funcion deshabilitada porque" +
                                    " no he metido TARJETA DE CREDITO en google cloud console por seguridad" , Toast.LENGTH_LONG).show();

                        }else{
                            paintRoute(result, 0);
                        }

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //No hago nada
                }
            });
            dialogo.show();
        }


        return false;
    }

    private class RouteTask extends AsyncTask<DirectionsApiRequest, Void, DirectionsResult> {

        @Override
        protected DirectionsResult doInBackground(DirectionsApiRequest... requests) {
            try {
                return requests[0].await();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            } catch (ApiException apie) {
                apie.printStackTrace();
            }

            return null;
        }
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        puntoPartida=new com.google.maps.model.LatLng(latLng.latitude , latLng.longitude);
        //Hago que la camara apunte hacia don de hago click
        miMapa.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        Objects.requireNonNull(miMapa.addMarker(new MarkerOptions()
                .position(new LatLng(puntoPartida.lat, puntoPartida.lng))
                .title("INICIO")))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        crearMarkers();

        Objects.requireNonNull(miMapa.addMarker(new MarkerOptions()
                .position(new LatLng(cine1.lat, cine1.lng))
                .title("ZARAGOZA")))
                .setSnippet("Cine de la zona centrica de la localidad. ENTRADA GRATUITA");

        Objects.requireNonNull(miMapa.addMarker(new MarkerOptions()
                .position(new LatLng(cine2.lat, cine2.lng))
                .title("MADRID")))
                .setSnippet("Uno de los principales cines de España");

        Objects.requireNonNull(miMapa.addMarker(new MarkerOptions()
                .position(new LatLng(cine3.lat, cine3.lng))
                .title("BARCELONA")))
                .setSnippet("Mas antiguo de la provincia");

        Objects.requireNonNull(miMapa.addMarker(new MarkerOptions()
                .position(new LatLng(cine4.lat, cine4.lng))
                .title("SEVILLA")))
                .setSnippet("Cine al aire libre");

        Objects.requireNonNull(miMapa.addMarker(new MarkerOptions()
                .position(new LatLng(cine5.lat, cine5.lng))
                .title("SANTIAGO DE COMPOSTELA")))
                .setSnippet("Famoso por apariciones en RRSS");

        Objects.requireNonNull(miMapa.addMarker(new MarkerOptions()
                .position(new LatLng(cine6.lat, cine6.lng))
                .title("VALENCIA")))
                .setSnippet("Acogedor y romantico");

        Toast.makeText(this, "Seleccione EL CINE", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {


    }

    private void crearMarkers() {
        cine1=new com.google.maps.model.LatLng(41.73463707764275, -1.0571548237694044);
        cine2 = new com.google.maps.model.LatLng(40.38874829408632, -3.7842929925115243);
        cine3 = new com.google.maps.model.LatLng(41.411562200476936, 2.1984557341740487);
        cine4 = new com.google.maps.model.LatLng(37.39348801025005, -6.00996221441307);
        cine5 = new com.google.maps.model.LatLng(42.76023393965538, -8.287488760748838);
        cine6 = new com.google.maps.model.LatLng(39.47737854310233, -0.3492460469420232);

    }

    private GeoApiContext getGeoContext(){
        GeoApiContext geoApiContext=new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3)
                .setApiKey("AIzaSyCLBgnBFBMy3SmyEmGUFF-RIt8UE9PAwpo")
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }

    //Pinto la ruta
    private void paintRoute(DirectionsResult resultado, int opcion){

        List<com.google.maps.model.LatLng> routePath = resultado.routes[opcion].overviewPolyline.decodePath();
        miMapa.addPolyline(new PolylineOptions()
                .add(DirectionsUtil.fromMapsToDirections(routePath))
                .color(Color.RED));
    }

}