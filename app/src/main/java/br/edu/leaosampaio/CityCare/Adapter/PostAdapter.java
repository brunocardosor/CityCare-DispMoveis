package br.edu.leaosampaio.CityCare.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.leaosampaio.CityCare.DAO.DenunciaDAO;
import br.edu.leaosampaio.CityCare.Modelo.Denuncia;
import br.edu.leaosampaio.CityCare.R;

/**
 * Created by lab1-18 on 12/06/17.
 */

public class PostAdapter extends RecyclerView.Adapter{

    private List<Denuncia> denuncias;
    private Context context;


    public PostAdapter(List<Denuncia> denuncias, Context context){
            this.denuncias = denuncias;
            this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_post_adapter, parent, false);
        PostAdapterViewHolder holder = new PostAdapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PostAdapterViewHolder holder1 = (PostAdapterViewHolder) holder;
        final Denuncia denuncia = denuncias.get(position);

        holder1.tvUsuario.setText(denuncia.getUsuario().toString());
        holder1.tvData.setText(denuncia.getDataHora());
        holder1.tvCategoria.setText(denuncia.getCategoria().toString());
        holder1.tvDescricao.setText(denuncia.getDescricao());
        holder1.tvLocalizacao.setText(denuncia.getLocalizacao());
        holder1.toolbar.inflateMenu(R.menu.menu_card_post);
        holder1.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.editPost){


                } if(item.getItemId() == R.id.deletePost){

                    AlertDialog.Builder bilder = new AlertDialog.Builder(context);
                    bilder.setMessage("Deseja apagar esta denúncia?");

                    bilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    bilder.setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DenunciaDAO denunciaDAO = new DenunciaDAO(context);
                            denunciaDAO.delete(denuncia,context);
                            notifyDataSetChanged();
                        }
                    });
                    bilder.show();
                }

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return denuncias.size();
    }

    public class PostAdapterViewHolder extends RecyclerView.ViewHolder{

        final TextView tvUsuario;
        final TextView tvData;
        final TextView tvCategoria;
        final TextView tvDescricao;
        final TextView tvLocalizacao;
        final Toolbar toolbar;

        public PostAdapterViewHolder(View view) {
            super(view);

            tvUsuario = (TextView) view.findViewById(R.id.tvUsuario);
            tvData = (TextView) view.findViewById(R.id.tvData);
            tvCategoria = (TextView) view.findViewById(R.id.tvCategoria);
            tvDescricao = (TextView) view.findViewById(R.id.tvDescricao);
            tvLocalizacao = (TextView) view.findViewById(R.id.tvLocalizacao);
            toolbar = (Toolbar) view.findViewById(R.id.toolbarCard);
        }
    }

    public void deletePost(MenuItem item) {

    }

    public void editPost(MenuItem item) {
    }
}
