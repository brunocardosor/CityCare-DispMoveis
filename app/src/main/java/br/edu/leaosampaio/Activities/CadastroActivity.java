package br.edu.leaosampaio.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import DAO.UsuarioDAO;
import Modelo.Usuario;

public class CadastroActivity extends AppCompatActivity{

    private String estado[] = {"","Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal","Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará","Paraíba","Paraná","Pernambuco","Piauí","Rio de Janeiro","Rio Grande do Norte","Rio Grande do Sul","Rondônia","Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins"};
    private String cidade[] = {"","Acrelândia","Assis Brasil","Brasileia","Bujari","Capixaba","Cruzeiro do Sul","Epitaciolândia","Feijó","Jordão","Manoel Urbano","Marechal Thaumaturgo","Mâncio Lima","Plácido de Castro","Porto Acre","Porto Walter","Rio Branco","Rodrigues Alves","Santa Rosa do Purus","Sena Madureira","Senador Guiomard","Tarauacá","Xapuri"};
    private EditText txtNome;
    private EditText txtSobrenome;
    private Spinner spinEstado;
    private Spinner spinCidade;
    private EditText txtEmail;
    private EditText txtSenha;
    private Button cadastrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtNome = (EditText) findViewById(R.id.txtNome);
        txtSobrenome = (EditText) findViewById(R.id.txtSobrenome);
        spinEstado = (Spinner) findViewById(R.id.spinEstado);
        spinCidade = (Spinner) findViewById(R.id.spinCidade);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtSenha = (EditText) findViewById(R.id.txtSenha);

        cadastrar = (Button) findViewById(R.id.btCadastrar);
        cadastrar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsuarioDAO usr = new UsuarioDAO(CadastroActivity.this);
                Usuario user = new Usuario();
                user.setNome(txtNome.getText().toString());
                user.setSobrenome(txtSobrenome.getText().toString());
                user.setEstado(spinEstado.getSelectedItem().toString());
                user.setCidade(spinCidade.getSelectedItem().toString());
                user.setEmail(txtEmail.getText().toString());
                user.setSenha(txtSenha.getText().toString());
                usr.salvar(user);
                finish();
            }
        }));

        ArrayAdapter<String> estados = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,estado);
        spinEstado.setAdapter(estados);


        ArrayAdapter<String> cidades = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,cidade);
        spinCidade.setAdapter(cidades);

        if(spinEstado.getSelectedItem() != "") {
            spinCidade.setClickable(true);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cadastro");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffff8800")));
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
