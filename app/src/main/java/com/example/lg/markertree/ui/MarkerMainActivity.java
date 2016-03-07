package com.example.lg.markertree.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lg.markertree.R;
import com.example.lg.markertree.ui.CategoryList.CategoryListFragment;
import com.example.lg.markertree.ui.mainList.MyListFragment;

public class MarkerMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Context mContext;
    TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this;
        userName = (TextView)findViewById(R.id.userName);

        //첫 fragment 설정
        android.support.v4.app.Fragment first= MyListFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, first);
        ft.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.marker_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,WriteActivity.class);
            startActivity(intent);
            //액티비티 호출
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Navigation bar 불러오기
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
            android.support.v4.app.Fragment first= MyListFragment.newInstance();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, first);
            ft.commit();


        } else if (id == R.id.music) {
            callFragment("음악");

        } else if (id == R.id.sports) {
            callFragment("운동");

        } else if (id == R.id.art) {
            callFragment("예술");

        } else if (id == R.id.game) {
            callFragment("게임");

        } else if (id == R.id.book) {
            callFragment("독서");
        }else if (id == R.id.study) {
            callFragment("공부");
        }else if (id == R.id.etc) {
            callFragment("기타");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void callFragment(String favorite){

        android.support.v4.app.Fragment first= CategoryListFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString("favorite",favorite);
        first.setArguments(bundle);
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.container, first);
        ft.commit();
    }
}
