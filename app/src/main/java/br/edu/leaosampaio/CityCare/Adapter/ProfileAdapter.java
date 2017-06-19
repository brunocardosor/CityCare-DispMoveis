package br.edu.leaosampaio.CityCare.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.leaosampaio.CityCare.Activity.DenunciaActivity;
import br.edu.leaosampaio.CityCare.DAO.DenunciaDAO;
import br.edu.leaosampaio.CityCare.Modelo.Denuncia;
import br.edu.leaosampaio.CityCare.R;

/**
 * Created by lab1-18 on 16/06/17.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileAdapterViewHolder> {

    private List<Denuncia> denuncias;
    private Context context;

    public ProfileAdapter(List<Denuncia> denuncias, Context context){
        this.denuncias = denuncias;
        this.context = context;
    }

    @Override
    public ProfileAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_post_adapter, parent,false);

        ProfileAdapterViewHolder holder = new ProfileAdapterViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ProfileAdapterViewHolder holder, final int position) {
        ProfileAdapterViewHolder view = holder;
        final Denuncia denuncia = denuncias.get(position);

        view.tvUsuario.setText(denuncia.getUsuario().toString());
        view.tvData.setText(denuncia.getDataHora());
        view.tvCategoria.setText(denuncia.getCategoria().toString());
        view.tvDescricao.setText(denuncia.getDescricao());
        view.tvLocalizacao.setText(denuncia.getEndereco(context));
        view.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.editPost) {
                    Intent i = new Intent(context, DenunciaActivity.class);
                    i.putExtra("denuncia", denuncia);
                    i.putExtra("categoria", denuncia.getCategoria());
                    context.startActivity(i);
                }
                if (item.getItemId() == R.id.deletePost) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Deseja apagar esta denúncia?");

                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DenunciaDAO denunciaDAO = new DenunciaDAO(context);
                            denunciaDAO.delete(denuncias.get(position), context);
                            denuncias.remove(position);
                            notifyDataSetChanged();
                            denuncias = denunciaDAO.feedDenuncias();
                            notifyDataSetChanged();
                        }
                    });
                    builder.show();
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProfileAdapterViewHolder extends RecyclerView.ViewHolder{

        final TextView tvUsuario;
        final TextView tvData;
        final TextView tvCategoria;
        final TextView tvDescricao;
        final TextView tvLocalizacao;
        final Toolbar toolbar;


        public ProfileAdapterViewHolder(View view) {
            super(view);

            tvUsuario = (TextView) view.findViewById(R.id.tvUsuario);
            tvData = (TextView) view.findViewById(R.id.tvData);
            tvCategoria = (TextView) view.findViewById(R.id.tvCategoria);
            tvDescricao = (TextView) view.findViewById(R.id.tvDescricao);
            tvLocalizacao = (TextView) view.findViewById(R.id.tvLocalizacao);
            toolbar = (Toolbar) view.findViewById(R.id.toolbarCard);
            toolbar.inflateMenu(R.menu.menu_card_post);

        }
    }
}
