package com.example.gabriellefranca.mapas;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    double lat = -25.472597;
    double lat_user = 0.0;
    double longi_user = 0.0;
    double longi = -49.252206;
    Button btnMapa;
    Integer tipo=1;
    String mapa;
    double lat_gabi = -25.465711;
    double longi_gabi = -49.193598;
    double lat_felipe = -25.492269;
    double longi_felipe = -49.2107451   ;


    private Marker marcador;
    LatLng ll = new LatLng(lat, longi);
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnMapa = (Button) findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(tipo){
                    case 1:
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        mapa = "HÍBRIDO";
                        tipo=2;
                        break;
                    case 2:
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        mapa = "SATÉLITE";
                        tipo=3;
                        break;
                    case 3:
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        mapa = "TERRENO";
                        tipo=4;
                        break;
                    case 4:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        mapa = "NORMAL";
                        tipo=1;
                        break;
                }
                Toast.makeText(MapsActivity.this,mapa,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, longi)).title("Concessionária Switch"));
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(ll, 20);
        //CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat_gabi, longi_gabi))
                .title("Casa da França")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gabi)));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat_felipe, longi_felipe))
                .title("Casa do Felipe")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.felipe)));





       /* // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, longi)).title("Senai Boqueirão ÉOQQQ"));
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(ll, 20);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);

        mMap.moveCamera(location);
        mMap.animateCamera(zoom, 3000, null);*/

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        minhalocalizacao();

    }

    private void colocarmarcador(double lat_user, double longi_user) {
        LatLng coordenadas = new LatLng(lat_user, longi_user);
        CameraUpdate localizacao = CameraUpdateFactory.newLatLngZoom(coordenadas, 20);
        if (marcador != null) marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Sua Localização")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.felipe)));
        mMap.animateCamera(localizacao);

    }

    private void atualizarlocal(Location locations) {
        if (locations != null) {
            lat_user = locations.getLatitude();
            longi_user = locations.getLongitude();
            colocarmarcador(lat_user, longi_user);
        }
    }

    public LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            atualizarlocal(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void minhalocalizacao() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        atualizarlocal(location);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,locListener);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,locListener);
        //  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0, (android.location.LocationListener) locListener);
    }

}