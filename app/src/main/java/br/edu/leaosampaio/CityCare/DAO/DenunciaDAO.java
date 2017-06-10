package br.edu.leaosampaio.CityCare.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import br.edu.leaosampaio.CityCare.Activity.DenunciaActivity;
import br.edu.leaosampaio.CityCare.Modelo.Categoria;
import br.edu.leaosampaio.CityCare.Modelo.Denuncia;
import br.edu.leaosampaio.CityCare.Modelo.UsuarioSingleton;

/**
 * Created by lab1-20 on 05/06/17.
 */

public class DenunciaDAO extends GenericDAO<Denuncia> {
    public DenunciaDAO(Context context) {
        super(context);
    }

    SQLiteDatabase db = getWritableDatabase();

    @Override
    public boolean salvar(Denuncia denuncia, Context c) {
        try{
            db.execSQL("INSERT INTO denuncia(denuncia_id_usuario, denuncia_id_categoria, denuncia_descricao, denuncia_localizacao, denuncia_data_hora)" +
                    "values(?,?,?,?,?)" +
                new Object[] {denuncia.getUsuario().getId(), denuncia.getCategoria().getId(), denuncia.getDescricao(), denuncia.getLocalizacao(),
                denuncia.getDataHora()});
            Log.i("SALVAR", "Salvo com Sucesso");
            return true;
        } catch (Exception ex){
            Log.e("SALVAR", ex.getMessage());
            Toast.makeText(c, "ERRO!", Toast.LENGTH_SHORT).show();
            return false;
        } finally {
            db.close();
        }
    }

    public List<Denuncia> listar(){
        return null;
    }

    @Override
    public boolean delete(Denuncia denuncia, Context c) {
        try{
            db.delete("denuncia", "id_denuncia", new String[]{(String.valueOf(denuncia.getId()))});
            Toast.makeText(c, "Denuncia Deletada", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception ex) {
            Log.e("DELETAR", ex.getMessage());
            Toast.makeText(c, "ERRO", Toast.LENGTH_SHORT).show();
            return false;
        } finally {
            db.close();
        }
    }

    @Override
    public boolean atualizar(Denuncia denuncia, Context c) {
        return false;
    }
}
