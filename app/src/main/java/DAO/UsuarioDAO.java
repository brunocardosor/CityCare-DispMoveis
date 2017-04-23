package DAO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Modelo.Usuario;
import Modelo.UsuarioSingleton;
import br.edu.leaosampaio.Activities.CadastroActivity;

/**
 * Created by lenilson on 21/04/17.
 */

public class UsuarioDAO extends GenericDAO<Usuario> {
    public UsuarioDAO(Context context) {
        super(context);
    }



    @Override
    public void salvar(Usuario usuario, Context context) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL("INSERT INTO usuario(nome_usuario,sobrenome_usuario,estado_usuario,cidade_usuario," +
                    "email_usuario,senha_usuario,status_usuario) VALUES('" + usuario.getNome() +
                    "','" + usuario.getSobrenome() + "','" + usuario.getEstado() +
                    "','" + usuario.getCidade() + "','" + usuario.getEmail() + "','" + usuario.getSenha() + "','1');");
            Log.i("SALVAR", "Salvo com sucesso");
            Toast.makeText(context, "Cadastro realizado com Sucesso", Toast.LENGTH_SHORT).show();

        } catch (Exception ex){
            Log.e("SALVAR", ex.getMessage());
            Toast.makeText(context, "Não foi possível realizar o cadastro", Toast.LENGTH_SHORT).show();

        }finally {
            db.close();
        }
    }

    @Override
    public void delete(Usuario usuario, Context context) {

        SQLiteDatabase db = getWritableDatabase();

        try{
            db.delete("usuario","id_usuario", new String[]{String.valueOf(usuario.getId())});
            Log.i("DELETE", "Usuário deletado");
            Toast.makeText(context, "Usuário deletado com Sucesso", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Log.e("DELETE", ex.getMessage());
            Toast.makeText(context, "ERRO!", Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }

    }

    @Override
    public void atualizar(Usuario usuario, Context context) {
        SQLiteDatabase db = getWritableDatabase();
        try{
            //db.update();
            Log.i("UPDATE", "Atualizado com Sucesso");
        } catch (Exception ex){
            Log.e("UPDATE", ex.getMessage());
        } finally {
            db.close();
        }
    }

    public boolean login(String email, String senha) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query("usuario", null, "email_usuario=" + email, null, null, null, null);
        List<Usuario> usuarios = listar(c);
            if (usuarios.size() == 1 && usuarios.get(0).getEmail().equals(email) && usuarios.get(0).getSenha().equals(senha)) {
                //verifica se o usuário está ativo
                if (usuarios.get(0).isStatus() == true) {
                    UsuarioSingleton us = new UsuarioSingleton();
                    us.setInstance(usuarios.get(0));
                    return true;
                } else
                    return false;
            } else
                return false;
        }

    private List<Usuario> listar(Cursor c) {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        if (c.moveToFirst()) {
            do {
                Usuario usuario = new Usuario();
                usuarios.add(usuario);
                usuario.setId(c.getLong(c.getColumnIndex("id_usuario")));
                usuario.setNome(c.getString(c.getColumnIndex("nome_usuario")));
                usuario.setSobrenome(c.getString(c.getColumnIndex("sobrenome_usuario")));
                usuario.setEmail(c.getString(c.getColumnIndex("email_usuario")));
                usuario.setSenha(c.getString(c.getColumnIndex("senha_usuario")));
                usuario.setEstado(c.getString(c.getColumnIndex("estado_usuario")));
                usuario.setCidade(c.getString(c.getColumnIndex("cidade_usuario")));
                if (c.getString(c.getColumnIndex("status_usuario")) == "1") {
                    usuario.setStatus(true);
                } else
                    usuario.setStatus(false);
            } while (c.moveToNext());
        }
        return usuarios;
    }
}
