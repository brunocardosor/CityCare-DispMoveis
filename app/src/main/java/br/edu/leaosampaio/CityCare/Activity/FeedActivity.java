package br.edu.leaosampaio.CityCare.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import br.edu.leaosampaio.CityCare.R;


public class FeedActivity extends Fragment {

    private FloatingActionButton floatWrite;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        floatWrite.findViewById(R.id.floatingMensagem);
        floatWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DenunciaActivity.class);
                startActivity(i);
            }
        });

        toolbar.findViewById(R.id.toolbarFeed);
        toolbar.setNavigationIcon(R.drawable.ic_list);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return inflater.inflate(R.layout.activity_postagens, container, false);
    }


}
