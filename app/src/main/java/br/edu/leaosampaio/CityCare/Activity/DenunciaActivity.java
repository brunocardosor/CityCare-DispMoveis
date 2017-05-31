package br.edu.leaosampaio.CityCare.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.edu.leaosampaio.CityCare.R;
import br.edu.leaosampaio.citycare_dispmoveis.R;

public class DenunciaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

        getActionBar().setTitle("Denuncia");
    }
}
