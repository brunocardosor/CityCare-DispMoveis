package br.edu.leaosampaio.CityCare.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.IOException;
import java.util.List;

import br.edu.leaosampaio.CityCare.DAO.CategoriaDAO;
import br.edu.leaosampaio.CityCare.DAO.DenunciaDAO;
import br.edu.leaosampaio.CityCare.Modelo.Categoria;
import br.edu.leaosampaio.CityCare.Modelo.Denuncia;
import br.edu.leaosampaio.CityCare.Modelo.Usuario;
import br.edu.leaosampaio.CityCare.Modelo.UsuarioAplication;
import br.edu.leaosampaio.CityCare.R;


public class DenunciaActivity extends AppCompatActivity {

    private Spinner spinCategoria;
    private EditText descricao;
    private ImageButton btEnviar;
    private TextView localizacao;

    Toolbar toolbar;
    DenunciaDAO denunciaDAO;
    Denuncia den = new Denuncia();
    Usuario usuario = UsuarioAplication.getInstance().getUsuario();
    Place place;
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

        spinCategoria = (Spinner) findViewById(R.id.spinCategoria);
        descricao = (EditText) findViewById(R.id.edtDescricao);
        localizacao = (TextView) findViewById(R.id.edtLocalizacao);
        btEnviar = (ImageButton) findViewById(R.id.btEnviar);

        CategoriaDAO categorias = new CategoriaDAO(this);
        final ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(DenunciaActivity.this, android.R.layout.simple_spinner_dropdown_item, categorias.buscar());
        spinCategoria.setAdapter(adapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Denunciar");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(getIntent().hasExtra("denuncia")){
            boolean posicao = false;

            den = getIntent().getParcelableExtra("denuncia");
            den.setCategoria((Categoria) getIntent().getParcelableExtra("categoria"));
            descricao.setText(den.getDescricao());
            localizacao.setText(den.getEndereco(this));
            while(posicao == false){
                for(int i = 0; i <= spinCategoria.getCount(); i++){
                    if(den.getCategoria().getDescricao() == spinCategoria.getItemAtPosition(i).toString()){
                        spinCategoria.setSelection(i,false);
                        posicao = true;
                    }
                }
            }

        }

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarDenuncia(den);
                if(getIntent().hasExtra("denuncia")) {
                    getIntent().removeExtra("denuncia");
                }
            }
        });

        localizacao.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(DenunciaActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PLACE_PICKER_REQUEST){
            if (resultCode == RESULT_OK){
                place = PlacePicker.getPlace(DenunciaActivity.this, data);
                localizacao.setText(place.getAddress());
                den.setLatitude(place.getLatLng().latitude);
                den.setLongitude(place.getLatLng().longitude);
                Geocoder geocoder = new Geocoder(this);
                try {
                    List<Address> cidades = geocoder.getFromLocation(den.getLatitude(),den.getLongitude(),1);
                    den.setCidade(cidades.get(0).getLocality());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private void cadastrarDenuncia(Denuncia denuncia) {
        if(TextUtils.isEmpty(descricao.getText().toString())){
            descricao.setError("Campo Obrigatório");
        }

        if(TextUtils.isEmpty(localizacao.getText().toString())){
            localizacao.setError("Campo Obrigatório");
        }

        if(spinCategoria.getSelectedItemPosition() == 0) {
            TextView errorTextView = (TextView) spinCategoria.getSelectedView();
            errorTextView.setError("Campo Obrigatório");
            errorTextView.setTextColor(Color.RED);
            errorTextView.setText("Campo Obrigatório");
        }

        else {
            denunciaDAO = new DenunciaDAO(DenunciaActivity.this);
            denuncia.setDescricao(descricao.getText().toString());
            denuncia.setCategoria((Categoria) spinCategoria.getSelectedItem());
            if(denuncia.getDataHora() == null){
                denuncia.setDataHora();
            }
            if(denuncia.getUsuario() == null){
                denuncia.setUsuario(usuario);
            }

            if(denunciaDAO.salvar(denuncia, DenunciaActivity.this)){
                finish();
            } else {
                spinCategoria.setSelection(0);
                descricao.setText("");
                localizacao.setText("");
            }

        }
    }



}
