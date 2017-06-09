package br.edu.leaosampaio.CityCare.Activity;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import br.edu.leaosampaio.CityCare.Modelo.Denuncia;
import br.edu.leaosampaio.CityCare.R;


public class DenunciaActivity extends AppCompatActivity {

    private Spinner spinCategoria;
    private EditText descricao;
    private ImageButton btEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

        spinCategoria = (Spinner) findViewById(R.id.spinCategoria);
        descricao = (EditText) findViewById(R.id.edtDescricao);
        btEnviar = (ImageButton) findViewById(R.id.btEnviar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Denunciar");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void cadastrarDenuncia(Denuncia denuncia) {
        if (spinCategoria.getSelectedItemId() == 0) {
            EditText errorEditText = (EditText) spinCategoria.getSelectedView();
            errorEditText.setError("Campo Obrigatório");
            errorEditText.setTextColor(Color.RED);
            errorEditText.setText("Campo Obrigatório");
        } else if(TextUtils.isEmpty(descricao.getText().toString())){
            descricao.setError("Campo Obrigatório");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
