package br.edu.leaosampaio.CityCare.Adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.edu.leaosampaio.CityCare.DAO.DenunciaDAO;
import br.edu.leaosampaio.CityCare.Modelo.Denuncia;
import br.edu.leaosampaio.CityCare.R;

/**
 * Created by lab1-18 on 12/06/17.
 */

public class PostagensFragment extends AppCompatActivity {

    DenunciaDAO denunciaDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postagem_fragment);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.post_recyclerView);

        List<Denuncia> denuncias = denunciaDAO.buscar();

        recyclerView.setAdapter( new PostAdapter(denuncias));
    }
}
