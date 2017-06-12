package br.edu.leaosampaio.CityCare.Activity;

import android.app.Fragment;
import android.app.FragmentBreadCrumbs;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import br.edu.leaosampaio.CityCare.R;


public class FeedActivity extends Fragment {

    Toolbar toolbar;
    FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        toolbar.findViewById(R.id.toolbarFeed);
        floatingActionButton.findViewById(R.id.toolbarFeed);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_list);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DenunciaActivity.class);
                startActivity(i);
            }
        });

        return inflater.inflate(R.layout.activity_postagens, container, false);
    }
}

