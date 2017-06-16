package br.edu.leaosampaio.CityCare.Fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.edu.leaosampaio.CityCare.R;

public class MapsFragment extends Fragment{

    MapView mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);
        mapFragment = (MapView) view.findViewById(R.id.map);

        mapFragment.onCreate(getArguments());

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                GoogleMap mMap = googleMap;

                LatLng jua = new LatLng(-7.1849977, -39.3156996);
                mMap.addMarker(new MarkerOptions().position(jua).title("Juazeiro do Norte"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(jua));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            }
        });

        return view;
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
}
