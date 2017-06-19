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
import br.edu.leaosampaio.CityCare.Activity.FeedActivity;
import br.edu.leaosampaio.CityCare.DAO.DenunciaDAO;
import br.edu.leaosampaio.CityCare.Fragments.PostagensFragment;
import br.edu.leaosampaio.CityCare.Modelo.Denuncia;
import br.edu.leaosampaio.CityCare.Modelo.UsuarioAplication;
import br.edu.leaosampaio.CityCare.R;

/**
 * Created by lab1-18 on 12/06/17.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostAdapterViewHolder> {

    private Context context;
    private List<Denuncia> denuncias;

    public PostAdapter(final List<Denuncia> denuncias, final Context context) {
        this.denuncias = denuncias;
        this.context = context;
    }


    @Override
    public PostAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_post_adapter, parent, false);
        PostAdapterViewHolder holder = new PostAdapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final PostAdapterViewHolder holder, final int position) {
        PostAdapterViewHolder holder1 = holder;
        final Denuncia denuncia = denuncias.get(position);

        holder1.tvUsuario.setText(denuncia.getUsuario().toString());
        holder1.tvData.setText(denuncia.getDataHora());
        holder1.tvCategoria.setText(denuncia.getCategoria().toString());
        holder1.tvDescricao.setText(denuncia.getDescricao());
        holder1.tvLocalizacao.setText(denuncia.getEndereco(context));
        holder1.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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
        if(denuncias.get(position).getUsuario().getId() != UsuarioAplication.getInstance().getUsuario().getId()){
            holder1.toolbar.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return denuncias.size();
    }

    public class PostAdapterViewHolder extends RecyclerView.ViewHolder {

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
            toolbar.inflateMenu(R.menu.menu_card_post);
        }
    }
}
