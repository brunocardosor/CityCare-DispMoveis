package br.edu.leaosampaio.CityCare.Activity;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;


import br.edu.leaosampaio.CityCare.DAO.CategoriaDAO;
import br.edu.leaosampaio.CityCare.DAO.DenunciaDAO;
import br.edu.leaosampaio.CityCare.Modelo.Categoria;
import br.edu.leaosampaio.CityCare.Modelo.Denuncia;
import br.edu.leaosampaio.CityCare.Modelo.UsuarioSingleton;
import br.edu.leaosampaio.CityCare.R;


public class DenunciaActivity extends AppCompatActivity {

    private Spinner spinCategoria;
    private EditText descricao;
    private ImageButton btEnviar;
    private EditText localizacao;

    CategoriaDAO categorias = new CategoriaDAO(this);
    Denuncia den = new Denuncia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

        spinCategoria = (Spinner) findViewById(R.id.spinCategoria);
        descricao = (EditText) findViewById(R.id.edtDescricao);
        localizacao = (EditText) findViewById(R.id.edtLocalizacao);
        btEnviar = (ImageButton) findViewById(R.id.btEnviar);



        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(DenunciaActivity.this, android.R.layout.simple_spinner_dropdown_item, categorias.buscar());
        spinCategoria.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Denunciar");
        actionBar.setDisplayHomeAsUpEnabled(true);

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarDenuncia(den);
            }
        });
    }



    private void cadastrarDenuncia(Denuncia denuncia) {
        if (spinCategoria.getSelectedItemId() == 0) {
            EditText errorEditText = (EditText) spinCategoria.getSelectedView();
            errorEditText.setError("Campo Obrigatório");
            errorEditText.setTextColor(Color.RED);
            errorEditText.setText("Campo Obrigatório");
        } if(TextUtils.isEmpty(descricao.getText().toString())){
            descricao.setError("Campo Obrigatório");
        } if(TextUtils.isEmpty(localizacao.getText().toString())){
            descricao.setError("Campo Obrigatório");
        } else {
            UsuarioSingleton usrSingleton = new UsuarioSingleton();
            DenunciaDAO denunciaDAO = new DenunciaDAO(DenunciaActivity.this);
            denuncia.setDescricao(descricao.getText().toString());
            denuncia.setCategoria((Categoria) spinCategoria.getSelectedItem());
            denuncia.setDataHora();
            denuncia.setUsuario(usrSingleton.getInstance());
            denuncia.setLocalizacao(localizacao.getText().toString());
            if(denunciaDAO.salvar(denuncia, DenunciaActivity.this)){
                finish();
            } else {
                spinCategoria.setSelection(0);
                descricao.setText("");
                localizacao.setText("");
            }

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
