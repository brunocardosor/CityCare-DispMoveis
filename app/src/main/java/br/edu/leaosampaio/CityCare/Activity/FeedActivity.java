package br.edu.leaosampaio.CityCare.Activity;

import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.edu.leaosampaio.CityCare.R;


public class FeedActivity extends AppCompatActivity {

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);



        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mTabLayout.setTabTextColors(android.R.color.white,android.R.color.white);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setTitle("");

    }
}
