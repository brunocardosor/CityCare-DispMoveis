package br.edu.leaosampaio.CityCare.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.edu.leaosampaio.CityCare.DAO.UsuarioDAO;
import br.edu.leaosampaio.CityCare.Modelo.Usuario;
import br.edu.leaosampaio.CityCare.Modelo.UsuarioAplication;
import br.edu.leaosampaio.CityCare.R;

public class AtualizarActivity extends AppCompatActivity {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; //String com sintaxe do Regex

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE); //Criando o padrão para o regex, definindo o caso como não sensitive


    private Usuario usuario = UsuarioAplication.getInstance().getUsuario();
    private Toolbar toolbar;
    private EditText edtNome;
    private EditText edtSobrenome;
    private Spinner edtSpinEstado;
    private Spinner edtSpinCidade;
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btDeletar;
    private Button btAtualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtSobrenome = (EditText) findViewById(R.id.edtSobrenome);
        edtSpinEstado = (Spinner) findViewById(R.id.edtSpinEstado);
        edtSpinCidade = (Spinner) findViewById(R.id.edtSpinCidade);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btAtualizar = (Button) findViewById(R.id.btAtualizar);
        btDeletar = (Button) findViewById(R.id.btDeletar);

        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle("Atualizar Perfil");

        final UsuarioDAO usrDao = new UsuarioDAO(this);
        List<String> estados = usrDao.listarEstados();
        ArrayAdapter<String> adapterEstados = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, estados);
        edtSpinEstado.setAdapter(adapterEstados);
        edtSpinEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    List<String> cidades = usrDao.listarCidades(i);
                    ArrayAdapter<String> adapterCidades = new ArrayAdapter<String>(AtualizarActivity.this, android.R.layout.simple_spinner_dropdown_item, cidades);
                    edtSpinCidade.setAdapter(adapterCidades);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String nome[] = usuario.getNome().split(" ");
        edtNome.setText(nome[0]);
        String sobrenome = nome[1];
        for (int i = 2; i < nome.length;i++){
            sobrenome += nome[i];
        }
        edtSobrenome.setText(sobrenome);

        edtEmail.setText(usuario.getEmail());
        edtSenha.setText(usuario.getSenha());

        btAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsuarioAtualizar();
                finish();
            }
        });

        btDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    if (usrDao.delete(usuario, AtualizarActivity.this)) {
                        Intent intent = new Intent(AtualizarActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    public void UsuarioAtualizar() {

        if (TextUtils.isEmpty(edtNome.getText().toString())) {
            edtNome.setError(getString(R.string.error_field_required));
        }

        if (TextUtils.isEmpty(edtSobrenome.getText().toString())) {
            edtSobrenome.setError(getString(R.string.error_field_required));
        }

        if (edtSpinEstado.getSelectedItemPosition() == 0) {
            TextView errorTextEstado = (TextView) edtSpinEstado.getSelectedView();
            errorTextEstado.setError(getString(R.string.error_field_required));
            errorTextEstado.setTextColor(Color.RED);
            errorTextEstado.setText(getString(R.string.error_field_required));
        }

        if (edtSpinCidade.getSelectedItemPosition() == 0) {
            TextView errorTextCidade = (TextView) edtSpinCidade.getSelectedView();
            errorTextCidade.setError(getString(R.string.error_field_required));
            errorTextCidade.setTextColor(Color.RED);
            errorTextCidade.setText(getString(R.string.error_field_required));
        }

        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setError(getString(R.string.error_field_required));
        } else if (!isEmailValid(edtEmail.getText().toString())) {
            edtEmail.setError("E-mail Inválido");
        }

        if (TextUtils.isEmpty(edtSenha.getText().toString())) {
            edtSenha.setError((getString(R.string.error_field_required)));
        } else if(!isPasswordValid(edtSenha.getText().toString())){
            edtSenha.setError("Senha Muito Curta. Min: 8 Dígitos");
        } else {
            UsuarioDAO usr = new UsuarioDAO(AtualizarActivity.this);
            Usuario user = new Usuario();
            user.setId(usuario.getId());
            user.setNome(edtNome.getText().toString() + " " + edtSobrenome.getText().toString());
            user.setEstado(edtSpinEstado.getSelectedItem().toString());
            user.setCidade(edtSpinCidade.getSelectedItem().toString());
            user.setEmail(edtEmail.getText().toString());
            user.setSenha(edtSenha.getText().toString());
            UsuarioAplication.getInstance().setUsuario(user);
            if(usr.atualizar(user,this)){
                finish();
            } else {
                edtNome.setText("");
                edtSobrenome.setText("");
                edtEmail.setText("");
                edtSenha.setText("");
                edtSpinCidade.setSelection(0);
                edtSpinEstado.setSelection(0);
            }

        }
    }

    private boolean isEmailValid(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 7;
    }

}

