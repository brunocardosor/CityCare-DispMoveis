package br.edu.leaosampaio.CityCare.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.leaosampaio.CityCare.Modelo.Categoria;

/**
 * Created by lenilson on 10/06/17.
 */

public class CategoriaDAO extends GenericDAO<Categoria> {
    public CategoriaDAO(Context context) {
        super(context);
    }

    SQLiteDatabase db = getWritableDatabase();

    @Override
    public boolean salvar(Categoria categoria, Context c) {
        try{
            db.execSQL("INSERT INTO categoria(descricao_categoria) values("+ categoria.getDescricao() +");");
            Log.i("CATEGORIA", "Categoria Salva com Sucesso");
            Toast.makeText(c, "Categoria Salva com Sucesso", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception ex) {
            Log.e("CATEGORIA", ex.getMessage());
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        } finally {
            db.close();
        }

    }

    @Override
    public boolean delete(Categoria categoria, Context c) {
        try{
            db.delete("categoria", "id_categoria", new String[]{String.valueOf(categoria.getId())});
            Log.i("CATEGORIA", "Categoria Deletada com Sucesso");
            Toast.makeText(c, "Categoria Deletada", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception ex) {
            Log.e("CATEGORIA", ex.getMessage());
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        } finally {
            db.close();
        }
    }

    @Override
    public boolean atualizar(Categoria categoria, Context c) {
        return false;
    }

    public List<Categoria> buscar(){
        Cursor c = db.rawQuery("SELECT * FROM categoria", null);
        return listar(c);
    }

    private List<Categoria> listar(Cursor c) {
        List<Categoria> categorias = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                Categoria categoria = new Categoria();
                categorias.add(categoria);
                categoria.setId(c.getLong(c.getColumnIndex("id_categoria")));
                categoria.setDescricao(c.getString((c.getColumnIndex("descricao_categoria"))));

            } while (c.moveToNext());
        }
        return categorias;
    }
}
