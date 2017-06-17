package br.edu.leaosampaio.CityCare.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.transition.TransitionManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.edu.leaosampaio.CityCare.Adapter.TabAdapter;
import br.edu.leaosampaio.CityCare.Fragments.PostagensFragment;
import br.edu.leaosampaio.CityCare.Modelo.Usuario;
import br.edu.leaosampaio.CityCare.Modelo.UsuarioAplication;
import br.edu.leaosampaio.CityCare.R;


public class FeedActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected FloatingActionButton floatingActionButton;
    protected TabLayout tabLayout;
    protected DrawerLayout drawerLayout;
    protected TextView txNome;
    protected TextView txCidade;
    protected TextView txEstado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (UsuarioAplication.getInstance().getUsuario() != null) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_feed);

            tabLayout = (TabLayout) findViewById(R.id.mTabLayout);
            toolbar = (Toolbar) findViewById(R.id.toolbarFeed);
            floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingMensagem);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.mTabLayout);
            tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_home));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_map));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_profile));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
            viewPager.setOffscreenPageLimit(2);
            final TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

            View view = navView.getHeaderView(0);
            txCidade = (TextView) view.findViewById(R.id.userCidade);
            txEstado = (TextView) view.findViewById(R.id.userEstado);
            txNome = (TextView) view.findViewById(R.id.userNome);

            txCidade.setText(UsuarioAplication.getInstance().getUsuario().getCidade());
            txEstado.setText(UsuarioAplication.getInstance().getUsuario().getEstado());
            txNome.setText(UsuarioAplication.getInstance().getUsuario().getNome());

            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.mipmap.ic_action_list);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });


            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(FeedActivity.this, DenunciaActivity.class);
                    startActivity(i);
                }
            });
        } else {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }




    public boolean onPrepareOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_bar).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    public void onLogoutListener(MenuItem item) {
        UsuarioAplication.getInstance().setUsuario(null);
        Intent i = new Intent(FeedActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void onProfileUpdateListener(MenuItem item) {
        Intent i = new Intent(FeedActivity.this, CadastroActivity.class);
        i.putExtra("usuario", UsuarioAplication.getInstance().getUsuario());
        startActivity(i);
    }
}

