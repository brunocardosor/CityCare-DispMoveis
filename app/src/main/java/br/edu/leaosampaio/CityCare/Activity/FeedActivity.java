package br.edu.leaosampaio.CityCare.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import br.edu.leaosampaio.CityCare.Fragments.PostagensFragment;
import br.edu.leaosampaio.CityCare.R;


public class FeedActivity extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_feed);

        tabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbarFeed);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingMensagem);

        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.citycare_logo_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_list);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FeedActivity.this, DenunciaActivity.class);
                startActivity(i);
            }
        });
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_bar).getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}

