package br.edu.leaosampaio.CityCare.Fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.leaosampaio.CityCare.DAO.DenunciaDAO;
import br.edu.leaosampaio.CityCare.Modelo.Denuncia;
import br.edu.leaosampaio.CityCare.R;

/**
 * Created by lenilson on 12/06/17.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostsViewHolder>{

    private final Context context;
    DenunciaDAO denunciaDAO = new DenunciaDAO(null);
    private List<Denuncia> denuncias = denunciaDAO.buscar();

    public PostAdapter(List<Denuncia> denuncias, Context context) {
        this.denuncias = denuncias;
        this.context = context;
    }


    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_post_adapter, parent, false);
        PostsViewHolder postsViewHolder = new PostsViewHolder(view);
        return postsViewHolder;
    }

    @Override
    public void onBindViewHolder(PostsViewHolder holder, int position) {
        Denuncia d = denuncias.get(position);
        holder.tvUsuario.setText(d.getUsuario().toString());
        holder.tvData.setText(d.getDataHora());
        holder.tvCategoria.setText(d.getCategoria().toString());
        holder.tvDescricao.setText(d.getDescricao());

    }

    @Override
    public int getItemCount() {
        return this.denuncias != null ? this.denuncias.size() : 0;
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView tvUsuario;
        TextView tvData;
        TextView tvCategoria;
        TextView tvDescricao;



        public PostsViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardPost);
            tvUsuario = (TextView) itemView.findViewById(R.id.tvUsuario);
            tvData = (TextView) itemView.findViewById(R.id.tvData);
            tvCategoria = (TextView) itemView.findViewById(R.id.tvCategoria);
            tvDescricao = (TextView) itemView.findViewById(R.id.tvDescricao);
        }
    }
}
