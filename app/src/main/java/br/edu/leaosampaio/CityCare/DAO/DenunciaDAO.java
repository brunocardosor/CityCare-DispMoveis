package br.edu.leaosampaio.CityCare.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import br.edu.leaosampaio.CityCare.Modelo.Denuncia;

/**
 * Created by lab1-20 on 05/06/17.
 */

public class DenunciaDAO extends GenericDAO<Denuncia> {
    public DenunciaDAO(Context context) {
        super(context);
    }

    SQLiteDatabase db = getWritableDatabase();

    @Override
    public void salvar(Denuncia denuncia, Context c) {
        try{
            db.execSQL("INSERT INTO denuncia(denuncia_id_usuario, denuncia_id_categoria, denuncia_descricao, denuncia_data_hora)" +
                    "values(?,?,?,?)" +
                new Object[] {denuncia.getUsuario().getId(), denuncia.getCategoria().getId(), denuncia.getDescricao(),
                denuncia.getDataHora()});
            Log.i("SALVAR", "Salvo com Sucesso");
        } catch (Exception ex){
            Log.e("SALVAR", ex.getMessage());
            Toast.makeText(c, "ERRO!", Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }

    @Override
    public void delete(Denuncia denuncia, Context c) {
        try{
            db.delete("denuncia", "id_denuncia", new String[]{(String.valueOf(denuncia.getId()))});
            Toast.makeText(c, "Denuncia Deletada", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Log.e("DELETAR", ex.getMessage());
            Toast.makeText(c, "ERRO", Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }

    @Override
    public void atualizar(Denuncia denuncia, Context c) {

    }
}
