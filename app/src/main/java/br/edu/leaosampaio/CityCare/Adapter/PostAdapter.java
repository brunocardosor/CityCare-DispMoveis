package br.edu.leaosampaio.CityCare.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import br.edu.leaosampaio.CityCare.Modelo.Denuncia;

/**
 * Created by lab1-18 on 12/06/17.
 */

public class PostAdapter extends RecyclerView.Adapter{

    private List<Denuncia> denuncias;

    public PostAdapter(List<Denuncia> denuncias){
            this.denuncias = denuncias;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return denuncias.size();
    }
}
