package br.edu.leaosampaio.CityCare.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.leaosampaio.CityCare.Modelo.Usuario;
import br.edu.leaosampaio.CityCare.Modelo.UsuarioAplication;

/**
 * Created by lenilson on 21/04/17.
 */

public class UsuarioDAO extends GenericDAO<Usuario> {
    public UsuarioDAO(Context context) {
        super(context);
    }

    UsuarioAplication usuarioAplication = UsuarioAplication.getInstance();
    SQLiteDatabase db = getWritableDatabase();

    @Override
    public boolean salvar(Usuario usuario, Context context) {

        try {
            db.execSQL("INSERT INTO usuario(nome_usuario,estado_usuario,cidade_usuario," +
                    "email_usuario,senha_usuario,status_usuario) VALUES('" + usuario.getNome() +
                    "','" + usuario.getEstado() + "','" + usuario.getCidade() +
                    "','" + usuario.getEmail() + "','" + usuario.getSenha() + "','1');");
            Log.i("SALVAR", "Salvo com sucesso");
            Toast.makeText(context,"Cadastro efetuado com sucesso",Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception ex){
            Log.e("SALVAR", ex.getMessage());
            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        }finally {
            db.close();
        }
    }

    @Override
    public boolean delete(Usuario usuario, Context context) {
            try {
                db.rawQuery("DELETE FROM usuario WHERE id_usuario='"+ usuario.getId() + "'",null);
                Log.i("DELETE", "Usuário deletado");
                Toast.makeText(context, "Usuario Deletado", Toast.LENGTH_SHORT).show();
                return true;
            } catch (Exception ex) {
                Log.e("DELETE", ex.getMessage());
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

    public boolean atualizar(Usuario usuario, Context c) {
        try{
            db.execSQL("UPDATE usuario SET nome_usuario='"+ usuario.getNome() +"'," +
                    " email_usuario='" + usuario.getEmail() +"'," +
                    "senha_usuario='"+usuario.getSenha() + "'," +
                    "estado_usuario='" + usuario.getEstado() + "'," +
                    "cidade_usuario='" + usuario.getCidade() +"' WHERE id_usuario='"+usuario.getId()+"'" );
            Toast.makeText(c,"Usuario Atualizado", Toast.LENGTH_LONG).show();
            return true;
        }catch (Exception ex){
            Log.e("ATUALIZAR", ex.getMessage());
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean login(String email, String senha) {
            Cursor c = db.query("usuario", null, "email_usuario='" + email + "'", null, null, null, null);
            List<Usuario> usuarios = listar(c);
            if (usuarios.size() == 1 && usuarios.get(0).getEmail().equals(email) && usuarios.get(0).getSenha().equals(senha)) {
                //verifica se o usuário está ativo
                if (usuarios.get(0).isStatus() == true) {
                    usuarioAplication.setUsuario(usuarios.get(0));
                    return true;
                } return false;
            } return false;
    }


    private List<Usuario> listar(Cursor c) {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        if (c.moveToFirst()) {
            do {
                Usuario usuario = new Usuario();
                usuarios.add(usuario);
                usuario.setId(c.getLong(c.getColumnIndex("id_usuario")));
                usuario.setNome(c.getString(c.getColumnIndex("nome_usuario")));
                usuario.setEmail(c.getString(c.getColumnIndex("email_usuario")));
                usuario.setSenha(c.getString(c.getColumnIndex("senha_usuario")));
                usuario.setEstado(c.getString(c.getColumnIndex("estado_usuario")));
                usuario.setCidade(c.getString(c.getColumnIndex("cidade_usuario")));
                if (c.getInt(c.getColumnIndex("status_usuario")) == 1) {
                    usuario.setStatus(true);
                } else
                    usuario.setStatus(false);
            } while (c.moveToNext());
        }
        return usuarios;
    }


    public List<String> listarCidades(int posicao){
        Cursor c = db.query("cidade", null, "cidade_id_estado='" + posicao + "'", null,null,null,null);
        List<String> cidades = new ArrayList<String>();
        cidades.add("Cidade");
        if(c.moveToFirst()){
            do {
                String cidade = c.getString(c.getColumnIndex("cidade"));
                cidades.add(cidade);
            }while (c.moveToNext());
        }
        return cidades;
    }
    public List<String> listarEstados(){
        Cursor c = db.query("estado",null,null,null,null,null, null);
        List<String> estados = new ArrayList<String>();
        estados.add("Estado");
        if(c.moveToFirst()){
            do {
                String estado;
                estado = c.getString(c.getColumnIndex("estado"));
                estados.add(estado);

            }while (c.moveToNext());
        }
        return estados;
    }
}
