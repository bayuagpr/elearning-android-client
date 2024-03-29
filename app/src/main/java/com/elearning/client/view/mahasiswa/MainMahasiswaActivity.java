package com.elearning.client.view.mahasiswa;

import android.content.Intent;
import android.os.Bundle;

import com.elearning.client.view.BaseActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.elearning.client.R;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.view.mahasiswa.kelas.tergabung.TergabungKelasFragment;
import com.elearning.client.view.mahasiswa.search.SearchActivity;
import com.google.android.material.tabs.TabLayout;


public class MainMahasiswaActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    SessionManager sessionManager;
    TabLayout bubbleTabBar;
    // TabItem tabEarly, tabLate;
    FrameLayout frameLayout;
    ViewPager viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mahasiswa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bubbleTabBar = findViewById(R.id.bubbleTabBarEnroll);
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

//        Benerin bug ketika rotasi landsacpe malah pindah ke mainActivity
        viewpager = findViewById(R.id.viewpager);
        viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(bubbleTabBar));

        bubbleTabBar.setupWithViewPager(viewpager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Set text Navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView tv_username = (TextView) headerView.findViewById(R.id.username);
        tv_username.setText(sessionManager.getKeyNama());

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_penjualan) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFrame, new TergabungKelasFragment())
                    .commit();
        } else if (id == R.id.nav_logout) {
            sessionManager.logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            startActivity(new Intent(MainMahasiswaActivity.this, SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
