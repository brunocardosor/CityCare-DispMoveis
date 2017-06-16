package br.edu.leaosampaio.CityCare.DAO;

import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.leaosampaio.CityCare.Modelo.Categoria;
import br.edu.leaosampaio.CityCare.Modelo.Denuncia;
import br.edu.leaosampaio.CityCare.Modelo.Usuario;
import br.edu.leaosampaio.CityCare.Modelo.UsuarioAplication;

/**
 * Created by lab1-20 on 05/06/17.
 */

public class DenunciaDAO extends GenericDAO<Denuncia> {
    public DenunciaDAO(Context context) {
        super(context);
    }

    final SQLiteDatabase db = getWritableDatabase();

    @Override
    public boolean salvar(Denuncia denuncia, Context c) {
        if(denuncia.getId() == null) {
            try {
                db.execSQL("INSERT INTO denuncia(denuncia_id_usuario, denuncia_id_categoria, denuncia_descricao, denuncia_localizacao, denuncia_data_hora)" +
                        "values('" + denuncia.getUsuario().getId() + "'," +
                        "'" + denuncia.getCategoria().getId() + "'," +
                        "'" + denuncia.getDescricao() + "'," +
                        "'" + denuncia.getLocalizacao() + "'," +
                        "'" + denuncia.getDataHora() + "')");
                Log.i("SALVAR", "Salvo com Sucesso");
                Toast.makeText(c, "SALVO", Toast.LENGTH_SHORT).show();
                return true;
            } catch (Exception ex) {
                Log.e("SALVAR", ex.getMessage());
                Toast.makeText(c, "ERRO!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            atualizar(denuncia,c);
        }
        return false;
    }

    public List<Denuncia> perfilPessoalDenuncias(){
        Usuario usuario = UsuarioAplication.getInstance().getUsuario();
        Cursor c = db.rawQuery("SELECT * FROM denuncia a INNER JOIN categoria b" +
                " ON a.denuncia_id_categoria = b.id_categoria INNER JOIN usuario c ON a.denuncia_id_usuario = c.id_usuario " +
                "WHERE denuncia_id_usuario='" + usuario.getId() +"' ORDER BY id_denuncia DESC ",null);
        return listar(c);
    }

    public List<Denuncia> perfilDenuncias(Usuario usuario){
        Cursor c = db.rawQuery("SELECT * FROM denuncia a INNER JOIN categoria b" +
                " ON a.denuncia_id_categoria = b.id_categoria INNER JOIN usuario c ON a.denuncia_id_usuario = c.id_usuario " +
                "WHERE denuncia_id_usuario='"+ usuario.getId() +"' ORDER BY id_denuncia DESC ", null);
        return listar(c);
    }

    public List<Denuncia> feedDenuncias(){
        Cursor c = db.rawQuery("SELECT * FROM denuncia a INNER JOIN categoria b " +
                "ON a.denuncia_id_categoria = b.id_categoria INNER JOIN usuario c ON a.denuncia_id_usuario = c.id_usuario " +
                "ORDER BY id_denuncia DESC", null);
        return listar(c);
    }

    private List<Denuncia> listar(Cursor c){
        List<Denuncia> denuncias = new ArrayList<Denuncia>();
        if(c != null){
            if(c.moveToFirst()) {
                do {
                    Denuncia denuncia = new Denuncia();
                    Categoria categoria = new Categoria();
                    Usuario usuario = new Usuario();

                    denuncias.add(denuncia);

                    denuncia.setId(c.getLong(c.getColumnIndex("id_denuncia")));
                    denuncia.setDescricao(c.getString(c.getColumnIndex("denuncia_descricao")));
                    denuncia.setLocalizacao(c.getString(c.getColumnIndex("denuncia_localizacao")));
                    denuncia.setDataHora(c.getString(c.getColumnIndex("denuncia_data_hora")));

                    categoria.setId(c.getLong(c.getColumnIndex("id_categoria")));
                    categoria.setDescricao(c.getString(c.getColumnIndex("descricao_categoria")));

                    usuario.setId(c.getLong(c.getColumnIndex("id_usuario")));
                    usuario.setNome(c.getString(c.getColumnIndex("nome_usuario")));
                    usuario.setCidade(c.getString(c.getColumnIndex("cidade_usuario")));
                    usuario.setEstado(c.getString(c.getColumnIndex("estado_usuario")));
                    if(c.getInt(c.getColumnIndex("status_usuario")) == 1){
                        usuario.setStatus(true);
                    } else {
                        usuario.setStatus(false);
                    }

                    denuncia.setCategoria(categoria);
                    denuncia.setUsuario(usuario);

                } while (c.moveToNext());
            }   return denuncias;
        }   return null;
    }

    @Override
    public boolean delete(Denuncia denuncia, Context c) {
        try{
            db.execSQL("DELETE FROM denuncia WHERE id_denuncia='"+ denuncia.getId().toString() +"';");
            Toast.makeText(c, "Denuncia Deletada", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception ex) {
            Log.e("DELETAR", ex.getMessage());
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean atualizar(Denuncia denuncia, Context c){
        try{
            db.execSQL("UPDATE denuncia SET deuncia_id_categoria = '"+ denuncia.getCategoria().getId() +"'," +
                    "denuncia_descricao = '" + denuncia.getDescricao() +"'," +
                    "denuncia_localizacao ='"+ denuncia.getLocalizacao()+"' WHERE id_denuncia='"+ denuncia.getId() +"')");
            Toast.makeText(c, "Denuncia Atualizada com Sucesso", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception ex) {
            Log.e("Atualizar", ex.getMessage());
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
