package br.edu.leaosampaio.CityCare.Fragments;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import br.edu.leaosampaio.CityCare.DAO.DenunciaDAO;
import br.edu.leaosampaio.CityCare.Modelo.Denuncia;
import br.edu.leaosampaio.CityCare.Modelo.UsuarioAplication;
import br.edu.leaosampaio.CityCare.R;

public class MapsFragment extends Fragment {
    MapView mapFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);
        mapFragment = (MapView) view.findViewById(R.id.map);
        mapFragment.onCreate(getArguments());
        mapFragment.onStart();
        mapFragment.onResume();

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                GoogleMap mMap = googleMap;
                String camera = UsuarioAplication.getInstance().getUsuario().getCidade();
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

                try {
                    preencher(mMap);
                    List<Address> addresses = geocoder.getFromLocationName(camera, 1);
                    Double latitude = addresses.get(0).getLatitude();
                    Double longitude = addresses.get(0).getLongitude();
                    LatLng cidadeLatLng = new LatLng(latitude, longitude);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(cidadeLatLng));
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(13));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }


    public void preencher(GoogleMap mMap) {
        DenunciaDAO denDao = new DenunciaDAO(getActivity());
        List<Denuncia> denuncias = denDao.feedDenuncias();
        for (int i = 0; i < denuncias.size(); i++) {
            LatLng cidade = new LatLng(denuncias.get(i).getLatitude(), denuncias.get(i).getLongitude());
            mMap.addMarker(new MarkerOptions().position(cidade).title(denuncias.get(i).getCategoria().toString()));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}