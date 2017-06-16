package br.edu.leaosampaio.CityCare.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.leaosampaio.CityCare.Adapter.PostAdapter;
import br.edu.leaosampaio.CityCare.Adapter.ProfileAdapter;
import br.edu.leaosampaio.CityCare.DAO.DenunciaDAO;
import br.edu.leaosampaio.CityCare.Modelo.Denuncia;
import br.edu.leaosampaio.CityCare.Modelo.Usuario;
import br.edu.leaosampaio.CityCare.Modelo.UsuarioAplication;
import br.edu.leaosampaio.CityCare.R;

/**
 * Created by lenilson on 15/06/17.
 */

public class ProfileFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private TextView txNome;
    private TextView txCidade;
    private TextView txEstado;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.post_profile_recyclerView);

        txNome = (TextView) view.findViewById(R.id.viewNome);
        txNome.setText(UsuarioAplication.getInstance().getUsuario().getNome());

        txCidade = (TextView) view.findViewById(R.id.viewCidade);
        txCidade.setText(UsuarioAplication.getInstance().getUsuario().getCidade());

        txEstado = (TextView) view.findViewById(R.id.viewEstado);
        txEstado.setText(UsuarioAplication.getInstance().getUsuario().getEstado());

        preencher();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        preencher();
    }

    private void preencher(){
        DenunciaDAO denunciaDAO = new DenunciaDAO(getActivity());

        List<Denuncia> denuncias = denunciaDAO.perfilPessoalDenuncias();

        recyclerView.setAdapter(new ProfileAdapter(denuncias, getActivity()));
    }
}
