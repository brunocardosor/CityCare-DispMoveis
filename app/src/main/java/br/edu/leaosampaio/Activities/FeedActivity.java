package br.edu.leaosampaio.Activities;

import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;


public class FeedActivity extends AppCompatActivity {

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        int cor = getBaseContext().getResources().getColor(android.R.color.white);

        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mTabLayout.setTabTextColors(cor,cor);

        ActionBar actionBar = getSupportActionBar();

    }
}
