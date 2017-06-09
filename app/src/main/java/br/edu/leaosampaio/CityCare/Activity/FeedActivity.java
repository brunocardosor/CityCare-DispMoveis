package br.edu.leaosampaio.CityCare.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.edu.leaosampaio.CityCare.R;


public class FeedActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private FloatingActionButton floatWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);



        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mTabLayout.setTabTextColors(android.R.color.white,android.R.color.white);

        floatWrite = (FloatingActionButton) findViewById(R.id.floatingMensagem);

        floatWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FeedActivity.this, DenunciaActivity.class);
                startActivity(i);
            }
        });


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setTitle("");

    }
}
