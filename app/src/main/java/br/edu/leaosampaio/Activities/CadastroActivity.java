package br.edu.leaosampaio.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import DAO.UsuarioDAO;
import Modelo.Usuario;

public class CadastroActivity extends AppCompatActivity{

    private String estado[] = {"Estado","Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal","Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará","Paraíba","Paraná","Pernambuco","Piauí","Rio de Janeiro","Rio Grande do Norte","Rio Grande do Sul","Rondônia","Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins"};
    private String cidade[] = {"Cidade","Acrelândia","Assis Brasil","Brasileia","Bujari","Capixaba","Cruzeiro do Sul","Epitaciolândia","Feijó","Jordão","Manoel Urbano","Marechal Thaumaturgo","Mâncio Lima","Plácido de Castro","Porto Acre","Porto Walter","Rio Branco","Rodrigues Alves","Santa Rosa do Purus","Sena Madureira","Senador Guiomard","Tarauacá","Xapuri"};
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
            public void onClick(View view) { UsuarioCadastrar();
            }
        }));

        ArrayAdapter<String> estados = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,estado);
        spinEstado.setAdapter(estados);

        ArrayAdapter<String> cidades = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,cidade);
        spinCidade.setAdapter(cidades);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cadastro");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffff8800")));
    }

    public void UsuarioCadastrar(){

        if(TextUtils.isEmpty(txtNome.getText().toString())){
            txtNome.setError(getString(R.string.error_field_required));
        }

        if(TextUtils.isEmpty(txtSobrenome.getText().toString())){
            txtSobrenome.setError(getString(R.string.error_field_required));
        }

        if(spinEstado.getSelectedItemPosition() == 0){
            TextView errorTextEstado = (TextView) spinEstado.getSelectedView();
            errorTextEstado.setError(getString(R.string.error_field_required));
            errorTextEstado.setTextColor(Color.RED);
            errorTextEstado.setText(getString(R.string.error_field_required));
        }

        if(spinCidade.getSelectedItemPosition() == 0){
            TextView errorTextCidade = (TextView) spinCidade.getSelectedView();
            errorTextCidade.setError(getString(R.string.error_field_required));
            errorTextCidade.setTextColor(Color.RED);
            errorTextCidade.setText(getString(R.string.error_field_required));
        }

        if(TextUtils.isEmpty(txtEmail.getText().toString())){
            txtEmail.setError(getString(R.string.error_field_required));
        }

        if(TextUtils.isEmpty(txtSenha.getText().toString())){
            txtSenha.setError((getString(R.string.error_field_required)));
        }

 else {
            UsuarioDAO usr = new UsuarioDAO(CadastroActivity.this);
            Usuario user = new Usuario();
            user.setNome(txtNome.getText().toString() + " " + txtSobrenome.getText().toString());
            user.setEstado(spinEstado.getSelectedItem().toString());
            user.setCidade(spinCidade.getSelectedItem().toString());
            user.setEmail(txtEmail.getText().toString());
            user.setSenha(txtSenha.getText().toString());
            usr.salvar(user,this);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (spinEstado.getSelectedItemPosition() != 0){
            spinCidade.setClickable(true);
        }


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
