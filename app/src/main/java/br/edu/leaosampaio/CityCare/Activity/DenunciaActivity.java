package br.edu.leaosampaio.CityCare.Activity;

import android.graphics.Color;
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
    private EditText localizacao;

    DenunciaDAO denunciaDAO;
    Denuncia den = new Denuncia();
    UsuarioAplication usuarioAplication = UsuarioAplication.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

        spinCategoria = (Spinner) findViewById(R.id.spinCategoria);
        descricao = (EditText) findViewById(R.id.edtDescricao);
        localizacao = (EditText) findViewById(R.id.edtLocalizacao);
        btEnviar = (ImageButton) findViewById(R.id.btEnviar);

        CategoriaDAO categorias = new CategoriaDAO(this);
        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(DenunciaActivity.this, android.R.layout.simple_spinner_dropdown_item, categorias.buscar());
        spinCategoria.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Denunciar");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarDenuncia(den);
            }
        });

        localizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DenunciaActivity.this, "ABRIRÁ O MAPA", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void cadastrarDenuncia(Denuncia denuncia) {
        if (spinCategoria.getSelectedItemId() == 0) {
            TextView errorTextView = (TextView) spinCategoria.getSelectedView();
            errorTextView.setError("Campo Obrigatório");
            errorTextView.setTextColor(Color.RED);
            errorTextView.setText("Campo Obrigatório");
        } if(TextUtils.isEmpty(descricao.getText().toString())){
            descricao.setError("Campo Obrigatório");
        } if(TextUtils.isEmpty(localizacao.getText().toString())){
            localizacao.setError("Campo Obrigatório");
        } else {
            denunciaDAO = new DenunciaDAO(DenunciaActivity.this);
            denuncia.setDescricao(descricao.getText().toString());
            denuncia.setCategoria((Categoria) spinCategoria.getSelectedItem());
            denuncia.setDataHora();
            denuncia.setUsuario(usuarioAplication.getUsuario());
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
}
