package br.edu.leaosampaio.CityCare.Activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.edu.leaosampaio.CityCare.DAO.UsuarioDAO;
import br.edu.leaosampaio.CityCare.Modelo.Usuario;
import br.edu.leaosampaio.CityCare.Modelo.UsuarioAplication;
import br.edu.leaosampaio.CityCare.R;

public class CadastroActivity extends AppCompatActivity{


    private EditText txtNome;
    private EditText txtSobrenome;
    private Spinner spinEstado;
    private Spinner spinCidade;
    private EditText txtEmail;
    private EditText txtSenha;
    private Button cadastrar;
    private Toolbar toolbar;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; //String com sintaxe do Regex

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE); //Criando o padrão para o regex, definindo o caso como não sensitive


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button delete = (Button) findViewById(R.id.btDeletar);
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

        final UsuarioDAO usrDao = new UsuarioDAO(this);
        List<String> estados = usrDao.listarEstados();

        ArrayAdapter<String> adapterEstados = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,estados);
        spinEstado.setAdapter(adapterEstados);
        spinEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0){
                    List<String> cidades = usrDao.listarCidades(i);
                    ArrayAdapter<String> adapterCidades = new ArrayAdapter<String>(CadastroActivity.this, android.R.layout.simple_spinner_dropdown_item,cidades);
                    spinCidade.setAdapter(adapterCidades);
                    spinCidade.setClickable(true);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Cadastro");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(getIntent().hasExtra("usuario")) {
            final Usuario usuario = getIntent().getParcelableExtra("usuario");
            toolbar.setTitle("Atualizar Perfil");
            boolean encontrado = false;
            String[] nome = usuario.getNome().split(" ");
            txtNome.setText(nome[0]);
            String sobrenome = nome[1];
            if (2 < nome.length) {
                for (int i = 2; i < nome.length; i++) {
                    sobrenome += " " + nome[i];
                }
            }

            txtSobrenome.setText(sobrenome);

                for (int i = 0; i < spinEstado.getCount(); i++) {
                    String txt = spinEstado.getItemAtPosition(i).toString();
                    String txt2 = usuario.getEstado();
                    if (txt2 == txt) {
                        spinEstado.setSelection(i);
                        return;
                    }
                }

                    for (int i = 0; i < spinCidade.getCount(); i++) {
                        String txt = spinEstado.getItemAtPosition(i).toString();
                        String txt2 = usuario.getCidade();
                        if (txt2 == txt) {
                            spinCidade.setSelection(i);
                            return;
                        }
                txtEmail.setText(usuario.getEmail());
                txtSenha.setText(usuario.getSenha());
                delete.setVisibility(View.VISIBLE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroActivity.this);
                        builder.setMessage("Deseja mesmo excluir sua conta?");
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                UsuarioDAO usrDAO = new UsuarioDAO(CadastroActivity.this);
                                usrDAO.delete(usuario, CadastroActivity.this);
                                UsuarioAplication.getInstance().setUsuario(null);
                                finish();
                            }
                        });

                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                    }
                });
            }
        }
    }

    public void UsuarioCadastrar() {

        if (TextUtils.isEmpty(txtNome.getText().toString())) {
            txtNome.setError(getString(R.string.error_field_required));
        }

        if (TextUtils.isEmpty(txtSobrenome.getText().toString())) {
            txtSobrenome.setError(getString(R.string.error_field_required));
        }

        if (spinEstado.getSelectedItemPosition() == 0) {
            TextView errorTextEstado = (TextView) spinEstado.getSelectedView();
            errorTextEstado.setError(getString(R.string.error_field_required));
            errorTextEstado.setTextColor(Color.RED);
            errorTextEstado.setText(getString(R.string.error_field_required));
        }

        if (spinCidade.getSelectedItemPosition() == 0) {
            TextView errorTextCidade = (TextView) spinCidade.getSelectedView();
            errorTextCidade.setError(getString(R.string.error_field_required));
            errorTextCidade.setTextColor(Color.RED);
            errorTextCidade.setText(getString(R.string.error_field_required));
        }

        if (TextUtils.isEmpty(txtEmail.getText().toString())) {
            txtEmail.setError(getString(R.string.error_field_required));
        } else if (!isEmailValid(txtEmail.getText().toString())) {
            txtEmail.setError("E-mail Inválido");
        }

        if (TextUtils.isEmpty(txtSenha.getText().toString())) {
            txtSenha.setError((getString(R.string.error_field_required)));
        } else if(!isPasswordValid(txtSenha.getText().toString())){
            txtSenha.setError("Senha Muito Curta. Min: 8 Dígitos");
        } else {
            UsuarioDAO usr = new UsuarioDAO(CadastroActivity.this);
            Usuario user = new Usuario();
            user.setNome(txtNome.getText().toString() + " " + txtSobrenome.getText().toString());
            user.setEstado(spinEstado.getSelectedItem().toString());
            user.setCidade(spinCidade.getSelectedItem().toString());
            user.setEmail(txtEmail.getText().toString());
            user.setSenha(txtSenha.getText().toString());
            if(usr.salvar(user,this)){
                finish();
            } else {
                txtNome.setText("");
                txtSobrenome.setText("");
                txtEmail.setText("");
                txtSenha.setText("");
                spinCidade.setSelection(0);
                spinEstado.setSelection(0);
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
