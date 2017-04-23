package DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import Modelo.Usuario;

/**
 * Created by lenilson on 21/04/17.
 */

public class UsuarioDAO extends GenericDAO<Usuario> {
    public UsuarioDAO(Context context) {
        super(context);
    }

    @Override
    public void salvar(Usuario usuario) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL("INSERT INTO usuario(nome_usuario,sobrenome_usuario,estado_usuario,cidade_usuario," +
                    "email_usuario,senha_usuario,status_usuario) VALUES('" + usuario.getNome() +
                    "','" + usuario.getSobrenome() + "','" + usuario.getEstado() +
                    "','" + usuario.getCidade() + "','" + usuario.getEmail() + "','" + usuario.getSenha() + ");");
            Toast.makeText(null, "Cadastro efetuado com sucesso!", Toast.LENGTH_SHORT).show();

        } catch (Exception ex){
            Log.e("SALVAR", ex.getMessage());
            Toast.makeText(null,"Não foi possível cadastrar seus dados.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void delete(Usuario usuario) {

    }

    @Override
    public void atualizar(Usuario usuario) {

    }


}
